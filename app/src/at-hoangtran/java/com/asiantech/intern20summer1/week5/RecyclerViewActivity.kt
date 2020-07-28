package com.asiantech.intern20summer1.week5

import android.annotation.SuppressLint
import android.content.ClipData
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R

class RecyclerViewActivity : AppCompatActivity() {
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.recycler_view_layout)

        val recyclerView = findViewById(R.id.rv_contact) as? RecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val item = mutableListOf<ItemRecycler>()
        item.add(ItemRecycler())
    }
}