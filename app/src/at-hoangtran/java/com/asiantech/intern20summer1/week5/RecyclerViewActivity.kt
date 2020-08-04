package com.asiantech.intern20summer1.week5

import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.loading_layout.*
import kotlinx.android.synthetic.`at-hoangtran`.recycler_view_layout.*

class RecyclerViewActivity : AppCompatActivity() {
    companion object {
        private const val HUNDED = 100
        private const val DELAY = 5000L
    }

    private val itemList: MutableList<ItemRecycler> = mutableListOf()
    private val adapter = RecyclerAdapter(itemList)
    private var isLoadMore = false

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.recycler_view_layout)

        handleRefresh()
        initAdapter()
        initData()
    }

    private fun initAdapter() {
        adapter.onItemClick = { position ->
            itemList[position].let {
                it.heartStatus = !it.heartStatus
                if (it.heartStatus) {
                    it.heartCount++
                } else {
                    it.heartCount--
                }
            }
            adapter.notifyItemChanged(position, null)
        }
        rv_contact.adapter = adapter
        rv_contact.layoutManager = LinearLayoutManager(this)
        rv_contact.setHasFixedSize(true)
    }

    private fun handleRefresh() {
        swipeContainer.setOnRefreshListener {
            Handler().postDelayed({
                itemList.clear()
                initData()
                adapter.notifyDataSetChanged()
                swipeContainer.isRefreshing = false
            }, DELAY)
        }
        rv_contact.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dy, dx)
                val linearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager
                val lastVisibleItem = linearLayoutManager?.findLastCompletelyVisibleItemPosition()
                if (!isLoadMore && (lastVisibleItem == itemList.size - 1)) {
                    progressBar.visibility = View.VISIBLE
                    Handler().postDelayed({
                        isLoadMore = true
                        progressBar.visibility = View.INVISIBLE
                        initData()
                        adapter.notifyDataSetChanged()
                    }, DELAY)
                }
                isLoadMore = false
            }
        })
    }

    private fun initData() {
        itemList.add(
            ItemRecycler(
                R.mipmap.image1,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image2,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image3,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image4,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image5,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image6,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image7,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image8,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image9,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image10,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image11,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image12,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image13,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image14,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image15,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image16,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image17,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image18,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image19,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
        itemList.add(
            ItemRecycler(
                R.mipmap.image20,
                R.mipmap.heartless,
                (0..HUNDED).random(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                false
            )
        )
    }
}
