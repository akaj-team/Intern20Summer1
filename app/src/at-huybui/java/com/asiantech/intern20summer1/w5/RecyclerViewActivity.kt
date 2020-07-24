package com.asiantech.intern20summer1.w5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-huybui`.activity_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {
    private val exampleLists: MutableList<ItemRecycler> = mutableListOf()
    var adapterRecycler = RecyclerAdapter(exampleLists)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        initData()
        initAdapter()
    }


    private fun initAdapter() {
        adapterRecycler.onItemClicked = { position ->
            exampleLists[position].let {
                it.statusHeart = !it.statusHeart
                if (it.statusHeart) {
                    it.amountHeart++
                } else {
                    it.amountHeart--
                }
            }
            adapterRecycler.notifyItemChanged(position, null)
            (recycler_view.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        }
        recycler_view.adapter = adapterRecycler
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
    }

    private fun initData() {
    }
}
