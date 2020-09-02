package com.asiantech.intern20summer1.w10.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.models.PostItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-huybui`.w10_item_recycler_post.view.*

class RecyclerAdapter(private val mutableList: MutableList<PostItem>) :
    RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>() {

    companion object {
        private const val BLACK_HEART_SYMBOL = "\uD83D\uDDA4"
    }

    internal var onItemClicked: (position: Int) -> Unit = {}

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


        private var image: ImageView = itemView.imgImage_Item_w10
        private var iconLike: ImageView = itemView.imgHeart_Item_w10
        private var content: TextView = itemView.tvContent_Item_w10
        private var createdAt: TextView = itemView.tvCreatedAt_Item_w10
        private var likeCount: TextView = itemView.tvLikeCount_Item_w10

        init {
            iconLike.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }

        fun bindData() {
            mutableList[adapterPosition].let { item ->

                Glide.with(itemView)
                    .load(item.image)
                    .into(image)
                val stLikeCount = "$BLACK_HEART_SYMBOL ${item.like_count} likes"
                content.text = item.content
                likeCount.text = stLikeCount
                createdAt.text = item.created_at
                if (item.like_flag) {
                    iconLike.setImageResource(R.drawable.w10_ic_heart_red)
                } else {
                    iconLike.setImageResource(R.drawable.w10_ic_heart_transparent)
                }
            }
        }
    }
}
