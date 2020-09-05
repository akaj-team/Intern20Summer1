package com.asiantech.intern20summer1.w10.adapter

import android.graphics.drawable.Drawable
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.models.PostItem
import com.asiantech.intern20summer1.w10.utils.AppUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.`at-huybui`.w10_item_recycler_post.view.*

class RecyclerAdapter(private val mutableList: MutableList<PostItem>) :
    RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>() {

    companion object {
        private const val BLACK_HEART_SYMBOL = "\uD83D\uDDA4"
        internal const val URL_IMAGE = "http://at-a-trainning.000webhostapp.com/images/"
    }

    internal var onLikeClicked: (position: Int) -> Unit = {}
    internal var onMenuClicked: (view: View, position: Int) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.w10_item_recycler_post, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount() = mutableList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


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

        fun bindData() {
            mutableList[adapterPosition].let { item ->
                val id = AppUtils().getIdUser(itemView.context)
                if (id == item.user_id) {
                    btnMenu.visibility = View.VISIBLE
                } else {
                    btnMenu.visibility = View.INVISIBLE
                }
                d("adapter", "id = $id | user = ${item.user_id} | $adapterPosition")
                loadImage(item)
                val stLikeCount = "$BLACK_HEART_SYMBOL ${item.like_count} likes"
                content.text = item.content
                likeCount.text = stLikeCount
                createdAt.text = AppUtils().convertDate(item.created_at)
                if (item.like_flag) {
                    iconLike.setImageResource(R.drawable.w10_ic_heart_red)
                } else {
                    iconLike.setImageResource(R.drawable.w10_ic_heart_transparent)
                }
            }
        }

        private fun loadImage(item: PostItem) {
            progressBar.visibility = View.VISIBLE
            Glide.with(itemView)
                .load(URL_IMAGE + item.image)
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
}
