package com.asiantech.intern20summer1.week7.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.model.PlantRecyclerViewItem
import com.asiantech.intern20summer1.week7.viewholder.PlantViewHolder
import com.bumptech.glide.Glide

class PlantAdapter : RecyclerView.Adapter<PlantViewHolder> {

    private var plants: List<PlantRecyclerViewItem>
    private var context: Context

    internal var onItemViewClick: (cultivationId: Int) -> Unit = {}

    constructor(context: Context, plants: MutableList<PlantRecyclerViewItem>) {
        this.plants = plants
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val plantView = inflater.inflate(R.layout.item_list_plant, parent, false)
        return PlantViewHolder(plantView)
    }

    override fun onBindViewHolder(viewHolder: PlantViewHolder, position: Int) {
        val plant = plants[position]

        // Set itemView based on views and data model
        val containerView = viewHolder.containerView
        containerView?.setBackgroundResource(
            when {
                position % 6 == 0 -> R.drawable.bg_item_plant_6_w7
                position % 5 == 0 -> R.drawable.bg_item_plant_5_w7
                position % 4 == 0 -> R.drawable.bg_item_plant_4_w7
                position % 3 == 0 -> R.drawable.bg_item_plant_3_w7
                position % 2 == 0 -> R.drawable.bg_item_plant_2_w7
                else -> R.drawable.bg_item_plant_1_w7
            }
        )
        containerView?.setOnClickListener {
            plant.cultivationId?.let { cultivationId -> onItemViewClick.invoke(cultivationId) }
        }

        val nameTextView = viewHolder.nameTextView
        nameTextView?.text = plant.name

        val displayImageView = viewHolder.displayImageView
        displayImageView?.let {
            Glide.with(it)
                .load(plant.imageUrl)
                .into(it)
        }

        val statusImageView = viewHolder.statusImageView
        if (plant.status) {
            statusImageView?.setImageResource(R.drawable.btn_facebook)
        } else {
            statusImageView?.setImageResource(R.drawable.btn_google_plus)
        }

        val dateCultivationTextView = viewHolder.dateCultivationTextView
        dateCultivationTextView?.text =
            context.getString(R.string.text_view_plant_at_description).plus(plant.dateCultivation)

        val dateHarvestTextView = viewHolder.dateHarvestTextView
        dateHarvestTextView?.text =
            context.getString(R.string.text_view_harvest_time_description).plus(plant.dateHarvest)
    }

    override fun getItemCount(): Int {
        return plants.size
    }
}
