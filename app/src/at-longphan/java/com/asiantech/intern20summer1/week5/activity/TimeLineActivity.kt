package com.asiantech.intern20summer1.week5.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week5.adapter.TimeLineItemAdapter
import com.asiantech.intern20summer1.week5.model.TimeLineItem

class TimeLineActivity : AppCompatActivity() {
    var timeLineItems = mutableListOf<TimeLineItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)

        var rvTimeLineItems: RecyclerView = findViewById(R.id.rvTimeLineItems)
        timeLineItems = TimeLineItem().createTimeLineItemsList(20)

        val adapter = TimeLineItemAdapter(this, timeLineItems)

        rvTimeLineItems.adapter = adapter

        rvTimeLineItems.layoutManager = LinearLayoutManager(this)
    }
}
