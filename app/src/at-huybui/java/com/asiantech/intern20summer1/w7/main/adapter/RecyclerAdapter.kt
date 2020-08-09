package com.asiantech.intern20summer1.w7.main.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.database.ConnectDataBase
import com.asiantech.intern20summer1.w7.main.companion.AppCompanion
import com.asiantech.intern20summer1.w7.model.CultivationModel
import com.asiantech.intern20summer1.w7.model.PlantModel
import kotlinx.android.synthetic.`at-huybui`.recycler_farm_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class RecyclerAdapter(private val mutableList: MutableList<CultivationModel>) :
    RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>() {

    private var dataBase: ConnectDataBase? = null
    internal var onItemClicked: (id: Int?) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapter.ItemViewHolder {
        dataBase = ConnectDataBase.dataBaseConnect(parent.context)
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_farm_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount() = mutableList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvName: TextView = itemView.tvNameVegetable
        private var tvDateCultivation: TextView = itemView.tvDateCultivation
        private var tvDateHarvest: TextView = itemView.tvDateHarvest
        private var imgPlant: ImageView = itemView.imgVegetable


        init {
            itemView.setOnClickListener {
                onItemClicked.invoke(mutableList[adapterPosition].id)
            }
        }

        fun bindData() {
            mutableList[adapterPosition].let { culPlant ->
                dataBase?.plantDao()?.getPlant(culPlant.plantId)?.let { plant ->
                    val cultivation = culPlant.dateCultivation
                    val harvest = getDateHarvest(culPlant.dateCultivation, plant)
                    tvDateCultivation.text =
                        itemView.context.getString(R.string.w7_text_cultivation, cultivation)
                    tvDateHarvest.text =
                        itemView.context.getString(R.string.w7_text_harvest, harvest)
                    tvName.text = plant.name
                    imgPlant.setImageURI(Uri.parse(plant.imageUri))                                                                             
                }
            }
        }

        @SuppressLint("SimpleDateFormat")
        private fun getDateHarvest(cultivation: String?, plant: PlantModel): String {
            cultivation?.let { cul ->
                val dateFormat = SimpleDateFormat(AppCompanion.FORMAT_CODE_DATE)
                val calendar = Calendar.getInstance()
                dateFormat.parse(cul)?.let { calendar.time = it }
                plant.growZoneNumber?.let { calendar.add(Calendar.DATE, it) }
                return dateFormat.format(calendar.time)
            }
            return "null"
        }
    }
}
