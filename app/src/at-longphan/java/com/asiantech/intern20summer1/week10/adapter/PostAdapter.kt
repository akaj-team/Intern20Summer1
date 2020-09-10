package com.asiantech.intern20summer1.week10.adapter

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week10.model.Post
import com.asiantech.intern20summer1.week10.other.IMAGE_FOLDER_URL
import com.asiantech.intern20summer1.week10.viewholder.PostViewHolder
import com.bumptech.glide.Glide

class PostAdapter : RecyclerView.Adapter<PostViewHolder> {

    companion object {
        private const val EXTRA_STRING_LENGTH = 5
    }

    internal var onIsLikedImageViewClick: (position: Int) -> Unit = {}
    internal var onPostOptionImageViewClick: (idPost: Int, image: String, content: String) -> Unit =
        { _: Int, _: String, _: String -> }
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
        val userIdTextView = viewHolder.fullNameTextView
        userIdTextView?.text = "User " + postItem.userId

        val imageImageView = viewHolder.imageImageView
        if (imageImageView != null) {
            handleImageImageView(imageImageView, postItem)
        }

        val likeFlagImageView = viewHolder.likeFlagImageView
        if (likeFlagImageView != null) {
            handleLikeFlagImageView(likeFlagImageView, postItem, position)
        }

        val likeCountTextView = viewHolder.likeCountTextView
        likeCountTextView?.text = postItem.likeCount.toString()

        val isPluralLikeTextView = viewHolder.isPluralLikeTextView
        if (isPluralLikeTextView != null) {
            if (likeCountTextView != null) {
                handleIsPluralLikeTextView(isPluralLikeTextView, likeCountTextView, postItem)
            }
        }

        val contentTextView = viewHolder.contentTextView
        if (contentTextView != null) {
            handleContentTextView(contentTextView, postItem)
        }

        val postOptionImageView = viewHolder.postOptionImageView
        if (postOptionImageView != null) {
            handlePostOptionImageView(postOptionImageView, postItem)
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    private fun handlePostOptionImageView(postOptionImageView: ImageView, postItem: Post) {
        postOptionImageView.setOnClickListener {
            postItem.id?.let { postId ->
                postItem.image?.let { image ->
                    postItem.content?.let { content ->
                        onPostOptionImageViewClick.invoke(postId, image, content)
                    }
                }
            }
        }
    }

    private fun handleImageImageView(imageImageView: ImageView, postItem: Post) {
        imageImageView.let {
            if (!postItem.image.isNullOrEmpty()) {
                it.visibility = View.VISIBLE
                val imageUrl = IMAGE_FOLDER_URL.plus(postItem.image)
                Glide.with(context).load(imageUrl).into(it)
            } else {
                it.visibility = View.GONE
            }
        }
    }

    private fun handleLikeFlagImageView(
        likeFlagImageView: ImageView,
        postItem: Post,
        position: Int
    ) {
        if (postItem.likeFlag) {
            likeFlagImageView.setImageResource(R.drawable.ic_heart_filled)
        } else {
            likeFlagImageView.setImageResource(R.drawable.ic_heart)
        }
        likeFlagImageView.setOnClickListener {
            onIsLikedImageViewClick.invoke(position)
        }
    }

    private fun handleIsPluralLikeTextView(
        isPluralLikeTextView: TextView,
        likeCountTextView: View,
        postItem: Post
    ) {

        when {
            postItem.likeCount > 1 -> {
                isPluralLikeTextView.text =
                    context.getString(R.string.text_view_plural_like_description)
                isPluralLikeTextView.setTypeface(Typeface.DEFAULT_BOLD, Typeface.NORMAL)
                likeCountTextView.visibility = View.VISIBLE
            }
            postItem.likeCount == 1 -> {
                isPluralLikeTextView.text =
                    context.getString(R.string.text_view_not_plural_like_description)
                isPluralLikeTextView.setTypeface(Typeface.DEFAULT_BOLD, Typeface.NORMAL)
                likeCountTextView.visibility = View.VISIBLE
            }
            postItem.likeCount == 0 -> {
                likeCountTextView.visibility = View.INVISIBLE
                isPluralLikeTextView.text =
                    context.getString(R.string.text_view_first_like_description)
                isPluralLikeTextView.setTypeface(Typeface.DEFAULT, Typeface.ITALIC)
            }
        }
    }

    private fun handleContentTextView(contentTextView: TextView, postItem: Post) {
        val userIdAndContent = "User " + postItem.userId + " " + postItem.content
        val spannableString = SpannableString(userIdAndContent)
        postItem.userId?.length?.let {
            spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, it + EXTRA_STRING_LENGTH, 0)
        }
        contentTextView.text = spannableString
    }
}
