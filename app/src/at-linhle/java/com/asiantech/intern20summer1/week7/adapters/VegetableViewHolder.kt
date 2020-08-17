package com.asiantech.intern20summer1.week7.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.data.AppDatabase
import com.asiantech.intern20summer1.week7.extensions.PlantStatus
import com.asiantech.intern20summer1.week7.models.Cultivation
import kotlinx.android.synthetic.`at-linhle`.item_list_vegetable.view.*

class VegetableViewHolder(
    internal var plantList: MutableList<Cultivation?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var appDatabase: AppDatabase? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        appDatabase = AppDatabase.getInstance(parent.context)
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_vegetable, parent, false)
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
            vegetableItem?.let { cultivation ->
                appDatabase?.getPlantDao()?.getPlant(cultivation.plantId)?.let {
                    tvVegetableName.text = it.name
                    tvDateGrow.text = cultivation.dateCultivation
                    tvDateHarvest.text =
                        PlantStatus().getDateHarvest(cultivation.dateCultivation, it)
                    imgVegetable.setImageURI(Uri.parse(it.imageUrl))
                }
            }
        }
    }
}
