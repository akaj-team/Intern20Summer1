package com.asiantech.intern20summer1.w5

import android.os.Bundle
import android.os.Handler
import android.util.Log.d
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-huybui`.activity_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {

    companion object {
        private const val DELAYS_PROGRESSBAR = 2000L
    }

    private val exampleLists: MutableList<ItemRecycler> = mutableListOf()
    var adapterRecycler = RecyclerAdapter(exampleLists)
    private var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        initData()
        initAdapter()
    }


    var counter = 0
    private fun initAdapter() {
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!loading && (lastVisibleItem == exampleLists.size - 1)) {
                        progressBar.visibility = View.VISIBLE
                        Handler().postDelayed({
                            loading = true
                            progressBar.visibility = View.INVISIBLE
                            initData()
                            adapterRecycler.notifyDataSetChanged()
                        }, DELAYS_PROGRESSBAR)

                }
                counter++
                d("XXXX","size: ${exampleLists.size} :dy = $dy: last: $lastVisibleItem" )
                loading = false
            }
        })


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
        for (i in 1..5) {
            handleAdd(i)
        }
    }


    private fun handleAdd(item: Int) {
        when (item) {
            1 -> exampleLists.add(
                ItemRecycler(
                    resources.getString(R.string.w5_name_buoi_da_xanh),
                    (0..1000).random(),
                    R.drawable.img_fruit_buoi_da_xanh,
                    false,
                    resources.getString(R.string.w5_information_buoi_da_xanh)
                )
            )
            2 -> exampleLists.add(
                ItemRecycler(
                    resources.getString(R.string.w5_name_chom_chom),
                    (0..1000).random(),
                    R.drawable.img_fruit_chom_chom,
                    false,
                    resources.getString(R.string.w5_information_chom_chom)
                )
            )
            3 -> exampleLists.add(
                ItemRecycler(
                    resources.getString(R.string.w5_name_dua),
                    (0..1000).random(),
                    R.drawable.img_fruit_dua,
                    false,
                    resources.getString(R.string.w5_information_dua)
                )
            )
            4 -> exampleLists.add(
                ItemRecycler(
                    resources.getString(R.string.w5_name_dua_nuoc),
                    (0..1000).random(),
                    R.drawable.img_fruit_dua_nuoc,
                    false,
                    resources.getString(R.string.w5_information_dua_nuoc)
                )
            )
            5 -> exampleLists.add(
                ItemRecycler(
                    resources.getString(R.string.w5_name_mang_cut),
                    (0..1000).random(),
                    R.drawable.img_fruit_mang_cut,
                    false,
                    resources.getString(R.string.w5_information_mang_cut)
                )
            )
            6 -> exampleLists.add(
                ItemRecycler(
                    resources.getString(R.string.w5_name_quyt_dong_thap),
                    (0..1000).random(),
                    R.drawable.img_fruit_quyt_dong_thap,
                    false,
                    resources.getString(R.string.w5_information_quyt_dong_thap)
                )
            )
            7 -> exampleLists.add(
                ItemRecycler(
                    resources.getString(R.string.w5_name_sau_rieng),
                    (0..1000).random(),
                    R.drawable.img_fruit_sau_rieng,
                    false,
                    resources.getString(R.string.w5_information_sau_rieng)
                )
            )
            8 -> exampleLists.add(
                ItemRecycler(
                    resources.getString(R.string.w5_name_thanh_tra),
                    (0..1000).random(),
                    R.drawable.img_fruit_thanh_tra,
                    false,
                    resources.getString(R.string.w5_information_thanh_tra)
                )
            )
            9 -> exampleLists.add(
                ItemRecycler(
                    resources.getString(R.string.w5_name_vu_sua),
                    (0..1000).random(),
                    R.drawable.img_fruit_vu_sua,
                    false,
                    resources.getString(R.string.w5_information_vu_sua)
                )
            )
        }
    }

    interface loadMoreItem {
        fun loadMore()
    }
}
