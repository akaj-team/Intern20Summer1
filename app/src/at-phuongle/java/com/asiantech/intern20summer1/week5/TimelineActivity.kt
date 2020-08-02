package com.asiantech.intern20summer1.week5

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.model.TimelineItem
import kotlinx.android.synthetic.`at-phuongle`.activity_timeline.*

class TimelineActivity : AppCompatActivity() {
    private var timeLineAdapter: TimelineRecyclerAdapter? = null
    private var isLoadMore: Boolean = false
    private var data: MutableList<TimelineItem> = DataSource.createDataSet()

    companion object {
        const val PROCESS_TIME = 2000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)

        initRecyclerView()
        addDataSet()
        handleSwipeRefreshLayout()
    }

    private fun addDataSet() {
        data.shuffle()
        timeLineAdapter?.submitList(data)
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@TimelineActivity)
            timeLineAdapter = TimelineRecyclerAdapter()
            adapter = timeLineAdapter
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lm = recyclerView.layoutManager as LinearLayoutManager
                val lastItems = lm.findLastVisibleItemPosition()

                if (isLoadMore && lastItems == data.size - 1) {
                    progressBar.visibility = View.VISIBLE

                    Handler().postDelayed({
                        val newData: MutableList<TimelineItem> = DataSource.createDataSet()
                        newData.shuffle()

                        data.addAll(newData)
                        timeLineAdapter?.notifyDataSetChanged()

                        progressBar.visibility = View.GONE
                    }, PROCESS_TIME)

                    isLoadMore = false
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isLoadMore = true
                }
            }
        })
    }

    private fun handleSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            data.clear()
            data = DataSource.createDataSet()
            initRecyclerView()
            addDataSet()
            timeLineAdapter?.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }
    }
}
