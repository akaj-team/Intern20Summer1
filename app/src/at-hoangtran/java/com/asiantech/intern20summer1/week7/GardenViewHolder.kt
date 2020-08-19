package com.asiantech.intern20summer1.week7

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.cultivation_item.view.*

class GardenViewHolder(
    internal var plantList: MutableList<Cultivation?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var appDatabase: AppDatabase? = null
    internal var onItemClicked: (id: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GardenViewHolder {
        appDatabase = AppDatabase.getInstance(parent.context)
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cultivation_item, parent, false)
        return GardenViewHolder(view)
    }

    override fun getItemCount() = plantList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GardenViewHolder) {
            holder.bind(position)
        }
    }

    inner class GardenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                plantList[adapterPosition]?.id?.let { it1 -> onItemClicked.invoke(it1) }
            }
        }
        fun bind(position: Int) {
            val gardenItem = plantList[position]
            val tvVegetableName = itemView.tvVegetableName
            val imgVegetableHarvest = itemView.imgVegetableHarvest
            val imgVegetableWorm = itemView.imgVegetableWorm
            val imgVegetableWater = itemView.imgVegetableWater
            val tvPlantTime = itemView.tvPlantTime
            val tvHarvestTime = itemView.tvHarvestTime
            val imgVegetable = itemView.imgVegetable
            gardenItem.let { cultivation ->
                appDatabase?.getPlantDAO()?.getPlant(cultivation?.plantId).let {
                    tvVegetableName.text = it?.name
                    tvPlantTime.text = cultivation?.dateCultivation
                    tvHarvestTime.text =
                        PlantStatus().getHarvestDate(cultivation?.dateCultivation, it)
                    imgVegetable.setImageURI((Uri.parse(it?.imageUrl)))
                    if (PlantStatus().isWormed(it, cultivation)) {
                        imgVegetableWorm.visibility = View.VISIBLE
                        imgVegetableWater.visibility = View.VISIBLE
                        imgVegetableHarvest.visibility = View.VISIBLE
                    }
                    if (PlantStatus().isWatered(it, cultivation)) {
                        imgVegetableWater.visibility = View.VISIBLE
                        imgVegetableWorm.visibility = View.VISIBLE
                        imgVegetableHarvest.visibility = View.VISIBLE
                    }
                    if (PlantStatus().isHarvest(it, cultivation)) {
                        imgVegetableHarvest.visibility = View.VISIBLE
                        imgVegetableWater.visibility = View.VISIBLE
                        imgVegetableWorm.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}
