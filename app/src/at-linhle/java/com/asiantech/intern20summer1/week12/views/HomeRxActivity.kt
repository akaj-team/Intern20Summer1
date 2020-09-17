package com.asiantech.intern20summer1.week12.views

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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

    companion object{
        private const val DELAY_TIME = 2000L
    }
    private var fullName: String? = null
    private var token: String? = null
    private lateinit var postItems: MutableList<Post?>
    private lateinit var postItemsStorage: MutableList<Post?>
    private lateinit var adapter: PostViewHolder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_home)
        getData()
        initData()
        toolbarHome?.title = fullName
        handleSwipeRefresh()
    }

    private fun getData() {
        fullName = intent?.getStringExtra(KEY_STRING_FULL_NAME)
        token = intent?.getStringExtra(SHARED_PREFERENCE_TOKEN)
    }

    private fun initAdapter() {
        postItems = postItemsStorage
        adapter = PostViewHolder(postItems)
//        adapter.onHeartClicked = {
//            handleClickingHeartIcon(it)
//        }
        recyclerViewContainer.layoutManager = LinearLayoutManager(this)
        recyclerViewContainer.adapter = adapter
    }

    private fun initData() {
        postItemsStorage = mutableListOf()
        token?.let {
            HomeViewModel().getListPost(it)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ response ->
                    if (response.isSuccessful) {
                        response.body()?.apply {
                            for (i in 0 until size) {
                                postItemsStorage.add(this[i])
                            }
                        }
                        progressLoadApi.visibility = View.GONE
                        initAdapter()
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
}
