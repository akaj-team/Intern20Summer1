package com.asiantech.intern20summer1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.model.NewPost
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.`at-vuongphan`.item_new_feed.view.*

class ItemFeedAdapter(private val newFeeds: MutableList<NewPost>) :
    RecyclerView.Adapter<ItemFeedAdapter.NewFeedViewHolder>() {
    internal var onItemClicked: (position: Int) -> Unit = {}
    internal var onItemDeleteClicked: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewFeedViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_new_feed, parent, false)
        return NewFeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewFeedViewHolder, position: Int) {
        (holder as? NewFeedViewHolder)?.bindData()
    }

    override fun getItemCount() = newFeeds.size

    inner class NewFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.tvName
        private val imgAvatar: CircleImageView = itemView.imgAvatar
        private val imgMain: ImageView = itemView.imgMainPhoto
        private val imgHeart: ImageView = itemView.imgHeart
        private val tvLike: TextView = itemView.tvLike
        private val tvStatus: TextView = itemView.tvStatus
        private val tvNameStatus: TextView = itemView.tvNameStatus
        private val tvFoodName: TextView = itemView.tvFoodName
        private val imgDelete: ImageView = itemView.imgOption

        init {
            imgHeart.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
            imgDelete.setOnClickListener {
                onItemDeleteClicked.invoke(adapterPosition)
            }
        }

        fun bindData() {
            newFeeds[adapterPosition].let {
                tvName.text = it.content
                Glide.with(itemView)
                    .load(it.image)
                    .placeholder(R.drawable.tran_thi_duyen)
                    .into(imgAvatar)
                Glide.with(itemView)
                    .load(it.image)
                    .placeholder(R.drawable.tran_thi_duyen)
                    .into(imgMain)
                if (it.like_flag) imgHeart.setImageResource(R.drawable.ic_hearted) else imgHeart.setImageResource(
                    R.drawable.ic_heart
                )
                tvStatus.text = it.content
                tvNameStatus.text = it.created_at
                tvLike.text =
                    itemView.context.getString(R.string.text_view_text_like_number, it.like_count)
            }
        }
    }
}