package com.asiantech.intern20summer1.week5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.recycler_item.view.*

class RecyclerAdapter(private val itemList: MutableList<ItemRecycler>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ItemRecycler = itemList[position]
        holder.imageItem = item.image
        holder.imageHeartStatus = item.heartStatus
        holder.heartCount.text = item.heartCount.toString()
        holder.content.text = item.content
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageItem: ImageView = itemView.img_item
        var imageHeartStatus = itemView.img_heart
        var heartCount = itemView.tv_heart_count
        var content = itemView.tv_content
    }

}

