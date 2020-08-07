package com.asiantech.intern20summer1.w7.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class PlantModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "plantId") val plantId: Int = 0,
    @ColumnInfo(name = "userName") val plantName: String = "",
    @ColumnInfo(name = "plantDescription") val plantDescription: String = "",
    @ColumnInfo(name = "growZoneNumber") val growZoneNumber: Int = 0,
    @ColumnInfo(name = "wateringInterval") val wateringInterval: Int = 0,
    @ColumnInfo(name = "imgUrl") val imgUrl: String? = null
) : Serializable
