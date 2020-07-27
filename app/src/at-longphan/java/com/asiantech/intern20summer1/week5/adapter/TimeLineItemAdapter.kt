package com.asiantech.intern20summer1.week5.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week5.model.TimeLineItem

class TimeLineItemAdapter : RecyclerView.Adapter<TimeLineItemAdapter.ViewHolder> {
    class ViewHolder : RecyclerView.ViewHolder {
        var nameTextView: TextView? = null
        var imageImageView: ImageView? = null
        var isLikedImageView: ImageView? = null
        var likesTextView: TextView? = null
        var isPluralLikeTextView: TextView? = null

        // Comment & Share
        var comment: ImageView? = null
        var share: ImageView? = null

        constructor(itemView: View) : super(itemView) {
            nameTextView = itemView.findViewById(R.id.tvNameUser) as? TextView
            imageImageView = itemView.findViewById(R.id.imgImagePost) as? ImageView
            isLikedImageView = itemView.findViewById(R.id.imgIsLiked) as? ImageView
            likesTextView = itemView.findViewById(R.id.tvLikes) as? TextView
            isPluralLikeTextView = itemView.findViewById(R.id.tvLikeDescription) as? TextView
            //Comment & Share
            comment = itemView.findViewById((R.id.imgComment))
            share = itemView.findViewById((R.id.imgShare))
        }
    }

    override fun getItemCount(): Int {
        return timeLineItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var timeLineItem = parent.context
        var inflater = LayoutInflater.from(timeLineItem)

        var timeLineView = inflater.inflate(R.layout.time_line_item_row, parent, false)

        return ViewHolder(timeLineView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val timeLineItem: TimeLineItem = TimeLineItem(timeLineItems[position])

        // Set itemView based on views and data model
        var nameTextView: TextView? = viewHolder.nameTextView
        nameTextView?.text = timeLineItem.name

        var imageImageView = viewHolder.imageImageView
        /*val url = URL(timeLineItem.imageUri)
        val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        imageImageView?.setImageBitmap(bitmap)*/

        /*if (imageImageView != null) {
            Glide.with(Activity()).load(timeLineItem.imageUri).into(imageImageView)
        }*/
        imageImageView?.setImageResource(R.drawable.cat)

        var isLikedImageView = viewHolder.isLikedImageView
        if (timeLineItem.isLiked) {
            isLikedImageView?.setImageResource(R.drawable.ic_heart_filled)
        } else {
            isLikedImageView?.setImageResource(R.drawable.ic_heart)
        }

        var likesTextView = viewHolder.likesTextView
        likesTextView?.text = timeLineItem.likes.toString()

        var isPluralLikeTextView = viewHolder.isPluralLikeTextView
        if (timeLineItem.likes > 1) {
            isPluralLikeTextView?.text = "likes"
        } else {
            isPluralLikeTextView?.text = "like"
        }

        // Comment & Share
        var commentImageView = viewHolder.comment
        commentImageView?.setOnClickListener {
            if (it.isInTouchMode) {
                commentImageView.setImageResource(R.drawable.ic_comment_filled)
            } else {
                commentImageView.setImageResource(R.drawable.ic_comment)
            }
        }
    }

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
