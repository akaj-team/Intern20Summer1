package com.asiantech.intern20summer1.week5.other

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R

class TimeLineViewHolder : RecyclerView.ViewHolder {

    internal var userNameTextView: TextView? = null
    internal var imageImageView: ImageView? = null
    internal var isLikedImageView: ImageView? = null
    internal var imgLikesImageView: ImageView? = null
    internal var likesTextView: TextView? = null
    internal var isPluralLikeTextView: TextView? = null
    internal var captionTextView: TextView? = null

    constructor(itemView: View) : super(itemView) {
        userNameTextView = itemView.findViewById(R.id.tvUserNameWeek5) as? TextView
        imageImageView = itemView.findViewById(R.id.imgImagePost) as? ImageView
        isLikedImageView = itemView.findViewById(R.id.imgIsLiked) as? ImageView
        imgLikesImageView = itemView.findViewById(R.id.imgLikes) as? ImageView
        likesTextView = itemView.findViewById(R.id.tvLikes) as? TextView
        isPluralLikeTextView = itemView.findViewById(R.id.tvIsPluralLike) as? TextView
        captionTextView = itemView.findViewById(R.id.tvCaptionWeek5) as? TextView
    }
}
