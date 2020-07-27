package com.asiantech.intern20summer1.week5.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week5.adapter.TimeLineItemAdapter
import com.asiantech.intern20summer1.week5.model.TimeLineItem

class TimeLineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)
        configStatusBar()
        initRecyclerView()
        initData()
        initAdapter()
        assignRecyclerView()
    }

    private var timeLineItems = mutableListOf<TimeLineItem>()
    private lateinit var rvTimeLineItems: RecyclerView
    private lateinit var adapter: TimeLineItemAdapter

    private fun configStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun initRecyclerView() {
        rvTimeLineItems = findViewById(R.id.rvTimeLineItems)
    }

    private fun initData() {
        timeLineItems = TimeLineItem().createTimeLineItemsList(20)
    }

    private fun initAdapter() {
        adapter = TimeLineItemAdapter(this, timeLineItems)
        adapter.onIsLikedImageViewClick = { position ->
            timeLineItems[position].let {
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
            (rvTimeLineItems.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        }
    }

    private fun assignRecyclerView() {
        rvTimeLineItems.adapter = adapter
        rvTimeLineItems.layoutManager = LinearLayoutManager(this)
    }
}
