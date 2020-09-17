package com.asiantech.intern20summer1.w11.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w11.data.api.ApiClient
import com.asiantech.intern20summer1.w11.data.models.PostItem
import com.asiantech.intern20summer1.w11.utils.AppUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.`at-huybui`.w10_item_recycler_post.view.*

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 01/09/2020.
 * This is RecyclerAdapter class. It is adapter for recycler view to display posts
 */

class RecyclerAdapter(private var mutableList: List<Any> = emptyList()) :
    RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder<*>>() {

    companion object {
        private const val TYPE_POST = 0
        private const val TYPE_LOAD = 1
        private const val BLACK_HEART_SYMBOL = "\uD83D\uDDA4"
    }

    internal var onLikeClicked: (position: Int) -> Unit = {}
    internal var onMenuClicked: (view: View, position: Int) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_POST -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.w10_item_recycler_post, parent, false)
                ItemViewHolder(view)
            }
            TYPE_LOAD -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.w10_item_recycler_load_more, parent, false)
                LoadMoreViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount() = mutableList.size

    override fun getItemViewType(position: Int): Int {
        return when {
            position < (mutableList.size - 1) -> {
                TYPE_POST
            }
            position == (mutableList.size - 1) -> {
                TYPE_LOAD
            }
            else -> {
                throw IllegalArgumentException(position.toString())
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = mutableList[position]
        if (holder is ItemViewHolder) {
            holder.bind(element as PostItem)
        } else if (holder is LoadMoreViewHolder) {
            holder.bind(element as PostItem)
        }
    }

    class LoadMoreViewHolder(itemView: View) : BaseViewHolder<PostItem>(itemView) {
        override fun bind(item: PostItem) {
        }
    }

    inner class ItemViewHolder(itemView: View) : BaseViewHolder<PostItem>(itemView) {
        private var image = itemView.imgImage
        private var iconLike = itemView.btnLike
        private var content = itemView.tvContent
        private var createdAt = itemView.tvCreatedAt
        private var likeCount = itemView.tvLikeCount
        private var btnMenu = itemView.btnMenu
        private var progressBar = itemView.progressBar

        init {
            iconLike.setOnClickListener {
                onLikeClicked.invoke(adapterPosition)
            }

            btnMenu.setOnClickListener {
                onMenuClicked.invoke(btnMenu, adapterPosition)
            }
        }

        override fun bind(item: PostItem) {
            val id = AppUtils().getIdUser(itemView.context)
            if (id == item.user_id) {
                btnMenu.visibility = View.VISIBLE
            } else {
                btnMenu.visibility = View.INVISIBLE
            }
            loadImage(item)
            val text = itemView.context.getString(R.string.w10_text_likes)
            val stLikeCount = "$BLACK_HEART_SYMBOL ${item.like_count} $text"
            content.text = item.content
            likeCount.text = stLikeCount
            createdAt.text = AppUtils().convertDate(item.created_at)
            if (item.like_flag) {
                iconLike.setImageResource(R.drawable.w10_ic_heart_red)
            } else {
                iconLike.setImageResource(R.drawable.w10_ic_heart_transparent)
            }
        }

        private fun loadImage(item: PostItem) {
            progressBar.visibility = View.VISIBLE
            Glide.with(itemView)
                .load(ApiClient.IMAGE_URL + item.image)
                .placeholder(R.drawable.w10_img_placeholder)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.INVISIBLE
                        return false
                    }
                })
                .into(image)
        }
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }
}
