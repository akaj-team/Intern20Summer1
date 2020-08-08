package com.asiantech.intern20summer1.w7.main.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.model.PlantModel
import kotlinx.android.synthetic.`at-huybui`.recycler_farm_item.view.*

class RecyclerAdapter(private val mutableList: MutableList<PlantModel>) :
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
        private var imgPlant: ImageView = itemView.imgVegetable

        init {
            itemView.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }

        fun bindData() {
            mutableList[adapterPosition].let { item ->
                name.text = item.name
                dateHarvest.text = item.imageUrl
                imgPlant.setImageURI(Uri.parse(item.imageUri))
            }
        }
    }
}
