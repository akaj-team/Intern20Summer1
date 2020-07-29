package com.asiantech.intern20summer1.week5

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-phuongle`.activity_timeline.*
import kotlinx.android.synthetic.`at-phuongle`.layout_timeline_list_item.*

class TimelineActivity : AppCompatActivity() {
    private var timeLineAdapter: TimelineRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)

        initRecyclerView()
        addDataSet()
    }

    private fun addDataSet() {
        val data = DataSource.createDataSet()
        timeLineAdapter?.submitList(data)
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@TimelineActivity)
            val topSpacingItemDecoration =
                TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            timeLineAdapter = TimelineRecyclerAdapter()
            adapter = timeLineAdapter
        }
    }
}
