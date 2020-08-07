package com.asiantech.intern20summer1.w7.main.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.model.TreeClass
import kotlinx.android.synthetic.`at-huybui`.recycler_farm_item.view.*

class RecyclerAdapter(private val mutableList: MutableList<TreeClass>) :
    RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>() {

    internal var onItemClicked: (position: Int) -> Unit = {}

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
        private var dateCultivation: TextView = itemView.tvDateCultivation
        private var dateHarvest: TextView = itemView.tvDateHarvest
        private var image: ImageView = itemView.imgVegetable
        private var iconStatusWorm = itemView.imgStatusWorm

        init {
            itemView.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }

        fun bindData() {
            mutableList[adapterPosition].let { item ->
                name.text = item.name
                dateCultivation.text = item.dateCultivation
                dateHarvest.text = item.dateCultivation
                image.setImageResource(item.image)
                if (item.statusWorm) {
                    iconStatusWorm.visibility = View.VISIBLE
                } else {
                    iconStatusWorm.visibility = View.INVISIBLE
                }
            }
        }
    }
}
