package com.asiantech.intern20summer1.w10.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.activity.HomeActivity.Companion.IMAGE_FOLDER_URL
import com.asiantech.intern20summer1.w10.data.Post
import com.bumptech.glide.Glide

class RecyclerViewAdapter(private val items: MutableList<Post>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal var onLikeClicked:(position : Int) -> Unit = {}

    internal var onItemClicked: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.w10_newfeed_item,parent,false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? RecyclerViewHolder)?.binData()
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgDelete : ImageView = itemView.findViewById(R.id.imgDelete)
        private val imgItem: ImageView = itemView.findViewById(R.id.imgItem)
        private val imgLike: ImageView = itemView.findViewById(R.id.imgLike)
        private val tvLikeCount: TextView = itemView.findViewById(R.id.tvLikeCount)
        private val tvContent: TextView = itemView.findViewById(R.id.tvContent)

        init {
            imgItem.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
            imgDelete.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
            imgLike.setOnClickListener {
                onLikeClicked.invoke(adapterPosition)
            }
        }

        fun binData() {
            val item = items[adapterPosition]
            tvContent.text = item.content
            Glide.with(itemView)
                .load(IMAGE_FOLDER_URL+item.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imgItem)
            tvLikeCount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_black, 0, 0, 0)
            tvLikeCount.text = String.format(
                itemView.context.getString(
                    R.string.w10_item_number_of_like,
                    item.like_count
                )
            )
            if (item.like_flag){
                imgLike.setImageResource(R.drawable.ic_hearted)
            }else{
                imgLike.setImageResource(R.drawable.ic_heart)
            }
        }
    }
}
