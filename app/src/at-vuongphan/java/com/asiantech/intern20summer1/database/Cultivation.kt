package com.asiantech.intern20summer1.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cultivation")
data class Cultivation(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "userGrowId") var userGrowId: Int? = null,
    @ColumnInfo(name = "plantId") var plantId: String? = null,
    @ColumnInfo(name = "dateCultivation") var dateCultivation: String? = null,
    @ColumnInfo(name = "dateWatering") var dateWatering: String? = null
)