package com.asiantech.intern20summer1.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant_table")
data class Plant(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "plantId")
    var plantId: String = "",
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "description")
    var description: String = "",
    @ColumnInfo(name = "growZoneNumber")
    var growZoneNumber: Int = 0,
    @ColumnInfo(name = "wateringInterval")
    var wateringInterval: Int = 0,
    @ColumnInfo(name = "imageUri")
    var imageUri: String = ""
)