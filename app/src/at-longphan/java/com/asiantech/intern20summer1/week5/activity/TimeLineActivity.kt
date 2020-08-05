package com.asiantech.intern20summer1.week5.activity

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week5.adapter.TimeLineItemAdapter
import com.asiantech.intern20summer1.week5.model.TimeLineItem
import kotlinx.android.synthetic.`at-longphan`.activity_time_line.*
import kotlinx.android.synthetic.`at-longphan`.item_list_time_line.*

class TimeLineActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private const val TIME_DELAY: Long = 2000
        private const val ITEMS_INIT: Int = 25
        private const val ITEMS_TAKE: Int = 10
    }

    private var timeLineItemsAll = mutableListOf<TimeLineItem>()
    private var timeLineItemsShowed = mutableListOf<TimeLineItem>()
    private lateinit var rvTimeLineItems: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: TimeLineItemAdapter
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)
        configStatusBar()
        initRecyclerView()
        initData()
        initAdapter()
        initIsLikedImageViewClickListener()
        assignRecyclerView()
        initSwipeRefreshLayout()
        initScrollListener()
    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = true
        reloadData()
        swipeRefreshLayout.isRefreshing = false
    }

    private fun configStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun initRecyclerView() {
        rvTimeLineItems = findViewById(R.id.recycleViewTimeLine)
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout = findViewById(R.id.swipeContainer)
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    private fun initData() {
        timeLineItemsAll = TimeLineItem().createTimeLineItemsList(ITEMS_INIT)
        for (i in 0 until ITEMS_TAKE) {
            timeLineItemsShowed.add(timeLineItemsAll[i])
        }
    }

    private fun initAdapter() {
        adapter = TimeLineItemAdapter(this, timeLineItemsShowed)
    }

    private fun initIsLikedImageViewClickListener() {
        adapter.onIsLikedImageViewClick = { position ->
            timeLineItemsShowed[position].let {
                it.isLiked = !it.isLiked
                if (it.isLiked) {
                    it.likes++
                } else {
                    if (it.likes > 0) {
                        it.likes--
                    }
                }
                if (it.likes > 1) {
                    it.isPluralLike = true
                }
            }
            imgLikes

            adapter.notifyItemChanged(position)
            // Remove flash animation when interact with a row item
            (rvTimeLineItems.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        }
    }

    private fun assignRecyclerView() {
        rvTimeLineItems.adapter = adapter
        rvTimeLineItems.layoutManager = LinearLayoutManager(this)
    }

    private fun initScrollListener() {
        rvTimeLineItems.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager
                if (!isLoading) {
                    linearLayoutManager?.let {
                        if (it.findLastCompletelyVisibleItemPosition() == timeLineItemsShowed.size - 1
                        ) {
                            loadMore()
                            isLoading = true
                            progressBarW5.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })
    }

    private fun loadMore() {
        Handler().postDelayed({
            var currentSize = timeLineItemsShowed.size
            val nextLimit = currentSize + 10
            while (currentSize < timeLineItemsAll.size && currentSize < nextLimit) {
                timeLineItemsShowed.add(timeLineItemsAll[currentSize])
                currentSize++
            }
            adapter.notifyDataSetChanged()
            isLoading = false
            progressBarW5.visibility = View.INVISIBLE
        }, TIME_DELAY)
    }

    private fun reloadData() {
        timeLineItemsShowed.clear()
        for (i in 0 until ITEMS_TAKE) {
            timeLineItemsShowed.add(timeLineItemsAll[i])
        }
        adapter.notifyDataSetChanged()
    }
}
