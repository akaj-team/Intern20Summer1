@file:Suppress("DEPRECATION")

package com.asiantech.intern20summer1.week12.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week12.activity.RecyclerViewNewFeed
import com.asiantech.intern20summer1.week12.data.models.NewPost
import com.asiantech.intern20summer1.week12.data.source.LocalRepository
import com.asiantech.intern20summer1.week12.data.source.PostRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-vuongphan`.w10_fragment_new_feed.*
import retrofit2.Response

class NewFeedFragment : Fragment() {
    private var newfeeds = mutableListOf<NewPost>()
    private lateinit var adapterNewFeeds: ItemFeedAdapter
    private var postItem = mutableListOf<NewPost>()
    private var isLoading = false
    private var postItemSearch = mutableListOf<NewPost>()
    private var token: String? = null
    private var viewModel: PostViewModel? = null

    companion object {
        private const val DELAY_TIME = 2000L
        private const val ITEMS_TAKE: Int = 10
        internal fun newInstance() = NewFeedFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = PostViewModel(PostRepository(), LocalRepository(requireContext()))
        token = viewModel?.getToken().toString()
        newfeeds = viewModel?.getDataRecyclerView() ?: mutableListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.w10_fragment_new_feed, container, false)
        val toolbar = view?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.tbNewFeed)
        (activity as RecyclerViewNewFeed).setSupportActionBar(toolbar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter(viewModel?.getDataRecyclerView() ?: mutableListOf())
        openAddNewFeedFragment()
        openDialogSearch()
        initListener()
        getListAPI()
        handleSwipeRefresh()
    }

    private fun openDialogSearch() {
        imgSearch?.setOnClickListener {
            val fragment = FragmentDialogSearch.newInstance(this@NewFeedFragment)
            fragmentManager?.let { it1 -> fragment.show(it1, "") }
        }
    }

    private fun openAddNewFeedFragment() {
        imgPlus?.setOnClickListener {
            (activity as? RecyclerViewNewFeed)?.openFragment(AddNewFeedFragment.newInstance(), true)
        }
    }

    private fun initAdapter(list: List<NewPost>) {
        if (list.size >= ITEMS_TAKE) {
            for (i in 0 until ITEMS_TAKE) {
                postItem.add(list[i])
            }
        } else {
            for (element in list) {
                postItem.add(element)
            }
        }
        adapterNewFeeds = ItemFeedAdapter(postItem)
        recyclerViewMain?.layoutManager = LinearLayoutManager(requireContext())
        adapterNewFeeds.onItemClicked = { position ->
            token?.let { viewModel?.likePost(it, position) }
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    adapterNewFeeds.notifyItemChanged(position, null)
                    (recyclerViewMain.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                        true
                }, {

                })
            handleSwipeRefresh()
        }
        recyclerViewMain?.adapter = adapterNewFeeds
    }

    private fun initListener() {
        srlRefreshItem.setOnRefreshListener {
            Handler().postDelayed({
                viewModel?.getDataRecyclerView()?.clear()
                getListAPI()
                adapterNewFeeds.notifyDataSetChanged()
                srlRefreshItem.isRefreshing = false
            }, DELAY_TIME)
        }
    }


    @Suppress("NAME_SHADOWING")
    private fun getListAPI() {
        viewModel?.getToken()?.let {
            viewModel?.getPost()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ it: Response<MutableList<NewPost>> ->
                    adapterNewFeeds.notifyDataSetChanged()
                    progressLoadApi.visibility = View.GONE
                     initAdapter(viewModel?.getDataRecyclerView() ?: mutableListOf())
                    initScrollListener(viewModel?.getDataRecyclerView() ?: mutableListOf())
                    handleSwipeRefresh()
                }, {

                })
        }
    }

    private fun handleSwipeRefresh() {
        srlRefreshItem.setOnRefreshListener {
            Handler().postDelayed({
                postItem.clear()
                progressBar.visibility = View.INVISIBLE
                getListAPI()
                adapterNewFeeds.notifyDataSetChanged()
                srlRefreshItem.isRefreshing = false
            }, DELAY_TIME)
        }
    }

    internal fun search(search: String) {
        postItemSearch.clear()
        for (i in newfeeds.indices) {
            if (newfeeds[i].content.contains(search)) {
                postItemSearch.add(newfeeds[i])
            }
        }
        if (postItemSearch.size == 0) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.search_error),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            imgSearch.visibility = View.INVISIBLE
            postItem.clear()
            newfeeds.clear()
            initAdapter(postItemSearch)
            initScrollListener(postItemSearch)
        }
    }

    private fun loadMore(list: List<NewPost>) {
        if (postItem.size != 0) {
            Handler().postDelayed({
                var currentSize = postItem.size
                val nextLimit = currentSize + ITEMS_TAKE
                while (currentSize < list.size && currentSize < nextLimit) {
                    postItem.add(list[currentSize])
                    currentSize++
                }
                adapterNewFeeds.notifyDataSetChanged()

                isLoading = false
                progressBar?.visibility = View.INVISIBLE
            }, DELAY_TIME)

            isLoading = true
            progressBar?.visibility = View.VISIBLE
        }
    }

    private fun initScrollListener(list: List<NewPost>) {
        recyclerViewMain?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager?
                if (!isLoading) {
                    linearLayoutManager?.let {
                        if (it.findLastVisibleItemPosition() == postItem.size - 3
                            && postItem.size < list.size
                        ) {
                            loadMore(list)
                        }
                    }
                }
            }
        })
    }
}
