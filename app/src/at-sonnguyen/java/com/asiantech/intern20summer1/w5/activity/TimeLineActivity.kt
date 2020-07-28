package com.asiantech.intern20summer1.w5.activity

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w5.data.TimeLineAdapter
import com.asiantech.intern20summer1.w5.data.TimeLineItem
import kotlinx.android.synthetic.`at-sonnguyen`.activity_time_line.*
import kotlin.random.Random

@Suppress("DEPRECATION")
class TimeLineActivity : AppCompatActivity() {
    private lateinit var adapter: TimeLineAdapter
    private var isLoading = false
    private var items = mutableListOf<TimeLineItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)
        initAdapter()
        items.addAll(newData())
        initListenerLikeButton()
        initScrollViewLoadMoreListener()
        initRefreshPost()
    }

    private fun initAdapter() {
        adapter = TimeLineAdapter(items)
        recyclerViewMain.layoutManager = LinearLayoutManager(this)
        recyclerViewMain.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.bg_recycler_view_divider_decoration))
        recyclerViewMain.addItemDecoration(dividerItemDecoration)

    }

    private fun initListenerLikeButton() {
        adapter.onItemClicked = {
            val isLiked = items[it].isLiked
            val item = items[it]
            if (isLiked) {
                item.isLiked = !isLiked
                item.likeNumber -= 1
            } else {
                item.isLiked = !isLiked
                item.likeNumber += 1
            }
            adapter.notifyItemChanged(it)
        }
    }

    private fun reloadData() {
        adapter.notifyDataSetChanged()
    }

    private fun newData(): MutableList<TimeLineItem> {
        val posts = mutableListOf<TimeLineItem>()
        val images = arrayOf(
            R.mipmap.img_1,
            R.mipmap.img_2,
            R.mipmap.img_3,
            R.mipmap.img_4,
            R.mipmap.img_5,
            R.mipmap.img_6,
            R.mipmap.img_7,
            R.mipmap.img_8,
            R.mipmap.img_9,
            R.mipmap.img_10
        )
        val user = arrayOf(
            getString(R.string.w5_user1),
            getString(R.string.w5_user2),
            getString(R.string.w5_user3),
            getString(R.string.w5_user4),
            getString(R.string.w5_user5),
            getString(R.string.w5_user6),
            getString(R.string.w5_user7),
            getString(R.string.w5_user8),
            getString(R.string.w5_user9),
            getString(R.string.w5_user10)
        )
        for (i in 0..9) {
            posts.add(
                TimeLineItem(
                    images[Random.nextInt(1, 10)],
                    Random.nextBoolean(),
                    Random.nextInt(0, 1000),
                    user[Random.nextInt(1, 10)],
                    "This is post number $i "
                )
            )
        }
        return posts
    }

    private fun initScrollViewLoadMoreListener() {
        recyclerViewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerViewMain.layoutManager as LinearLayoutManager
                val lastItemIndex = layoutManager.findLastVisibleItemPosition()
                if (!isLoading) {
                    if (lastItemIndex == items.size - 1) {
                        isLoading = true
                        progressBarMain.visibility = View.VISIBLE
                        Handler().postDelayed({
                            progressBarMain.visibility = View.INVISIBLE
                            items.addAll(newData())
                            reloadData()
                            isLoading = false
                        }, 2000)
                    }
                }
            }
        })
    }

    private fun initRefreshPost() {
        pullToRefresh.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )
        pullToRefresh.setColorSchemeColors(Color.WHITE)
        pullToRefresh.setOnRefreshListener {
            Handler().postDelayed({
                items.clear()
                items.addAll(newData())
                reloadData()
                pullToRefresh.isRefreshing = false
            }, 1000)
        }
    }
}
