package com.asiantech.intern20summer1.week5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.recycler_item.view.*

class RecyclerAdapter(private val itemList: MutableList<ItemRecycler>) :
    RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>() {

    internal var onItemClick: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapter.ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: RecyclerAdapter.ItemViewHolder, position: Int) {
        holder.bind()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.img_item
        var heart: ImageView = itemView.img_heart
        var heartCount: TextView = itemView.tv_heart_count
        var content: TextView = itemView.tv_content

        init {
            heart.setOnClickListener {
                onItemClick.invoke(adapterPosition)
            }
        }

        fun bind() {
            itemList[adapterPosition].let {
                val stHeartCount = "${it.heartCount} likes"
                image.setImageResource(it.image)
                if (it.heartStatus) {
                    heart.setImageResource(R.mipmap.heart)
                } else {
                    heart.setImageResource(R.mipmap.heartless)
                }
                content.text = it.content
                heartCount.text = stHeartCount
            }
        }
    }
}
