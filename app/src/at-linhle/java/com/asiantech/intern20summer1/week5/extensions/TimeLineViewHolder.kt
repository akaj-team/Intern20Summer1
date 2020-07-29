package com.asiantech.intern20summer1.week5.extensions

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week5.interfaces.LoadMore
import com.asiantech.intern20summer1.week5.models.TimeLineItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-linhle`.item_list_time_line.view.*
import kotlinx.android.synthetic.`at-linhle`.loading_layout.view.*

class TimeLineViewHolder(
    recyclerView: RecyclerView,
    internal var activity: Activity,
    internal var timeLineItems: MutableList<TimeLineItem?>
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
                if (!isLoading && lastVisibleItem == timeLineItems.size - 1) {
                    loadMore?.onLoadMore()
                    isLoading = true
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_ITEM_TYPE) {
            val view =
                LayoutInflater.from(activity).inflate(R.layout.item_list_time_line, parent, false)
            TimeLineViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(activity).inflate(R.layout.loading_layout, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount() = timeLineItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TimeLineViewHolder) {
            holder.onBindData(position)
        } else if (holder is LoadingViewHolder) {
            holder.progressBar.isIndeterminate = true
        }
    }

    override fun getItemViewType(position: Int) =
        if (timeLineItems[position] == null) VIEW_LOADING_TYPE else VIEW_ITEM_TYPE

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar = itemView.progressBar
    }

    inner class TimeLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBindData(position: Int) {
            val timeLineItem = timeLineItems[position]
            val imgAvatar = itemView.imgAvatar
            val tvUserName = itemView.tvUserName
            val tvProfileName = itemView.tvProfileName
            val imgTimeLine = itemView.imgTimeLine
            val imgLike = itemView.imgLike
            val tvCountLike = itemView.tvCountLike

            timeLineItem?.let {
                Glide.with(itemView).load(it.avatar).circleCrop().into(imgAvatar)
                Glide.with(itemView).load(it.picture).into(imgTimeLine)
                tvUserName.text = it.userName
                tvProfileName.text = it.userName
                tvCountLike.text = it.countLike.toString()
                if (it.isLiked) {
                    imgLike.setImageResource(R.drawable.ic_heart_bold_active)
                } else {
                    imgLike.setImageResource(R.drawable.ic_heart_bold)
                }

                imgLike.setOnClickListener {
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
        timeLineItems.clear()
        notifyDataSetChanged()
    }

    fun addAll(timeLineItemNew: List<TimeLineItem?>) {
        timeLineItems.addAll(timeLineItemNew)
        notifyDataSetChanged()
    }
}
