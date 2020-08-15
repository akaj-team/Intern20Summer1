package com.asiantech.intern20summer1.w7.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.database.PlantDatabase
import com.asiantech.intern20summer1.w7.database.data.Cultivation
import com.asiantech.intern20summer1.w7.extension.getDateHarvest

class VegetableAdapter(private val cultivationList: MutableList<Cultivation>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var database: PlantDatabase? = null
    internal var onItemClick: (id: Int?) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        database = PlantDatabase.getInstance(parent.context)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.w7_item_line, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount() = cultivationList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? RecyclerViewHolder)?.binData()
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPlantName: TextView = itemView.findViewById(R.id.tvPlantName)
        private val tvCultivationDate: TextView = itemView.findViewById(R.id.tvCultivationDate)
        private val tvHarvestDate: TextView = itemView.findViewById(R.id.tvHarvestDate)
        private val imgPlant: ImageView = itemView.findViewById(R.id.imgPlant)

        internal fun binData() {
            cultivationList[adapterPosition].let { cultivation ->
                database?.plantDao()?.getPlant(cultivation.plantId)?.let { plant ->
                    val cultivationDate = cultivation.dateCultivation
                    val harvestDate = getDateHarvest(cultivation.dateCultivation, plant)
                    tvCultivationDate.text =
                        itemView.context.getString(R.string.w7_text_cultivation, cultivationDate)
                    tvHarvestDate.text =
                        itemView.context.getString(R.string.w7_text_harvest, harvestDate)
                    tvPlantName.text = plant.name
//                    imgPlant.setImageURI(Uri.parse(plant.imageUri))
                }
            }
        }
    }
}
