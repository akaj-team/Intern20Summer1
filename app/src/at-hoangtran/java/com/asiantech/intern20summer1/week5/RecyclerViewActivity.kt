package com.asiantech.intern20summer1.week5

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.recycler_view_layout.*

class RecyclerViewActivity : AppCompatActivity() {
    private lateinit var recyclerAdapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.recycler_view_layout)

        initRecyclerView()
    }

    @SuppressLint("WrongConstant")
    private fun initRecyclerView() {
        rv_contact.apply {
            layoutManager = LinearLayoutManager(this@RecyclerViewActivity)
            val topSpacing = TopSpacingDecoration(30)
            addItemDecoration(topSpacing)
            recyclerAdapter = RecyclerAdapter()
            adapter = recyclerAdapter
        }
    }
}
