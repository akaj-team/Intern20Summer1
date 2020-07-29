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

    internal var onIsLikedImageViewClick: (position: Int) -> Unit = {}

    private var timeLineItems: MutableList<TimeLineItem>
    private var context: Context

    constructor(context: Context, timeLineItems: MutableList<TimeLineItem>) {
        this.timeLineItems = timeLineItems
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val timeLineView = inflater.inflate(R.layout.item_list_time_line, parent, false)
        return TimeLineViewHolder(timeLineView)
    }

    override fun onBindViewHolder(viewHolder: TimeLineViewHolder, position: Int) {
        val timeLineItem = timeLineItems[position]

        // Set itemView based on views and data model
        val nameTextView: TextView? = viewHolder.nameTextView
        nameTextView?.text = timeLineItem.name

        val nameTextViewAbove: TextView? = viewHolder.nameTextViewAbove
        nameTextViewAbove?.text = timeLineItem.name

        val imageImageView = viewHolder.imageImageView
        when {
            position % 3 == 0 -> imageImageView?.setImageResource(R.drawable.cat3)
            position % 2 == 0 -> imageImageView?.setImageResource(R.drawable.cat2)
            else -> imageImageView?.setImageResource(R.drawable.cat)
        }

        val contentTextView = viewHolder.contentTextView
        contentTextView?.text = timeLineItem.content

        val isLikedImageView = viewHolder.isLikedImageView
        if (timeLineItem.isLiked) {
            isLikedImageView?.setImageResource(R.drawable.ic_heart_filled)
        } else {
            isLikedImageView?.setImageResource(R.drawable.ic_heart)
        }
        isLikedImageView?.setOnClickListener {
            onIsLikedImageViewClick.invoke(position)
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
            isPluralLikeTextView?.setTypeface(Typeface.DEFAULT, Typeface.ITALIC)
        } else {
            likesTextView?.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return timeLineItems.size
    }
}
