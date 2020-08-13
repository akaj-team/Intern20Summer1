package com.asiantech.intern20summer1.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.database.Cultivation
import com.asiantech.intern20summer1.database.VegetableDB
import com.asiantech.intern20summer1.fragment.VegetableFragmentRecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.`at-vuongphan`.w7_item_recycler_view.view.*

class VegetableRecyclerViewAdapter(private val mutableList: MutableList<Cultivation>) :
    RecyclerView.Adapter<VegetableRecyclerViewAdapter.ItemViewHolder>() {
    internal var onItemClicked: (id: Int?) -> Unit = {}
    private var dataBase: VegetableDB? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VegetableRecyclerViewAdapter.ItemViewHolder {
        dataBase = VegetableDB.dataBaseConnect(parent.context)
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.w7_item_recycler_view, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount() = mutableList.size

    override fun onBindViewHolder(
        holder: VegetableRecyclerViewAdapter.ItemViewHolder,
        position: Int
    ) {
        holder.bindData()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvName: TextView = itemView.tvNameVegetable
        private var icon: CircleImageView = itemView.circleData
        private var tvDateHarvest: TextView = itemView.tvTimeTwo
        private var tvDateCultivation: TextView = itemView.tvTimeOne
        private var avatar: CircleImageView = itemView.imgVegetable

        init {
            itemView.setOnClickListener {
                onItemClicked.invoke(mutableList[adapterPosition].id)
            }
        }

        fun bindData() {
//            tvName.text = mutableList[adapterPosition].name
//            tvDateCultivation.text = mutableList[adapterPosition].description
//            avatar.setImageURI(Uri.parse(mutableList[adapterPosition].imageUri))
//            icon.setImageURI(Uri.parse(mutableList[adapterPosition].imageUri))
            mutableList[adapterPosition].let {
                dataBase?.plantDao()?.getPlant(it.plantId)?.apply {
                    val cultivation = it.dateCultivation
                    val harvest =
                        VegetableFragmentRecyclerView().getDateHarvest(it.dateCultivation, this)
                    tvDateCultivation.text =
                        itemView.context.getString(R.string.text_cultivation, cultivation)
                    tvDateHarvest.text = itemView.context.getString(R.string.text_harvest, harvest)
                    tvName.text = name
                    avatar.setImageURI(Uri.parse(imageUri))
                    icon.setImageURI(Uri.parse(imageUri))
                }
            }
        }
    }
}
