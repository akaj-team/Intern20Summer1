package com.asiantech.intern20summer1.w5

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-huybui`.activity_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {

    companion object {
        private const val SDK_VERSION = 23
        private const val DELAYS_PROGRESS = 2000L
        private const val MAX_RANDOM = 1000
    }

    private val fruitLists: MutableList<ItemRecycler> = mutableListOf()
    private val adapterRecycler = RecyclerAdapter(fruitLists)
    private var isLoadMore = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        handleStatusBarFollowSdk()
        initFruitData()
        initAdapter()
        handleForLoadMoreAndRefreshListener()
    }

    private fun initAdapter() {
        adapterRecycler.onItemClicked = { position ->
            fruitLists[position].let {
                it.isStatusHeart = !it.isStatusHeart
                if (it.isStatusHeart) {
                    it.amountHeart++
                } else {
                    it.amountHeart--
                }
                playSoundPop()
            }
            adapterRecycler.notifyItemChanged(position, null)
            (fruitRecyclerView.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                false
        }
        fruitRecyclerView.adapter = adapterRecycler
        fruitRecyclerView.layoutManager = LinearLayoutManager(this)
        fruitRecyclerView.setHasFixedSize(true)
    }

    private fun handleForLoadMoreAndRefreshListener() {
        swipeRefreshContainer.setOnRefreshListener {
            Handler().postDelayed({
                fruitLists.clear()
                initFruitData()
                adapterRecycler.notifyDataSetChanged()
                swipeRefreshContainer.isRefreshing = false
                playSoundPop()
            }, DELAYS_PROGRESS)
        }

        fruitRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoadMore && (lastVisibleItem == fruitLists.size - 1)) {
                    progressBar.visibility = View.VISIBLE
                    Handler().postDelayed({
                        isLoadMore = true
                        progressBar.visibility = View.INVISIBLE
                        initFruitData()
                        adapterRecycler.notifyDataSetChanged()
                    }, DELAYS_PROGRESS)
                }
                isLoadMore = false
            }
        })
    }

    private fun initFruitData() {
        fruitLists.add(
            ItemRecycler(
                resources.getString(R.string.w5_name_buoi_da_xanh),
                (0..MAX_RANDOM).random(),
                R.drawable.img_fruit_buoi_da_xanh,
                false,
                resources.getString(R.string.w5_information_buoi_da_xanh)
            )
        )
        fruitLists.add(
            ItemRecycler(
                resources.getString(R.string.w5_name_chom_chom),
                (0..MAX_RANDOM).random(),
                R.drawable.img_fruit_chom_chom,
                false,
                resources.getString(R.string.w5_information_chom_chom)
            )
        )
        fruitLists.add(
            ItemRecycler(
                resources.getString(R.string.w5_name_dua),
                (0..MAX_RANDOM).random(),
                R.drawable.img_fruit_dua,
                false,
                resources.getString(R.string.w5_information_dua)
            )
        )
        fruitLists.add(
            ItemRecycler(
                resources.getString(R.string.w5_name_dua_nuoc),
                (0..MAX_RANDOM).random(),
                R.drawable.img_fruit_dua_nuoc,
                false,
                resources.getString(R.string.w5_information_dua_nuoc)
            )
        )
        fruitLists.add(
            ItemRecycler(
                resources.getString(R.string.w5_name_mang_cut),
                (0..MAX_RANDOM).random(),
                R.drawable.img_fruit_mang_cut,
                false,
                resources.getString(R.string.w5_information_mang_cut)
            )
        )
        fruitLists.add(
            ItemRecycler(
                resources.getString(R.string.w5_name_quyt_dong_thap),
                (0..MAX_RANDOM).random(),
                R.drawable.img_fruit_quyt_dong_thap,
                false,
                resources.getString(R.string.w5_information_quyt_dong_thap)
            )
        )
        fruitLists.add(
            ItemRecycler(
                resources.getString(R.string.w5_name_sau_rieng),
                (0..MAX_RANDOM).random(),
                R.drawable.img_fruit_sau_rieng,
                false,
                resources.getString(R.string.w5_information_sau_rieng)
            )
        )
        fruitLists.add(
            ItemRecycler(
                resources.getString(R.string.w5_name_thanh_tra),
                (0..MAX_RANDOM).random(),
                R.drawable.img_fruit_thanh_tra,
                false,
                resources.getString(R.string.w5_information_thanh_tra)
            )
        )
        fruitLists.add(
            ItemRecycler(
                resources.getString(R.string.w5_name_mang_cut),
                (0..MAX_RANDOM).random(),
                R.drawable.img_fruit_mang_cut,
                false,
                resources.getString(R.string.w5_information_mang_cut)
            )
        )
        fruitLists.add(
            ItemRecycler(
                resources.getString(R.string.w5_name_vu_sua),
                (0..MAX_RANDOM).random(),
                R.drawable.img_fruit_vu_sua,
                false,
                resources.getString(R.string.w5_information_vu_sua)
            )
        )
        fruitLists.shuffle()
    }

    private fun handleStatusBarFollowSdk() {
        if (Build.VERSION.SDK_INT >= SDK_VERSION) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = ContextCompat.getColor(baseContext, R.color.status_bar)
            }
        }
    }

    private fun playSoundPop() {
        val soundPop = MediaPlayer.create(this, R.raw.facebook_pop)
        soundPop.start()
    }
}
