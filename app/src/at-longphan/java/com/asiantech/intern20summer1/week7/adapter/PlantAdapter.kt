package com.asiantech.intern20summer1.week7.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.model.PlantRecyclerViewItem
import com.asiantech.intern20summer1.week7.viewholder.PlantViewHolder

class PlantAdapter : RecyclerView.Adapter<PlantViewHolder> {

    companion object {
        private const val PICTURE_2 = 2
        private const val PICTURE_3 = 3
    }

    internal var onItemViewClick: (plantId: Int) -> Unit = {}

    private var plants: MutableList<PlantRecyclerViewItem>
    private var context: Context

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
        containerView?.setOnClickListener {
            plant.plantId?.let { it -> onItemViewClick.invoke(it) }
        }

        val nameTextView = viewHolder.nameTextView
        nameTextView?.text = plant.name

        val displayImageView = viewHolder.displayImageView
        when {
            position % PICTURE_3 == 0 -> displayImageView?.setImageResource(R.drawable.img_cat3)
            position % PICTURE_2 == 0 -> displayImageView?.setImageResource(R.drawable.img_cat2)
            else -> displayImageView?.setImageResource(R.drawable.img_cat)
        }

        val statusImageView = viewHolder.statusImageView
        if (plant.status != null) {
            if (plant.status!!) {
                statusImageView?.setImageResource(R.drawable.btn_facebook)
            } else {
                statusImageView?.setImageResource(R.drawable.btn_google_plus)
            }
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
