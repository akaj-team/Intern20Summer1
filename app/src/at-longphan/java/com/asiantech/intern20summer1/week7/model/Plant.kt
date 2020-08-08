package com.asiantech.intern20summer1.week7.model

class Plant {

    companion object {
        //private const val RANDOM_LIKES_RANGE = 4
    }

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

    /*fun createTreesList(numItems: Int): MutableList<Plant> {
        val timeLineItems = mutableListOf<Plant>()
        for (i in 1..numItems) {
            val random = (0..RANDOM_LIKES_RANGE).random()
            timeLineItems.add(
                Plant(
                    "Name $i",
                    "",
                    "This is content, this is content this is content this is content",
                    random != 0 && i % 2 == 0,
                    random,
                    random > 1
                )
            )
        }
        return timeLineItems
    }*/
}
