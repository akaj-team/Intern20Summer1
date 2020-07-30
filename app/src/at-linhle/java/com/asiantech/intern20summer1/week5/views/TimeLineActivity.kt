package com.asiantech.intern20summer1.week5.views

import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week5.extensions.TimeLineViewHolder
import com.asiantech.intern20summer1.week5.extensions.changeColorStatusBar
import com.asiantech.intern20summer1.week5.interfaces.LoadMore
import com.asiantech.intern20summer1.week5.models.TimeLineItem
import kotlinx.android.synthetic.`at-linhle`.activity_time_line.*
import kotlin.random.Random

class TimeLineActivity : AppCompatActivity(), LoadMore {

    companion object {
        private const val LAST_ITEM_POSITION = 10
        private const val RANDOM_INDEX_MAX = 100
        private const val DELAY_TIME = 2000L
    }

    private lateinit var timeLineItems: MutableList<TimeLineItem?>
    private lateinit var timeLineItemsStorage: MutableList<TimeLineItem?>
    private lateinit var adapter: TimeLineViewHolder

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)
        changeColorStatusBar(window, titleColor)
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
                timeLineItems.add(timeLineItemsStorage[Random.nextInt(timeLineItemsStorage.size)])
            }
            adapter.notifyDataSetChanged()
            adapter.setLoaded()
        }, DELAY_TIME)
    }

    private fun initAdapter() {
        timeLineItemsStorage.shuffle()
        timeLineItems = timeLineItemsStorage.subList(0, LAST_ITEM_POSITION)
        adapter = TimeLineViewHolder(recyclerViewContainer, this, timeLineItems)
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
                    R.drawable.img_profile,
                    R.drawable.ic_01,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Annabella",
                    R.drawable.img_profile,
                    R.drawable.ic_02,
                    (0..RANDOM_INDEX_MAX).random(),
                    true
                )
            )
            add(
                TimeLineItem(
                    "Angela",
                    R.drawable.img_profile,
                    R.drawable.ic_03,
                    (0..RANDOM_INDEX_MAX).random(),
                    true
                )
            )
            add(
                TimeLineItem(
                    "Thekla",
                    R.drawable.img_profile,
                    R.drawable.ic_04,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Calantha",
                    R.drawable.img_profile,
                    R.drawable.ic_05,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Charmaine",
                    R.drawable.img_profile,
                    R.drawable.ic_06,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Christabel",
                    R.drawable.img_profile,
                    R.drawable.ic_07,
                    (0..RANDOM_INDEX_MAX).random(),
                    true
                )
            )
            add(
                TimeLineItem(
                    "Cosima",
                    R.drawable.img_profile,
                    R.drawable.ic_08,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Drusilla",
                    R.drawable.img_profile,
                    R.drawable.ic_09,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Ermintrude",
                    R.drawable.img_profile,
                    R.drawable.ic_10,
                    (0..RANDOM_INDEX_MAX).random(),
                    true
                )
            )
            add(
                TimeLineItem(
                    "Esperanza",
                    R.drawable.img_profile,
                    R.drawable.ic_11,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Euphemia",
                    R.drawable.img_profile,
                    R.drawable.ic_12,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Genevieve",
                    R.drawable.img_profile,
                    R.drawable.ic_13,
                    (0..RANDOM_INDEX_MAX).random(),
                    true
                )
            )
            add(
                TimeLineItem(
                    "Guinevere",
                    R.drawable.img_profile,
                    R.drawable.ic_14,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Iphigenia",
                    R.drawable.img_profile,
                    R.drawable.ic_15,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Jocasta",
                    R.drawable.img_profile,
                    R.drawable.ic_16,
                    (0..RANDOM_INDEX_MAX).random(),
                    true
                )
            )
            add(
                TimeLineItem(
                    "Keelin",
                    R.drawable.img_profile,
                    R.drawable.ic_17,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Kelsey",
                    R.drawable.img_profile,
                    R.drawable.ic_18,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Lysandra",
                    R.drawable.ic_19,
                    R.drawable.img_profile,
                    (0..RANDOM_INDEX_MAX).random(),
                    true
                )
            )
            add(
                TimeLineItem(
                    "Martha",
                    R.drawable.img_profile,
                    R.drawable.ic_20,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Miranda",
                    R.drawable.img_profile,
                    R.drawable.ic_21,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Neala",
                    R.drawable.img_profile,
                    R.drawable.ic_22,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Olwen",
                    R.drawable.img_profile,
                    R.drawable.ic_23,
                    (0..RANDOM_INDEX_MAX).random(),
                    true
                )
            )
            add(
                TimeLineItem(
                    "Philomena",
                    R.drawable.img_profile,
                    R.drawable.ic_24,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Theodora",
                    R.drawable.img_profile,
                    R.drawable.ic_25,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Tryphena",
                    R.drawable.img_profile,
                    R.drawable.ic_26,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Veronica",
                    R.drawable.img_profile,
                    R.drawable.ic_27,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Xavia",
                    R.drawable.img_profile,
                    R.drawable.ic_28,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Winifred",
                    R.drawable.img_profile,
                    R.drawable.ic_29,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
            add(
                TimeLineItem(
                    "Ula",
                    R.drawable.img_profile,
                    R.drawable.ic_30,
                    (0..RANDOM_INDEX_MAX).random(),
                    false
                )
            )
        }
    }
}
