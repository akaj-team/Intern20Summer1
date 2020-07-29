package com.asiantech.intern20summer1.week5.other

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R

class TimeLineViewHolder : RecyclerView.ViewHolder {

    internal var nameTextView: TextView? = null
    internal var nameTextViewAbove: TextView? = null
    internal var imageImageView: ImageView? = null
    internal var contentTextView: TextView? = null
    internal var isLikedImageView: ImageView? = null
    internal var likesTextView: TextView? = null
    internal var isPluralLikeTextView: TextView? = null

    constructor(itemView: View) : super(itemView) {
        nameTextView = itemView.findViewById(R.id.tvNameUser) as? TextView
        nameTextViewAbove = itemView.findViewById(R.id.tvNameUserAbove) as? TextView
        imageImageView = itemView.findViewById(R.id.imgImagePost) as? ImageView
        contentTextView = itemView.findViewById(R.id.tvContent) as? TextView
        isLikedImageView = itemView.findViewById(R.id.imgIsLiked) as? ImageView
        likesTextView = itemView.findViewById(R.id.tvLikes) as? TextView
        isPluralLikeTextView = itemView.findViewById(R.id.tvLikeDescription) as? TextView
    }
}
