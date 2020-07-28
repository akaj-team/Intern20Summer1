package com.asiantech.intern20summer1.week5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.`at-hoangtran`.recycler_item.view.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemList: List<ItemRecycler> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                holder.bind(itemList[position])
            }
        }
    }

    fun submitList(list: List<ItemRecycler>) {
        itemList = list
    }

    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageItem: ImageView = itemView.img_item
        private val imageHeartStatus: ImageView = itemView.img_heart
        private val heartCount: TextView = itemView.tv_heart_count
        private val content: TextView = itemView.tv_content

        fun bind(itemRecycler: ItemRecycler) {
            heartCount.setText(itemRecycler.heartCount)
            content.text = itemRecycler.content

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error((R.drawable.ic_launcher_background))
            Glide.with(itemView.context).applyDefaultRequestOptions(requestOptions)
                .load(itemRecycler.image).into(imageItem)
            Glide.with(itemView.context).applyDefaultRequestOptions(requestOptions)
                .load(itemRecycler.heartStatus).into(imageHeartStatus)
        }
    }
}

