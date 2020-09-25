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
import com.asiantech.intern20summer1.week12.data.source.LocalRepository
import com.asiantech.intern20summer1.week12.data.source.PostRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.`at-vuongphan`.w10_fragment_new_feed.*

class NewFeedFragment : Fragment() {
    private lateinit var adapterNewFeeds: ItemFeedAdapter
    private var id: Int? = 0
    private var token: String? = null
    private var viewModel: PostViewModel? = null
    private var post = viewModel?.getListPostAdapter()

    companion object {
        private const val DELAY_TIME = 2000L
        internal fun newInstance() = NewFeedFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            PostViewModel(PostRepository(requireContext()), LocalRepository(requireContext()))
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
        openAddNewFeedFragment()
        openDialogSearch()
        getData()
        initData()
        openDialogSearch()
        openAddNewFeedFragment()
    }

    private fun getData() {
        token = viewModel?.getToken().toString()
        id = viewModel?.getIdUser()
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

    private fun initAdapter() {
        adapterNewFeeds = ItemFeedAdapter(post)
        recyclerViewMain?.layoutManager = LinearLayoutManager(requireContext())
        adapterNewFeeds.onItemClicked = {
            handleClickingHeartIcon(it)
        }
        recyclerViewMain?.adapter = adapterNewFeeds
    }

    private fun initData() {
        token?.let {
            viewModel?.getListPostFromServer(it)
                ?.subscribeOn(io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    initAdapter()
                    progressLoadApi?.visibility = View.GONE
                    handleSwipeRefresh()
                    initScrollListener()
                }, {

                })
        }
    }

    private fun handleSwipeRefresh() {
        srlRefreshItem.setOnRefreshListener {
            Handler().postDelayed({
                viewModel?.refreshData()
                imgPlus?.visibility = View.VISIBLE
                progressLoadApi?.visibility = View.VISIBLE
                initData()
                adapterNewFeeds.notifyDataSetChanged()
                srlRefreshItem.isRefreshing = false
            }, DELAY_TIME)
        }
    }

    private fun initScrollListener() {
        recyclerViewMain?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager?
                linearLayoutManager?.let {
                    val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                    viewModel?.loadMore(lastVisibleItem)
                    viewModel?.updateProgressBar()?.subscribe({
                        if (it) {
                            progressBar?.visibility = View.VISIBLE
                        } else {
                            progressBar?.visibility = View.INVISIBLE
                        }
                    }, {
                        //No-op
                    })
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                adapterNewFeeds.notifyDataSetChanged()
            }
        })
    }

    private fun handleClickingHeartIcon(position: Int) {
        token?.let {
            viewModel?.updateLikePost(
                it,
                id?.let { it1 -> viewModel?.getListPost()?.get(it1)?.id } ?: 0

            )
                ?.subscribeOn(io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    adapterNewFeeds.notifyItemChanged(position, null)
                    (recyclerViewMain.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                        true
                }, {
                    //No-op
                })
        }
    }

    internal fun search(search: String) {
        viewModel?.searchPostFromServer(search)
            ?.subscribeOn(io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                if (viewModel?.getResultSearch() == true) {
                    adapterNewFeeds.notifyDataSetChanged()
                    imgPlus?.visibility = View.INVISIBLE
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.search_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, {
                //No-op
            })
    }
}
