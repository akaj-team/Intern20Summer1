package com.asiantech.intern20summer1.w5.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R

class TimeLineAdapter(private val cars: MutableList<TimeLineItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal var onItemClicked: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.time_line_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? RecyclerViewHolder)?.bindData()
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imgCar: ImageView = itemView.findViewById(R.id.imgCar)
        private val imgLike: ImageView = itemView.findViewById(R.id.imgLike)
        private val tvLikeNumber: TextView = itemView.findViewById(R.id.tvLikeNumber)
        private val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)

        init {
            imgLike.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }

        fun bindData() {
            val item = cars[adapterPosition]
            tvUsername.text = item.userName
            imgCar.setImageResource(item.image)
            tvLikeNumber.text = String.format(
                itemView.context.getString(
                    R.string.w5_item_number_of_like,
                    item.likeNumber
                )
            )
            tvDescription.text = item.description
            if (item.isLiked)
                imgLike.setImageResource(R.mipmap.ic_liked)
            else
                imgLike.setImageResource(R.mipmap.ic_non_liked)
        }
    }
}
