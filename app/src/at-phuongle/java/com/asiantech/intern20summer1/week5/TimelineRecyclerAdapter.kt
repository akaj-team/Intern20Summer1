package com.asiantech.intern20summer1.week5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.model.TimelineItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.`at-phuongle`.layout_timeline_list_item.view.*

class TimelineRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<TimelineItem> = ArrayList()

    companion object {
        const val MAX_ITEM = 10
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TimelineViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_timeline_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return MAX_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TimelineViewHolder -> {
                holder.bind(items.random())
            }
        }
    }

    fun submitList(timeLineList: List<TimelineItem>) {
        items = timeLineList
    }

    class TimelineViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timeLineAvatar = itemView.imgAvatar
        private val timeLineName = itemView.tvName
        private val timeLineImage = itemView.imgImage
        private val timeLineLike = itemView.tvLike
        private val timeLineComment = itemView.tvComment

        fun bind(timeLine: TimelineItem) {
            timeLineName.text = timeLine.name
            timeLineLike.text = timeLine.like + " likes"
            timeLineComment.text = "${timeLine.name}  ${timeLine.comment}"

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Picasso.with(itemView.context)
                .load(timeLine.avatar)
                .into(timeLineAvatar)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(timeLine.image)
                .into(timeLineImage)
        }
    }
}
