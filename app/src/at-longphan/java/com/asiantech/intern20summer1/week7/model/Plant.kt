package com.asiantech.intern20summer1.week7.model

class Plant {

    internal var category: String? = null
    internal var name: String? = null
    internal var description: String? = null
    internal var growZoneNumber: Int? = null
    internal var wateringInterval: Int? = null
    internal var imageUrl: String? = null

    constructor(
        category: String,
        name: String,
        description: String,
        growZoneNumber: Int,
        wateringInterval: Int,
        imageUrl: String
    ) {
        this.category = category
        this.name = name
        this.description = description
        this.growZoneNumber = growZoneNumber
        this.wateringInterval = wateringInterval
        this.imageUrl = imageUrl
    }
}
