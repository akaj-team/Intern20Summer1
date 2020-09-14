package com.asiantech.intern20summer1.week5

import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.recycler_view_layout.*
import java.util.*

@Suppress("DEPRECATION")
class TimeLineActivity : AppCompatActivity(), LoadMore {

    companion object {
        private const val LAST_ITEM_POSITION = 10
        private const val RANDOM_INDEX_MAX = 100
        private const val DELAY_TIME = 2000L
    }

    private lateinit var timeLineItems: MutableList<TimeLineItem?>
    private lateinit var timeLineItemsStorage: MutableList<TimeLineItem?>
    private lateinit var adapter: RecyclerAdapter

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view_layout)
        initData()
        initAdapter()
        pullToRefresh()
    }

    override fun onLoadMore() {
        timeLineItems.add(null)
        adapter.notifyItemInserted(timeLineItems.size - 1)
        Handler().postDelayed({
            timeLineItems.removeAt(timeLineItems.size - 1)
            val index = timeLineItems.size
            adapter.notifyItemRemoved(index)
            val end = index + LAST_ITEM_POSITION
            for (i in index until end) {
                timeLineItems.add(timeLineItemsStorage[Random().nextInt(timeLineItemsStorage.size)])
            }
            adapter.notifyDataSetChanged()
            adapter.setLoaded()
        }, DELAY_TIME)
    }

    private fun initAdapter() {
        timeLineItemsStorage.shuffle()
        timeLineItems = timeLineItemsStorage.subList(0, LAST_ITEM_POSITION)
        adapter = RecyclerAdapter(recyclerViewContainer, this, timeLineItems)
        adapter.onHeartClicked = { position ->
            timeLineItems[position]?.let {
                if (it.isLiked) {
                    it.isLiked = false
                    it.countLike--
                } else {
                    it.isLiked = true
                    it.countLike++
                }
                adapter.notifyItemChanged(position)
            }
        }
        recyclerViewContainer.adapter = adapter
        adapter.setLoadMore(this)
    }

    private fun pullToRefresh() {
        swipeContainer.setOnRefreshListener {
            Handler().postDelayed({
                adapter.clear()
                adapter.addAll(timeLineItemsStorage.apply {
                    shuffle()
                    subList(0, LAST_ITEM_POSITION)
                })
                swipeContainer.isRefreshing = false
            }, DELAY_TIME)
        }
    }

    private fun initData() {
        timeLineItemsStorage = mutableListOf()
        timeLineItemsStorage.apply {
            add(
                TimeLineItem(
                    "Artemis",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image1,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Annabella",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image2,
                    (0..RANDOM_INDEX_MAX).random(),
                    true
                )
            )
            add(
                TimeLineItem(
                    "Angela",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image3,
                    (0..RANDOM_INDEX_MAX).random(),
                    true
                )
            )
            add(
                TimeLineItem(
                    "Thekla",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image4,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Calantha",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image5,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Charmaine",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image6,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Christabel",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image7,
                    (0..RANDOM_INDEX_MAX).random(),
                    true
                )
            )
            add(
                TimeLineItem(
                    "Cosima",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image8,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Drusilla",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image9,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Ermintrude",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image10,
                    (0..RANDOM_INDEX_MAX).random(),
                    true
                )
            )
            add(
                TimeLineItem(
                    "Esperanza",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image11,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Euphemia",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image12,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Genevieve",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image13,
                    (0..RANDOM_INDEX_MAX).random(),
                    true
                )
            )
            add(
                TimeLineItem(
                    "Guinevere",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image14,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Iphigenia",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image15,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Jocasta",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image16,
                    (0..RANDOM_INDEX_MAX).random(),
                    true
                )
            )
            add(
                TimeLineItem(
                    "Keelin",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image17,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Kelsey",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image18,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Lysandra",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image19,
                    (0..RANDOM_INDEX_MAX).random(),
                    true
                )
            )
            add(
                TimeLineItem(
                    "Martha",
                    R.mipmap.ic_launcher_round,
                    R.mipmap.image20,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
        }
    }
}
