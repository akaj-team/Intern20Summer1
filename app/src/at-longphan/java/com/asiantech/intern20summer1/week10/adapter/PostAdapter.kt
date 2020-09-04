package com.asiantech.intern20summer1.week10.adapter

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
import com.asiantech.intern20summer1.week10.model.Post
import com.asiantech.intern20summer1.week10.other.PostViewHolder

class PostAdapter : RecyclerView.Adapter<PostViewHolder> {

    companion object {
        private const val PICTURE_2 = 2
        private const val PICTURE_3 = 3
    }

    internal var onIsLikedImageViewClick: (position: Int) -> Unit = {}

    private var posts: MutableList<Post>
    private var context: Context

    constructor(context: Context, posts: MutableList<Post>) {
        this.posts = posts
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val postView = inflater.inflate(R.layout.item_list_post_w10, parent, false)
        return PostViewHolder(postView)
    }

    override fun onBindViewHolder(viewHolder: PostViewHolder, position: Int) {
        val timeLineItem = posts[position]

        // Set itemView based on views and data model
        val userNameTextView: TextView? = viewHolder.userNameTextView
        userNameTextView?.text = timeLineItem.userName

        val imageImageView = viewHolder.imageImageView
        when {
            position % PICTURE_3 == 0 -> imageImageView?.setImageResource(R.drawable.img_cat3)
            position % PICTURE_2 == 0 -> imageImageView?.setImageResource(R.drawable.img_cat2)
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

        when {
            timeLineItem.likes > 1 -> {
                isPluralLikeTextView?.text =
                    context.getString(R.string.text_view_plural_like_description)
                isPluralLikeTextView?.setTypeface(Typeface.DEFAULT_BOLD, Typeface.NORMAL)
                likesTextView?.visibility = View.VISIBLE
            }
            timeLineItem.likes == 1 -> {
                isPluralLikeTextView?.text =
                    context.getString(R.string.text_view_not_plural_like_description)
                isPluralLikeTextView?.setTypeface(Typeface.DEFAULT_BOLD, Typeface.NORMAL)
                likesTextView?.visibility = View.VISIBLE
            }
            timeLineItem.likes == 0 -> {
                likesTextView?.visibility = View.INVISIBLE
                isPluralLikeTextView?.text =
                    context.getString(R.string.text_view_first_like_description)
                isPluralLikeTextView?.setTypeface(Typeface.DEFAULT, Typeface.ITALIC)
            }
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
        return posts.size
    }
}
