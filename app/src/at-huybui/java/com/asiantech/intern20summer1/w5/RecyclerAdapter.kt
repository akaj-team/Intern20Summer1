package com.asiantech.intern20summer1.w5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-huybui`.recycler_item.view.*


class RecyclerAdapter(private val mutableList: MutableList<ItemRecycler>) :
    RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount() = mutableList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = mutableList[position]
        holder.name1.text = currentItem.name
        holder.name2.text = currentItem.name
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name1 = itemView.tvName1W5
        var name2 = itemView.tvName2W5

        fun bind(item: ItemRecycler) {
        }
    }
}