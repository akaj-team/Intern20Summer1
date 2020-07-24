package com.asiantech.intern20summer1.w5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-huybui`.activity_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val exampleList:MutableList<ItemRecycler> = mutableListOf()

        exampleList.add(ItemRecycler("huy",1,1,true,""))
        exampleList.add(ItemRecycler("huy",1,1,true,""))
        exampleList.add(ItemRecycler("huy",1,1,true,""))
        exampleList.add(ItemRecycler("huy",1,1,true,""))

        recycler_view.adapter = RecyclerAdapter(exampleList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

    }
}
