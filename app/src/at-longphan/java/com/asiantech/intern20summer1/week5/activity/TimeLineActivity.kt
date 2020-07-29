package com.asiantech.intern20summer1.week5.activity

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week5.adapter.TimeLineItemAdapter
import com.asiantech.intern20summer1.week5.model.TimeLineItem
import kotlinx.android.synthetic.`at-longphan`.activity_time_line.*


class TimeLineActivity : AppCompatActivity() {

    private var timeLineItemsAll = mutableListOf<TimeLineItem>()
    private var timeLineItemsShowed = mutableListOf<TimeLineItem>()
    private lateinit var rvTimeLineItems: RecyclerView
    private lateinit var adapter: TimeLineItemAdapter
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)
        configStatusBar()
        initRecyclerView()
        initData()
        initAdapter()
        assignRecyclerView()
        initScrollListener()
    }

    private fun configStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun initRecyclerView() {
        rvTimeLineItems = findViewById(R.id.rvTimeLineItems)
    }

    private fun initData() {
        timeLineItemsAll = TimeLineItem().createTimeLineItemsList(25)
        timeLineItemsAll.shuffle()
        for (i in 0..9) {
            timeLineItemsShowed.add(timeLineItemsAll[i])
        }
    }

    private fun initAdapter() {
        adapter = TimeLineItemAdapter(this, timeLineItemsShowed)
        adapter.onIsLikedImageViewClick = { position ->
            timeLineItemsShowed[position].let {
                it.isLiked = !it.isLiked
                if (it.isLiked) {
                    it.likes++
                } else {
                    if (it.likes > 0) it.likes--
                }
                if (it.likes > 1) {
                    it.isPluralLike = true
                }
            }
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
                val linearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager?
                if (!isLoading) {
                    linearLayoutManager?.let {
                        if (it.findLastCompletelyVisibleItemPosition() == timeLineItemsShowed.size - 1
                            && timeLineItemsShowed.size < timeLineItemsAll.size
                        ) {
                            loadMore()
                            isLoading = true
                            progressBar.visibility = View.VISIBLE
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
            progressBar.visibility = View.INVISIBLE
        }, 2000)
    }
}
