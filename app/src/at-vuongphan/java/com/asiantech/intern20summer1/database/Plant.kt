package com.asiantech.intern20summer1.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "plantId") val plantId: String? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "growZoneNumber") val growZoneNumber: Int? = null,
    @ColumnInfo(name = "wateringInterval") val wateringInterval: Int? = null,
    @ColumnInfo(name = "imageUrl") val imageUrl: String? = null,
    @ColumnInfo(name = "imageUri") val imageUri: String? = null
)
