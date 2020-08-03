package com.asiantech.intern20summer1.week5

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.recycler_view_layout.*
import java.util.*

class RecyclerViewActivity : AppCompatActivity(), LoadMore {

    companion object {
        private const val TEN = 10
        private const val HUNDRED = 100
        private const val DELAY_TIME = 2000L
    }

    private lateinit var itemList: MutableList<ItemRecycler>
    private lateinit var itemListStorage: MutableList<ItemRecycler>
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view_layout)
        initAdapter()
        pullToRefresh()
        putData()
    }

    override fun onLoadMore() {
        adapter.notifyItemInserted(itemList.size - 1)
        Handler().postDelayed({
            itemList.removeAt(itemList.size - 1)
            val index = itemList.size
            adapter.notifyItemRemoved(index)
            val end = index + TEN
            for (i in index until end) {
                itemList.add(itemListStorage[Random().nextInt(itemListStorage.size)])
            }
            adapter.notifyDataSetChanged()
            adapter.setLoaded()
        }, DELAY_TIME)
    }

    private fun initAdapter() {
        itemListStorage.shuffle()
        itemList = itemListStorage.subList(0, 10)
        adapter = RecyclerAdapter(rv_contact, this, itemList.toMutableList())
        adapter.onHeartClicked = { position ->
            itemList[position].let {
                if (it.heartStatus) {
                    it.heartStatus = false
                    it.heartCount--
                } else {
                    it.heartStatus = true
                    it.heartCount++
                }
                adapter.notifyItemChanged(position)
            }
        }
        rv_contact.adapter = adapter
        adapter.setLoadMore(this)
    }



    private fun pullToRefresh() {
        swipeContainer.setOnRefreshListener {
            Handler().postDelayed({
                adapter.clear()
                adapter.addAll(itemListStorage.apply {
                    shuffle()
                    subList(0, 10)
                })
                swipeContainer.isRefreshing = false
            }, DELAY_TIME)
        }
    }

    fun putData() {
        itemListStorage = mutableListOf()
        itemListStorage.apply {
            add(
                ItemRecycler(
                    R.mipmap.image1,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image2,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image3,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image4,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image5,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image6,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image7,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image8,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image9,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image10,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image11,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image12,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image13,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image14,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image15,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image16,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image17,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image18,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image19,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
            add(
                ItemRecycler(
                    R.mipmap.image20,
                    R.mipmap.heartless,
                    (0..HUNDRED).random(),
                    "Chè Ngon Hà Nội & Món Ngon Đường Phố - Tô Hiệu\n" +
                            "297 Tô Hiệu, Quận Cầu Giấy, Hà Nội",
                    false
                )
            )
        }
    }
}
