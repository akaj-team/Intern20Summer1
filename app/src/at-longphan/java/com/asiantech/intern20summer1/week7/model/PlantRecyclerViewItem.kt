package com.asiantech.intern20summer1.week7.model

class PlantRecyclerViewItem {

    internal var plantId: Int? = null
    internal var name: String? = null
    internal var dateCultivation: String? = null
    internal var dateHarvest: String? = null
    internal var imageUrl: String? = null
    internal var status: Boolean? = null

    constructor(
        plantId: Int,
        name: String,
        dateCultivation: String,
        dateHarvest: String,
        imageUrl: String,
        status: Boolean
    ) {
        this.plantId = plantId
        this.name = name
        this.dateCultivation = dateCultivation
        this.dateHarvest = dateHarvest
        this.imageUrl = imageUrl
        this.status = status
    }
}
