@file:Suppress("DEPRECATION")

package com.asiantech.intern20summer1.week12.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week12.activity.RecyclerViewNewFeed
import com.asiantech.intern20summer1.adapter.w10.ItemFeedAdapter
import com.asiantech.intern20summer1.week12.data.models.NewPost
import com.asiantech.intern20summer1.week12.data.models.ResponseLike
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
        viewModel = PostViewModel(PostRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.w10_fragment_new_feed, container, false)
        val toolbar = view?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.tbNewFeed)
        (activity as RecyclerViewNewFeed).setSupportActionBar(toolbar)
        getToken()
        val imgPlus = view.findViewById<ImageView>(R.id.imgPlus)
        val imgSearch = view.findViewById<ImageView>(R.id.imgSearch)
        imgSearch?.setOnClickListener {
            val fragment = FragmentDialogSearch.newInstance(this@NewFeedFragment)
            fragmentManager?.let { it1 -> fragment.show(it1, "") }
        }
        imgPlus?.setOnClickListener {
            Bundle().let {
                it.putString(resources.getString(R.string.key_token), token)
                val addNewFeedFragment = AddNewFeedFragment.newInstance()
                addNewFeedFragment.arguments = it
                (activity as? RecyclerViewNewFeed)?.openFragment(addNewFeedFragment, true)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        getListAPI()
        handleSwipeRefresh()
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
            addLikePost(position)
            handleSwipeRefresh()
        }
        recyclerViewMain?.adapter = adapterNewFeeds
    }

    private fun getToken() {
        val bundle = arguments
        if (bundle != null) {
            token = bundle.getString(resources.getString(R.string.key_data)).toString()
        } else {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.string_error),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initListener() {
        srlRefreshItem.setOnRefreshListener {
            Handler().postDelayed({
                newfeeds.clear()
                getListAPI()
                adapterNewFeeds.notifyDataSetChanged()
                srlRefreshItem.isRefreshing = false
            }, DELAY_TIME)
        }
    }


    @Suppress("NAME_SHADOWING")
    private fun getListAPI() {
        token?.let { viewModel?.getPost(it) }
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ it: Response<MutableList<NewPost>> ->
                if (it.isSuccessful) {
                    it.body()?.apply {
                        newfeeds = this
                    }
                    progressLoadApi.visibility = View.GONE
                    initAdapter(newfeeds)
                    initScrollListener(newfeeds)
                    handleSwipeRefresh()
                } else {
                    Toast.makeText(requireContext(), "Thất bại", Toast.LENGTH_SHORT).show()
                }
            }, {
                it.message?.let { it -> displayErrorDialog(it) }
            })
    }

    private fun displayErrorDialog(message: String) {
        val errorDialog = AlertDialog.Builder(requireContext())
        errorDialog.setTitle(
            getString(R.string.dialog_title_error)
        )
            .setMessage(message)
            .setPositiveButton(R.string.dialog_text_ok) { dialog, _ -> dialog.dismiss() }
            .show()
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

    @Suppress("NAME_SHADOWING")
    private fun addLikePost(position: Int) {
        token?.let { viewModel?.likePost(it, newfeeds[position].id) }
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ it: Response<ResponseLike> ->
                if (it.isSuccessful) {
                    it.body()?.let {
                        newfeeds[position].like_count = it.likeCount
                        newfeeds[position].like_flag = it.like_flag
                        adapterNewFeeds.notifyItemChanged(position, null)
                        (recyclerViewMain.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                            true
                    }
                }
            }, {
                it.message.let { it -> it?.let { it1 -> displayErrorDialog(it1) } }
            })
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
                newfeeds[i].content = search
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
}
