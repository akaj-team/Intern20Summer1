package com.asiantech.intern20summer1.week7

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.cultivation_item.view.*

class GardenViewHolder(
    recyclerView: RecyclerView,
    val activity: Activity,
    val plantList: MutableList<Plant?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GardenViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.cultivation_item, parent, false)
        return GardenViewHolder(view as RecyclerView)
    }

    override fun getItemCount() = plantList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GardenViewHolder) {
            holder.bind(position)
        }
    }

    inner class GardenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            val gardenItem = plantList[position]
            val tvVegetableName = itemView.tvVegetableName
            val imgVegetableName = itemView.imgVegetableName
            val tvPlantTime = itemView.tvPlantTime
            val tvCultivationTime = itemView.tvCultivationTime
            val imgVegetable = itemView.imgVegetable
            gardenItem.let {  }
        }
    }
}
