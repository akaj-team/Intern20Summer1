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
import com.asiantech.intern20summer1.week10.other.IMAGE_FOLDER_URL
import com.asiantech.intern20summer1.week10.viewholder.PostViewHolder
import com.bumptech.glide.Glide

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
        val postItem = posts[position]

        // Set itemView based on views and data model
        val userIdTextView: TextView? = viewHolder.fullNameTextView
        userIdTextView?.text = postItem.userId

        val imageImageView = viewHolder.imageImageView
        /*when {
            position % PICTURE_3 == 0 -> imageImageView?.setImageResource(R.drawable.img_cat3)
            position % PICTURE_2 == 0 -> imageImageView?.setImageResource(R.drawable.img_cat2)
            else -> imageImageView?.setImageResource(R.drawable.img_cat)
        }*/
        imageImageView?.let {
            Glide.with(it).load(IMAGE_FOLDER_URL.plus(postItem.image)).into(it)
        }

        val likeFlagImageView = viewHolder.likeFlagImageView
        if (postItem.likeFlag) {
            likeFlagImageView?.setImageResource(R.drawable.ic_heart_filled)
        } else {
            likeFlagImageView?.setImageResource(R.drawable.ic_heart)
        }
        likeFlagImageView?.setOnClickListener {
            onIsLikedImageViewClick.invoke(position)
        }

        val likeCountTextView = viewHolder.likeCountTextView
        likeCountTextView?.text = postItem.likeCount.toString()

        val isPluralLikeTextView = viewHolder.isPluralLikeTextView

        when {
            postItem.likeCount > 1 -> {
                isPluralLikeTextView?.text =
                    context.getString(R.string.text_view_plural_like_description)
                isPluralLikeTextView?.setTypeface(Typeface.DEFAULT_BOLD, Typeface.NORMAL)
                likeCountTextView?.visibility = View.VISIBLE
            }
            postItem.likeCount == 1 -> {
                isPluralLikeTextView?.text =
                    context.getString(R.string.text_view_not_plural_like_description)
                isPluralLikeTextView?.setTypeface(Typeface.DEFAULT_BOLD, Typeface.NORMAL)
                likeCountTextView?.visibility = View.VISIBLE
            }
            postItem.likeCount == 0 -> {
                likeCountTextView?.visibility = View.INVISIBLE
                isPluralLikeTextView?.text =
                    context.getString(R.string.text_view_first_like_description)
                isPluralLikeTextView?.setTypeface(Typeface.DEFAULT, Typeface.ITALIC)
            }
        }

        val userIdAndContent = postItem.userId + " " + postItem.content
        val spannableString = SpannableString(userIdAndContent)
        postItem.userId?.length?.let {
            spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, it, 0)
        }
        val contentTextView: TextView? = viewHolder.contentTextView
        contentTextView?.text = spannableString
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}
