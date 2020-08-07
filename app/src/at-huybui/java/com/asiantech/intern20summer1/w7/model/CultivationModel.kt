package com.asiantech.intern20summer1.w7.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class CultivationModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cultivationId") val cultivationId: Int = 0,
    @ColumnInfo(name = "userGrowId") val userGrowId: Int = 0,
    @ColumnInfo(name = "plantId") val plantId: String = "",
    @ColumnInfo(name = "dateCultivation") val dateCultivation: String = "",
    @ColumnInfo(name = "dateWatering") val dateWatering: String = ""
) : Serializable
