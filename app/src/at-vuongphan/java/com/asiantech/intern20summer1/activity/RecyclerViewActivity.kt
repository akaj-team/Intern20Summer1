package com.asiantech.intern20summer1.activity

import android.graphics.Color
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
import com.asiantech.intern20summer1.adapter.RecyclerAdapter
import com.asiantech.intern20summer1.data.ItemRecycler
import com.asiantech.intern20summer1.data.avatar
import com.asiantech.intern20summer1.data.images
import com.asiantech.intern20summer1.data.user
import kotlinx.android.synthetic.`at-vuongphan`.activity_recycler_view.*
import kotlin.random.Random

@Suppress("DEPRECATION")
class RecyclerViewActivity : AppCompatActivity() {
    private val exampleLists: MutableList<ItemRecycler> = mutableListOf()
    var adapterRecycler = RecyclerAdapter(exampleLists)
    private var isLoadMore = false

    companion object {
        private const val SDK_VERSION = 23
        private const val END_LOOP = 9
        private const val AVATAR_START = 0
        private const val AVATAR_END = 4
        private const val IMAGE_START = 0
        private const val IMAGE_END = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        handleStatusBarFollowSdk()
        initAdapter()
        exampleLists.addAll(newData())
        initScrollViewLoadMore()
        initRefreshPost()
    }

    private fun initAdapter() {
        initListenerButton()
        recyclerViewActivity.adapter = adapterRecycler
        recyclerViewActivity.layoutManager = LinearLayoutManager(this)
        recyclerViewActivity.setHasFixedSize(true)
    }

    private fun initListenerButton() {
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
            (recyclerViewActivity.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                false
        }
    }

    private fun reloadData() {
        adapterRecycler.notifyDataSetChanged()
    }

    private fun newData(): MutableList<ItemRecycler> {
        val posts = mutableListOf<ItemRecycler>()
        val title = listOf(
            R.string.title_name_one,
            R.string.title_name_two,
            R.string.title_name_three,
            R.string.title_name_four,
            R.string.title_name_five,
            R.string.title_name_six
        )
        for (i in 0..END_LOOP) {
            posts.add(
                ItemRecycler(
                    avatar[Random.nextInt(AVATAR_START, AVATAR_END)],
                    resources.getString(title.random()),
                    images[Random.nextInt(IMAGE_START, IMAGE_END)],
                    false,
                    0,
                    resources.getString(user.random()),
                    resources.getString(R.string.information)
                )
            )
        }
        return posts
    }

    private fun initScrollViewLoadMore() {
        recyclerViewActivity.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerViewActivity.layoutManager as? LinearLayoutManager
                val lastItemIndex = layoutManager?.findLastVisibleItemPosition()
                if (!isLoadMore) {
                    if (lastItemIndex == exampleLists.size - 1) {
                        isLoadMore = true
                        progressBarMain.visibility = View.VISIBLE
                        Handler().postDelayed({
                            progressBarMain.visibility = View.INVISIBLE
                            exampleLists.addAll(newData())
                            reloadData()
                            isLoadMore = false
                        }, 2000)
                    }
                }
            }
        })
    }

    private fun initRefreshPost() {
        swipeRefreshContainer.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this@RecyclerViewActivity,
                R.color.colorPrimary
            )
        )
        swipeRefreshContainer.setColorSchemeColors(Color.WHITE)
        swipeRefreshContainer.setOnRefreshListener {
            Handler().postDelayed({
                exampleLists.clear()
                exampleLists.addAll(newData())
                reloadData()
                swipeRefreshContainer.isRefreshing = false
            }, 1000)
        }
    }

    private fun handleStatusBarFollowSdk() {
        if (Build.VERSION.SDK_INT >= SDK_VERSION) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}
