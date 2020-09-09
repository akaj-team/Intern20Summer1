package com.asiantech.intern20summer1.week10.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R

class PostViewHolder : RecyclerView.ViewHolder {

    internal var fullNameTextView: TextView? = null
    internal var imageImageView: ImageView? = null
    internal var likeFlagImageView: ImageView? = null
    internal var likeCountTextView: TextView? = null
    internal var isPluralLikeTextView: TextView? = null
    internal var contentTextView: TextView? = null
    internal var postOptionImageView: ImageView? = null


    constructor(itemView: View) : super(itemView) {
        fullNameTextView = itemView.findViewById(R.id.tvUserNameWeek10) as? TextView
        imageImageView = itemView.findViewById(R.id.imgImagePostWeek10) as? ImageView
        likeFlagImageView = itemView.findViewById(R.id.imgIsLikedWeek10) as? ImageView
        likeCountTextView = itemView.findViewById(R.id.tvLikesWeek10) as? TextView
        isPluralLikeTextView = itemView.findViewById(R.id.tvIsPluralLikeWeek10) as? TextView
        contentTextView = itemView.findViewById(R.id.tvCaptionWeek10) as? TextView
        postOptionImageView = itemView.findViewById(R.id.imgPostOptionW10) as? ImageView
    }
}
