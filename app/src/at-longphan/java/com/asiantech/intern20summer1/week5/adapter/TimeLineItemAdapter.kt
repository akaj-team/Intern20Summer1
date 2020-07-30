package com.asiantech.intern20summer1.week5.adapter

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
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
        val userNameTextView: TextView? = viewHolder.userNameTextView
        userNameTextView?.text = timeLineItem.userName

        val imageImageView = viewHolder.imageImageView
        when {
            position % 3 == 0 -> imageImageView?.setImageResource(R.drawable.img_cat3)
            position % 2 == 0 -> imageImageView?.setImageResource(R.drawable.img_cat2)
            else -> imageImageView?.setImageResource(R.drawable.img_cat)
        }

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
            isPluralLikeTextView?.text =
                context.getString(R.string.text_view_plural_like_description)
        } else {
            isPluralLikeTextView?.text =
                context.getString(R.string.text_view_not_plural_like_description)
        }
        if (timeLineItem.likes == 0) {
            likesTextView?.visibility = View.INVISIBLE
            isPluralLikeTextView?.text =
                context.getString(R.string.text_view_first_like_description)
            isPluralLikeTextView?.setTypeface(Typeface.DEFAULT, Typeface.ITALIC)
        } else {
            likesTextView?.visibility = View.VISIBLE
        }

        val userNameAndCaption = timeLineItem.userName + " " + timeLineItem.caption
        val spannableString = SpannableString(userNameAndCaption)
        timeLineItem.userName?.length?.let {
            spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, it, 0)
        }
        val captionTextView: TextView? = viewHolder.captionTextView
        captionTextView?.text = spannableString
    }

    override fun getItemCount(): Int {
        return timeLineItems.size
    }
}
