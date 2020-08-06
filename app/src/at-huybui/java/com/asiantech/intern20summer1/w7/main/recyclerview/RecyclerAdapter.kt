package com.asiantech.intern20summer1.w7.main.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-huybui`.recycler_farm_item.view.*

class RecyclerAdapter(private val mutableList: MutableList<RecyclerItem>) :
    RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapter.ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_farm_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount() = mutableList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var name: TextView = itemView.tvNameVegetable
        private var information: TextView = itemView.tvInformationVegetable
        private var img: ImageView = itemView.imgVegetable
        fun bindData() {
            mutableList[adapterPosition].let { item ->
                name.text = item.name
                information.text = item.infomation
                img.setImageResource(item.image)
            }
        }
    }

}
