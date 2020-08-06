package com.asiantech.intern20summer1.week7.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant")
data class Plant(
    @PrimaryKey val id: String? = "",
    var name: String? = "",
    var description: String? = "",
    var growZoneNumber: Int? = 0,
    var wateringInterval: Int? = 0,
    var imageUrl: String? = ""
)
