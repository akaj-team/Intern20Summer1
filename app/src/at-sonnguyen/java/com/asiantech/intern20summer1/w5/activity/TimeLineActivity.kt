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

    companion object{
        private const val MAX_LIKE_NUMBER = 1000
        private const val DELAY_TIME = 2000
        private const val MAX_DATA_NUMBER_ONE_TIME =10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)
        initAdapter()
        items.addAll(getNewData())
        initListenerLikeButton()
        initScrollViewLoadMoreListener()
        initRefreshPostListener()
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

    private fun getNewData(): MutableList<TimeLineItem> {
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
        for (i in 0 until MAX_DATA_NUMBER_ONE_TIME) {
            posts.add(
                TimeLineItem(
                    images[Random.nextInt(1, MAX_DATA_NUMBER_ONE_TIME)],
                    Random.nextBoolean(),
                    Random.nextInt(0, MAX_LIKE_NUMBER),
                    user[Random.nextInt(1, MAX_DATA_NUMBER_ONE_TIME)],
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
                            items.addAll(getNewData())
                            reloadData()
                            isLoading = false
                        }, DELAY_TIME.toLong())
                    }
                }
            }
        })
    }

    private fun initRefreshPostListener() {
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
                items.addAll(getNewData())
                reloadData()
                pullToRefresh.isRefreshing = false
            }, DELAY_TIME.toLong())
        }
    }
}
