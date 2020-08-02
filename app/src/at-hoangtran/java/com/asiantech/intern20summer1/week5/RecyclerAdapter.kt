package com.asiantech.intern20summer1.week5

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-hoangtran`.loading_layout.view.*
import kotlinx.android.synthetic.`at-hoangtran`.recycler_item.view.*

class RecyclerAdapter(
    recyclerView: RecyclerView,
    internal var activity: Activity,
    internal var itemRecycler: MutableList<ItemRecycler?>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_ITEM_TYPE = 1
        private const val VIEW_LOADING_TYPE = 0
    }

    internal var onHeartClicked: (Int) -> Unit = {}
    private var loadMore: LoadMore? = null
    private var isLoading = false
    private var lastVisibleItem = 0

    init {
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!isLoading && lastVisibleItem == itemRecycler.size - 1) {
                    loadMore?.onLoadMore()
                    isLoading = true
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_ITEM_TYPE) {
            val view =
                LayoutInflater.from(activity).inflate(R.layout.recycler_view_layout, parent, false)
            TimeLineViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(activity).inflate(R.layout.loading_layout, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount() = itemRecycler.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TimeLineViewHolder) {
            holder.onBindData(position)
        }
    }

    override fun getItemViewType(position: Int) =
        if (itemRecycler[position] == null) VIEW_LOADING_TYPE else VIEW_ITEM_TYPE

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar = itemView.progressBar
    }

    inner class TimeLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBindData(position: Int) {
            val item = itemRecycler[position]
            val imgItem = itemView.img_item
            val imgHeart = itemView.img_heart
            val tvHeartCount = itemView.tv_heart_count
            val tvContent = itemView.tv_content

            item.let {
                Glide.with(itemView).load(it?.heart).into(imgHeart)
                Glide.with(itemView).load(it?.image).into(imgItem)
                tvContent.text = it?.content
                tvHeartCount.text = it?.heartCount.toString()
                if (it != null) {
                    if (it.heartStatus) {
                        imgHeart.setImageResource(R.mipmap.heart)
                    } else {
                        imgHeart.setImageResource(R.mipmap.heartless)
                    }
                }
                imgHeart.setOnClickListener {
                    onHeartClicked(position)
                }
            }
        }
    }

    fun setLoaded() {
        isLoading = false
    }

    fun setLoadMore(loadMore: LoadMore) {
        this.loadMore = loadMore
    }

    fun clear() {
        itemRecycler.clear()
        notifyDataSetChanged()
    }

    fun addAll(itemRecyclerNew: MutableList<ItemRecycler>) {
        itemRecycler.addAll(itemRecyclerNew)
        notifyDataSetChanged()
    }
}
