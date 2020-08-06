package com.asiantech.intern20summer1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.data.VegetableItemRecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.`at-vuongphan`.w7_item_recycler_view.view.*

class VegetableRecyclerViewAdapter(private val mutableList: MutableList<VegetableItemRecyclerView>) :
    RecyclerView.Adapter<VegetableRecyclerViewAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VegetableRecyclerViewAdapter.ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.w7_item_recycler_view, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount() = mutableList.size

    override fun onBindViewHolder(holder: VegetableRecyclerViewAdapter.ItemViewHolder, position: Int) {
        holder.bindData()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var nameVegetable: TextView = itemView.tvNameVegetable
        private var imageData: CircleImageView = itemView.circleData
        private var timerOne: TextView = itemView.tvTimeOne
        private var timerTwo: TextView = itemView.tvTimeTwo
        private var avatar: CircleImageView = itemView.imgVegetable

        fun bindData() {
            mutableList[adapterPosition].let {
                nameVegetable.text = it.nameVegetable
                timerOne.text = it.timerOne
                timerTwo.text = it.timerTwo
                imageData.setImageResource(it.imageData)
                avatar.setImageResource(it.avatar)
            }
        }
    }
}
