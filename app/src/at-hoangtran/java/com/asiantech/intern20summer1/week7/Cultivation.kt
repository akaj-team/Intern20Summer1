package com.asiantech.intern20summer1.week7

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cultivation")
data class Cultivation(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    var userGrowId: Int? = 0,
    var plantId: String? = "",
    var dateCultivation: String? = "",
    var dateWatering: String? = ""
)
