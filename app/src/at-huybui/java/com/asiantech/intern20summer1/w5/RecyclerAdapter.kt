package com.asiantech.intern20summer1.w5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        holder.bind(currentItem)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name1: TextView = itemView.tvName1W5
        var name2: TextView = itemView.tvName2W5
        var image: ImageView = itemView.imgImageW5
        var iconHeart: ImageView = itemView.imgIconHeartW5
        var information: TextView = itemView.tvInformationW5
        var amountHeart: TextView = itemView.tvAmountHeartW5

        fun bind(item: ItemRecycler) {
            name1.text = item.name
            name2.text = item.name
            information.text = item.infomation
            amountHeart.text = "  ${item.amountHeart} likes"
            if (item.statusHeart) {
                iconHeart.setImageResource(R.drawable.ic_heart_red)
            } else {
                iconHeart.setImageResource(R.drawable.ic_heart_transparent)
            }
            image.setImageResource(item.image)

            iconHeart.setOnClickListener {
                    item.statusHeart = !item.statusHeart
            }
        }

    }
}