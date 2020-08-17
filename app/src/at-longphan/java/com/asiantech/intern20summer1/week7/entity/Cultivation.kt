package com.asiantech.intern20summer1.week7.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cultivations")
data class Cultivation(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val userGrowId: Int,
    val plantId: String,
    val dateCultivation: String,
    val dateWatering: String
)
