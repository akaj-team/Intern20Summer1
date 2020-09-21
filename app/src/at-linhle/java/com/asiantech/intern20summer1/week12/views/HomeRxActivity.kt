package com.asiantech.intern20summer1.week12.views

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week12.adapters.PostViewHolder
import com.asiantech.intern20summer1.week12.fragments.LoginFragment.Companion.KEY_STRING_FULL_NAME
import com.asiantech.intern20summer1.week12.fragments.LoginFragment.Companion.SHARED_PREFERENCE_TOKEN
import com.asiantech.intern20summer1.week12.fragments.SearchDialogFragment
import com.asiantech.intern20summer1.week12.models.Post
import com.asiantech.intern20summer1.week12.repository.RemoteRepository
import com.asiantech.intern20summer1.week12.repository.datasource.HomeDataSource
import com.asiantech.intern20summer1.week12.viewmodels.HomeViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.`at-linhle`.activity_post_home.*

class HomeRxActivity : AppCompatActivity() {

    companion object {
        private const val DELAY_TIME = 2000L
        private const val TIME_DELAY = 2000L
        private const val LIMIT_ITEM = 10
    }

    private var fullName: String? = null
    private var token: String? = null
    private var postItems = mutableListOf<Post?>()
    private var postItemsSearch = mutableListOf<Post>()
    private var postItemsStorage = mutableListOf<Post>()
    private lateinit var adapter: PostViewHolder
    private var isLoading = false
    private var viewModel: HomeDataSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_home)
        viewModel = HomeViewModel(RemoteRepository())
        getData()
        initData()
        handleSearchIconCLicked()
        toolbarHome?.title = fullName
    }

    private fun getData() {
        fullName = intent?.getStringExtra(KEY_STRING_FULL_NAME)
        token = intent?.getStringExtra(SHARED_PREFERENCE_TOKEN)
    }

    private fun initAdapter(list: List<Post>) {
        if (list.size >= LIMIT_ITEM) {
            for (i in 0 until LIMIT_ITEM) {
                postItems.add(list[i])
            }
        } else {
            for (element in list) {
                postItems.add(element)
            }
        }
        adapter = PostViewHolder(postItems)
        recyclerViewContainer?.layoutManager = LinearLayoutManager(this)
        adapter.onHeartClicked = {
            handleClickingHeartIcon(it)
        }
        recyclerViewContainer?.adapter = adapter
    }

    private fun initData() {
        token?.let {
            viewModel?.getListPost(it)
                ?.subscribeOn(io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ response ->
                    if (response.isSuccessful) {
                        response.body()?.apply {
                            postItemsStorage = this
                        }
                        progressLoadApi?.visibility = View.GONE
                        initAdapter(postItemsStorage)
                        handleSwipeRefresh()
                        initScrollListener(postItemsStorage)
                    }
                }, {
                    //No-op
                })
        }
    }

    private fun handleSwipeRefresh() {
        swipeContainer.setOnRefreshListener {
            Handler().postDelayed({
                postItems.clear()
                imgPlus?.visibility = View.VISIBLE
                progressLoadApi?.visibility = View.VISIBLE
                initData()
                adapter.notifyDataSetChanged()
                swipeContainer.isRefreshing = false
            }, DELAY_TIME)
        }
    }

    private fun initScrollListener(list: List<Post>) {
        recyclerViewContainer?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager?
                if (!isLoading) {
                    linearLayoutManager?.let {
                        if (it.findLastVisibleItemPosition() == postItems.size - 3
                            && postItems.size < list.size
                        ) {
                            loadMore(list)
                        }
                    }
                }
            }
        })
    }

    private fun loadMore(list: List<Post>) {
        if (postItems.size != 0) {
            Handler().postDelayed({
                var currentSize = postItems.size
                val nextLimit = currentSize + LIMIT_ITEM
                while (currentSize < list.size && currentSize < nextLimit) {
                    postItems.add(list[currentSize])
                    currentSize++
                }
                adapter.notifyDataSetChanged()

                isLoading = false
                progressBar?.visibility = View.INVISIBLE
            }, TIME_DELAY)

            isLoading = true
            progressBar?.visibility = View.VISIBLE
        }
    }

    private fun handleClickingHeartIcon(position: Int) {
        token?.let {
            viewModel?.updateLikePost(it, postItems[position]?.id ?: 0)
                ?.subscribeOn(io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ response ->
                    if (response.isSuccessful) {
                        response.body()?.let { post ->
                            postItems[position]?.likeCount = post.likeCount
                            postItems[position]?.likeFlag = post.likeFlag
                            adapter.notifyItemChanged(position, null)
                            (recyclerViewContainer?.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                                true
                        }
                    }
                }, {
                    //No-op
                })
        }
    }

    private fun handleSearchIconCLicked() {
        imgPlus?.setOnClickListener {
            val fragmentManager = supportFragmentManager
            SearchDialogFragment().show(fragmentManager, null)
        }
    }

    internal fun search(search: String) {
        postItemsSearch.clear()
        for (i in postItemsStorage.indices) {
            if (postItemsStorage[i].content.contains(search)) {
                postItemsStorage[i].content = search
                postItemsSearch.add(postItemsStorage[i])
            }
        }
        if (postItemsSearch.size == 0) {
            Toast.makeText(
                this,
                getString(R.string.home_rx_activity_search_result),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            imgPlus?.visibility = View.INVISIBLE
            postItems.clear()
            postItemsStorage.clear()
            initAdapter(postItemsSearch)
            initScrollListener(postItemsSearch)
        }
    }
}
