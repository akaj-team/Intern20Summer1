package com.asiantech.intern20summer1.week5

import android.annotation.SuppressLint
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
    private var items: MutableList<TimelineItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TimelineViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_timeline_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TimelineViewHolder -> {
                holder.bind(items[position])
                holder.itemView.imgLike.setOnClickListener {
                    if (!items[position].isLiked) {
                        holder.itemView.imgLike.setImageResource(R.drawable.like)
                        items[position].like++
                        holder.itemView.tvLike.text =
                            items[position].like.toString() + " " + "likes"
                        items[position].isLiked = true
                    } else {
                        holder.itemView.imgLike.setImageResource(R.drawable.heart)
                        items[position].like--
                        holder.itemView.tvLike.text =
                            items[position].like.toString() + " " + "likes"
                        items[position].isLiked = false
                    }
                }
            }
        }
    }

    fun submitList(timeLineList: MutableList<TimelineItem>) {
        items = timeLineList
    }

    class TimelineViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timeLineAvatar = itemView.imgAvatar
        private val timeLineName = itemView.tvName
        private val timeLineImage = itemView.imgImage
        private val timeLineLike = itemView.tvLike
        private val timeLineComment = itemView.tvComment

        @SuppressLint("SetTextI18n")
        fun bind(timeLine: TimelineItem) {
            timeLineName.text = timeLine.name
            timeLineLike.text =
                timeLine.like.toString() + " " + "likes"
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
