package com.asiantech.intern20summer1.week7.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R

class PlantViewHolder : RecyclerView.ViewHolder {

    internal var containerView: View? = null
    internal var nameTextView: TextView? = null
    internal var displayImageView: ImageView? = null
    internal var statusImageView: ImageView? = null
    internal var dateCultivationTextView: TextView? = null
    internal var dateHarvestTextView: TextView? = null

    constructor(itemView: View) : super(itemView) {

        containerView = itemView.findViewById(R.id.containerItemListPlant) as? View
        nameTextView = itemView.findViewById(R.id.tvNamePlant) as? TextView
        displayImageView = itemView.findViewById(R.id.imgDisplayPlant) as? ImageView
        statusImageView = itemView.findViewById(R.id.imgStatusPlant) as? ImageView
        dateCultivationTextView = itemView.findViewById(R.id.tvDateCultivation) as? TextView
        dateHarvestTextView = itemView.findViewById(R.id.tvDateHarvest) as? TextView
    }
}
