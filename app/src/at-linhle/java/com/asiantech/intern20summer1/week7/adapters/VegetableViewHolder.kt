package com.asiantech.intern20summer1.week7.adapters

import android.annotation.SuppressLint
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
    internal var onItemClicked: (id: Int?) -> Unit = {}

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

        init {
            itemView.setOnClickListener {
                onItemClicked.invoke(plantList[adapterPosition]?.id)
            }
        }

        @SuppressLint("SetTextI18n")
        fun onBindData(position: Int) {
            val vegetableItem = plantList[position]
            val tvVegetableName = itemView.tvVegetableName
            val imgVegetable = itemView.imgVegetable
            val imgWorm = itemView.imgWormVegetable
            val imgLackWater = itemView.imgLackWater
            val imgHarvest = itemView.imgHarvestVegetable
            val tvDateGrow = itemView.tvDateGrow
            val tvDateHarvest = itemView.tvDateHarvest
            vegetableItem?.let { cultivation ->
                appDatabase?.getPlantDao()?.getPlant(cultivation.plantId)?.let {
                    tvVegetableName.text = it.name
                    tvDateGrow.text =
                        itemView.context.getString(R.string.vegetable_view_holder_date_grow_description) + cultivation.dateCultivation
                    tvDateHarvest.text =
                        itemView.context.getString(R.string.vegetable_view_holder_date_harvest_description) + PlantStatus().getDateHarvest(cultivation.dateCultivation, it)
                    imgVegetable.setImageURI(Uri.parse(it.imageUrl))
                    imgHarvest.visibility = View.INVISIBLE
                    imgWorm.visibility = View.INVISIBLE
                    imgLackWater.visibility = View.INVISIBLE
                    if (PlantStatus().isWormed(
                            it,
                            cultivation
                        ) && !PlantStatus().isComingHarvest(it, cultivation)
                    ) {
                        imgWorm.visibility = View.VISIBLE
                    }
                    if (PlantStatus().isLackedWater(
                            it,
                            cultivation
                        ) && !PlantStatus().isComingHarvest(it, cultivation)
                    ) {
                        imgLackWater.visibility = View.VISIBLE
                    }
                    if (PlantStatus().isComingHarvest(it, cultivation)) {
                        imgHarvest.visibility = View.VISIBLE
                        imgWorm.visibility = View.INVISIBLE
                        imgLackWater.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }
}
