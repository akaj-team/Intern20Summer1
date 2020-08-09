package com.asiantech.intern20summer1.w7.main.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.database.ConnectDataBase
import com.asiantech.intern20summer1.w7.model.CultivationModel
import kotlinx.android.synthetic.`at-huybui`.recycler_farm_item.view.*
import java.time.LocalDate

class RecyclerAdapter(private val mutableList: MutableList<CultivationModel>) :
    RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>() {

    companion object {
        private const val FORMAT_CODE_DATE = "dd/MM/yyyy HH:mm"
    }

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
        private var name: TextView = itemView.tvNameVegetable
        private var dateCultivation: TextView = itemView.tvDateCultivation
        private var dateHarvest: TextView = itemView.tvDateHarvest
        private var imgPlant: ImageView = itemView.imgVegetable


        init {
            itemView.setOnClickListener {
                onItemClicked.invoke(mutableList[adapterPosition].id)
            }
        }

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bindData() {
            mutableList[adapterPosition].let { item ->
                val plant = dataBase?.plantDao()?.getPlant(item.plantId)
                name.text = plant?.name
                val dateCul = item.dateCultivation
                dateCultivation.text = "Trồng lúc: $dateCul"
                imgPlant.setImageURI(Uri.parse(plant?.imageUri))
            }
        }
    }
}
