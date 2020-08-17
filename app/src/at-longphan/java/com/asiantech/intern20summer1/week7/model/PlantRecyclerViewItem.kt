package com.asiantech.intern20summer1.week7.model

class PlantRecyclerViewItem {

    internal var cultivationId: Int? = null
    internal var name: String? = null
    internal var dateCultivation: String? = null
    internal var dateHarvest: String? = null
    internal var imageUrl: String? = null
    internal var status: Boolean = false

    constructor(
        cultivationId: Int,
        name: String,
        dateCultivation: String,
        dateHarvest: String,
        imageUrl: String,
        status: Boolean
    ) {
        this.cultivationId = cultivationId
        this.name = name
        this.dateCultivation = dateCultivation
        this.dateHarvest = dateHarvest
        this.imageUrl = imageUrl
        this.status = status
    }
}
