package com.asiantech.intern20summer1.w5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-huybui`.activity_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {
    companion object{
        internal interface onClickHeart
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val exampleList: MutableList<ItemRecycler> = mutableListOf()

        exampleList.add(
            ItemRecycler(
                "huy",
                120,
                R.drawable.img_angry_bird_10,
                true,
                "haifhiahfuiiishafuisfuihfh"
            )
        )
        exampleList.add(
            ItemRecycler(
                "huy",
                16456,
                R.drawable.img_angry_bird_9,
                true,
                "haifhiahfuiiishafuisfuihfh"
            )
        )
        exampleList.add(
            ItemRecycler(
                "huy",
                15556,
                R.drawable.img_angry_bird_5,
                true,
                "haifhiahfuiiishafuisfuihfh"
            )
        )
        exampleList.add(
            ItemRecycler(
                "huy",
                146,
                R.drawable.img_angry_bird_2,
                true,
                "haifhiahfuiiishafuisfuihfh"
            )
        )
        exampleList.add(
            ItemRecycler(
                "huy",
                165,
                R.drawable.img_angry_bird_13,
                true,
                "haifhiahfuiiishafuisfuihfh"
            )
        )

        recycler_view.adapter = RecyclerAdapter(exampleList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

    }
}
