package com.asiantech.intern20summer1.w7.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/04/20
 * This is data class for cultivation model
 */

@Entity(tableName = "cultivation")
data class CultivationModel(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "userGrowId") var userGrowId: Int? = null,
    @ColumnInfo(name = "plantId") var plantId: String? = null,
    @ColumnInfo(name = "dateCultivation") var dateCultivation: String? = null,
    @ColumnInfo(name = "dateWatering") var dateWatering: String? = null
)
