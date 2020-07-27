package com.asiantech.intern20summer1.week5.other

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R

class TimeLineViewHolder : RecyclerView.ViewHolder {
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
