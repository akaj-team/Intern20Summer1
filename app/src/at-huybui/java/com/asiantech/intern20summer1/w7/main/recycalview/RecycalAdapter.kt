package com.asiantech.intern20summer1.w7.main.recycalview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val mutableList: MutableList<RecycalItem>) :
    RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapter.ItemViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = mutableList.size

    override fun onBindViewHolder(holder: RecyclerAdapter.ItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var name1: TextView = itemView.tvName1W5

        init {
            iconHeart.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }
    }

    fun bindData() {
        mutableList[adapterPosition].let { item ->
            val stName1 = "${adapterPosition + 1}. ${item.name}"
            val stAmountHeart = "$BLACK_HEART_SYMBOL ${item.amountHeart} likes"
            name1.text = stName1
            name2.text = item.name
            information.text = item.information
            amountHeart.text = stAmountHeart
            if (item.isStatusHeart) {
                iconHeart.setImageResource(R.drawable.ic_heart_red)
            } else {
                iconHeart.setImageResource(R.drawable.ic_heart_transparent)
            }
            image.setImageResource(item.image)
        }
    }
}

}