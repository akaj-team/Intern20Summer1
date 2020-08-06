package com.asiantech.intern20summer1.week7.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.models.Plant
import kotlinx.android.synthetic.`at-linhle`.item_list_vegetable.view.*

class VegetableViewHolder(
    recyclerView: RecyclerView,
    internal var activity: Activity,
    internal var plantList: MutableList<Plant?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(activity).inflate(R.layout.item_list_vegetable, parent, false)
        return VegetableViewHolder(view)
    }

    override fun getItemCount() = plantList.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VegetableViewHolder) {
            holder.onBindData(position)
        }
    }

    inner class VegetableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBindData(position: Int) {
            val vegetableItem = plantList[position]
            val tvVegetableName = itemView.tvVegetableName
            val imgVegetableStatus = itemView.imgVegetableStatus
            val imgVegetable = itemView.imgVegetable
            val tvDateGrow = itemView.tvDateGrow
            val tvDateHarvest = itemView.tvDateHarvest

            vegetableItem?.let {

            }
        }
    }
}
