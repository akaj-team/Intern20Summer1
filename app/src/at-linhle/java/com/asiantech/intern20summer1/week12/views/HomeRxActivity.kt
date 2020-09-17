package com.asiantech.intern20summer1.week12.views

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week12.adapters.PostViewHolder
import com.asiantech.intern20summer1.week12.fragments.LoginFragment.Companion.KEY_STRING_FULL_NAME
import com.asiantech.intern20summer1.week12.fragments.LoginFragment.Companion.SHARED_PREFERENCE_TOKEN
import com.asiantech.intern20summer1.week12.models.Post
import com.asiantech.intern20summer1.week12.viewmodels.HomeViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
    private lateinit var postItemsStorage: List<Post?>
    private lateinit var adapter: PostViewHolder
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_home)
        getData()
        initData()
        toolbarHome?.title = fullName
    }

    private fun getData() {
        fullName = intent?.getStringExtra(KEY_STRING_FULL_NAME)
        token = intent?.getStringExtra(SHARED_PREFERENCE_TOKEN)
    }

    private fun initAdapter() {
        for(i in 0 until LIMIT_ITEM){
            postItems.add(postItemsStorage[i])
        }
        adapter = PostViewHolder(postItems)
        recyclerViewContainer.layoutManager = LinearLayoutManager(this)
//        adapter.onHeartClicked = {
//            handleClickingHeartIcon(it)
//        }
        recyclerViewContainer.adapter = adapter
    }

    private fun initData() {
        postItemsStorage = listOf()
        token?.let {
            HomeViewModel().getListPost(it)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ response ->
                    if (response.isSuccessful) {
                        response.body()?.apply {
                            postItemsStorage = this
                        }
                        progressLoadApi.visibility = View.GONE
                        initAdapter()
                        handleSwipeRefresh()
                        initScrollListener()
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
                progressLoadApi.visibility = View.VISIBLE
                initData()
                adapter.notifyDataSetChanged()
                swipeContainer.isRefreshing = false
            }, DELAY_TIME)
        }
    }

    private fun initScrollListener() {
        recyclerViewContainer?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager?
                if (!isLoading) {
                    linearLayoutManager?.let {
//                        Log.d("TAG", "onScrolled: ${it.findLastVisibleItemPosition()}")
                        if (it.findLastVisibleItemPosition() == postItems.size - 3
                            && postItems.size < postItemsStorage.size
                        ) {
                            loadMore()
                        }
                    }
                }
            }
        })
    }

    private fun loadMore() {
        if (postItems.size != 0) {
            Handler().postDelayed({
                var currentSize = postItems.size
                val nextLimit = currentSize + LIMIT_ITEM
                while (currentSize < postItemsStorage.size && currentSize < nextLimit) {
                    postItems.add(postItemsStorage[currentSize])
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
}
