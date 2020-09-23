package com.asiantech.intern20summer1.w12.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w12.activity.HomeActivity.Companion.IMAGE_FOLDER_URL
import com.asiantech.intern20summer1.w12.data.model.Post
import com.bumptech.glide.Glide

class RecyclerViewAdapter(private val items: MutableList<Post?>, internal val userId: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal var onLikeClicked: (position: Int) -> Unit = {}

    internal var onUpdateClicked: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.w12_newfeed_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? RecyclerViewHolder)?.binData()
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgEdit: ImageView = itemView.findViewById(R.id.imgEdit)
        private val imgItem: ImageView = itemView.findViewById(R.id.imgItem)
        private val imgLike: ImageView = itemView.findViewById(R.id.imgLike)
        private val tvCreatedTime: TextView = itemView.findViewById(R.id.tvCreatedTime)
        private val tvLikeCount: TextView = itemView.findViewById(R.id.tvLikeCount)
        private val tvContent: TextView = itemView.findViewById(R.id.tvContent)

        init {
            imgEdit.setOnClickListener {
                onUpdateClicked.invoke(adapterPosition)
            }
            imgLike.setOnClickListener {
                onLikeClicked.invoke(adapterPosition)
            }
        }

        fun binData() {
            val item = items[adapterPosition]
            item?.let {
                tvContent.text = item.content
                Glide.with(itemView)
                    .load(IMAGE_FOLDER_URL + item.image)
                    .into(imgItem)
                tvLikeCount.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_black_heart,
                    0,
                    0,
                    0
                )
                if (userId == item.userId) {
                    imgEdit.visibility = View.VISIBLE
                } else {
                    imgEdit.visibility = View.INVISIBLE
                }
                tvCreatedTime.text = item.create_at
                tvLikeCount.text = String.format(
                    itemView.context.getString(
                        R.string.w10_item_number_of_like,
                        item.like_count
                    )
                )
                if (item.like_flag) {
                    imgLike.setImageResource(R.drawable.ic_hearted)
                } else {
                    imgLike.setImageResource(R.drawable.ic_heart)
                }
            }
        }
    }
}
