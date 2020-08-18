package com.asiantech.intern20summer1.w7.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.database.PlantDatabase
import com.asiantech.intern20summer1.w7.database.data.Cultivation
import com.asiantech.intern20summer1.w7.extension.getDateHarvest
import com.asiantech.intern20summer1.w7.extension.isComingHarvest
import com.asiantech.intern20summer1.w7.extension.isLackedWater
import com.asiantech.intern20summer1.w7.extension.isWormed
import kotlin.random.Random

class PlantAdapter(private val cultivationList: MutableList<Cultivation>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        val relativeLayoutResource = arrayOf(
            R.drawable.w7_bg_relative_layout_1,
            R.drawable.w7_bg_relative_layout_2,
            R.drawable.w7_bg_relative_layout_3,
            R.drawable.w7_bg_relative_layout_4
        )
    }

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
        private val rlItemView: RelativeLayout = itemView.findViewById(R.id.rlItemView)
        private val imgWormedPlantFragment: ImageView = itemView.findViewById(R.id.imgWormedPlant)
        private val imgLackedWaterPlantFragment: ImageView =
            itemView.findViewById(R.id.imgLackedWaterPlant)
        private val imgComingHarvestPlant: ImageView =
            itemView.findViewById(R.id.imgComingHarvestPlant)

        init {
            itemView.setOnClickListener {
                onItemClick.invoke(cultivationList[adapterPosition].id)
            }
        }

        internal fun binData() {
            cultivationList[adapterPosition].let { cultivation ->
                database?.plantDao()?.getPlant(cultivation.plantId)?.let { plant ->
                    rlItemView.setBackgroundResource(
                        relativeLayoutResource[Random.nextInt(
                            1,
                            relativeLayoutResource.size
                        )]
                    )
                    val cultivationDate = cultivation.dateCultivation
                    val harvestDate = getDateHarvest(cultivation.dateCultivation, plant)
                    tvCultivationDate.text =
                        itemView.context.getString(R.string.w7_text_cultivation, cultivationDate)
                    tvHarvestDate.text =
                        itemView.context.getString(R.string.w7_text_harvest, harvestDate)
                    tvPlantName.text = plant.name
                    plant.imageUri?.let {
                        imgPlant.setImageURI(Uri.parse(it))
                    }
                    if (isWormed(plant, cultivation)) {
                        imgWormedPlantFragment.visibility = View.VISIBLE
                    } else {
                        imgWormedPlantFragment.visibility = View.INVISIBLE
                    }
                    if (isLackedWater(plant, cultivation)) {
                        imgLackedWaterPlantFragment.visibility = View.VISIBLE
                    } else {
                        imgLackedWaterPlantFragment.visibility = View.INVISIBLE
                    }
                    if (isComingHarvest(plant, cultivation)) {
                        imgComingHarvestPlant.visibility = View.VISIBLE
                    } else {
                        imgComingHarvestPlant.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }
}
