package com.asiantech.intern20summer1.week5.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week5.model.TimeLineItem
import com.asiantech.intern20summer1.week5.other.TimeLineViewHolder

class TimeLineItemAdapter : RecyclerView.Adapter<TimeLineViewHolder> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {
        val timeLineItem = parent.context
        val inflater = LayoutInflater.from(timeLineItem)

        val timeLineView = inflater.inflate(R.layout.time_line_item_row, parent, false)

        return TimeLineViewHolder(timeLineView)
    }

    override fun onBindViewHolder(viewHolder: TimeLineViewHolder, position: Int) {
        val timeLineItem = TimeLineItem(timeLineItems[position])

        // Set itemView based on views and data model
        val nameTextView: TextView? = viewHolder.nameTextView
        nameTextView?.text = timeLineItem.name

        val imageImageView = viewHolder.imageImageView
        imageImageView?.setImageResource(R.drawable.cat)


        val isLikedImageView = viewHolder.isLikedImageView
        if (timeLineItem.isLiked) {
            isLikedImageView?.setImageResource(R.drawable.ic_heart_filled)
        } else {
            isLikedImageView?.setImageResource(R.drawable.ic_heart)
        }
        isLikedImageView?.setOnClickListener {
            onIsLikedImageViewClick.invoke(position)
            notifyItemChanged(position)
        }

        val likesTextView = viewHolder.likesTextView
        likesTextView?.text = timeLineItem.likes.toString()

        val isPluralLikeTextView = viewHolder.isPluralLikeTextView
        if (timeLineItem.likes > 1) {
            isPluralLikeTextView?.text = "likes"
        } else {
            isPluralLikeTextView?.text = "like"
        }

        if (timeLineItem.likes == 0) {
            likesTextView?.visibility = View.INVISIBLE
            isPluralLikeTextView?.text = "Be the first to like this"
            isPluralLikeTextView?.setTypeface(null,Typeface.ITALIC)
        } else {
            likesTextView?.visibility = View.VISIBLE
            isPluralLikeTextView?.setTypeface(null,Typeface.NORMAL)
        }

        // Comment & Share
        val commentImageView = viewHolder.comment
        commentImageView?.setOnClickListener {
            if (it.isInTouchMode) {
                commentImageView.setImageResource(R.drawable.ic_comment_filled)
            } else {
                commentImageView.setImageResource(R.drawable.ic_comment)
            }
        }
    }

    override fun getItemCount(): Int {
        return timeLineItems.size
    }

    internal var onIsLikedImageViewClick: (position: Int) -> Unit = {}
    private var timeLineItems: MutableList<TimeLineItem>
    private var context: Context

    constructor(context: Context, timeLineItems: MutableList<TimeLineItem>) {
        this.timeLineItems = timeLineItems
        this.context = context
    }

    private fun getContext(): Context {
        return context
    }
}
