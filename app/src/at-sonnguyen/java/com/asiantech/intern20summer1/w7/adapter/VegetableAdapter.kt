package com.asiantech.intern20summer1.w7.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.database.Vegetable

class VegetableAdapter(private val vegetables: MutableList<Vegetable>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.w7_item_line, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount() = vegetables.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? RecyclerViewHolder)?.binData()
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvPlantName)
        private val tvDateGrow: TextView = itemView.findViewById(R.id.tvGrowZoneNumber)
        private val tvDateHarvest: TextView = itemView.findViewById(R.id.tvDateHarvest)
        private val imgVegetable: ImageView = itemView.findViewById(R.id.imgVegetable)

        internal fun binData() {
            val vegetable = vegetables[adapterPosition]
            tvName.text = vegetable.name
            tvDateGrow.text = vegetable.plantTime
            tvDateHarvest.text = vegetable.collectTime
            imgVegetable.setImageResource(vegetable.image)
        }
    }
}
