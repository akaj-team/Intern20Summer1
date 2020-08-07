package com.asiantech.intern20summer1.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cultivation_table",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class Cultivation(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "userGrowId")
    var userGrowId: Int = 0,
    @ColumnInfo(name = "planId")
    var plantId: String = "",
    @ColumnInfo(name = "dateCultivation")
    var dateCultivation: String = "",
    @ColumnInfo(name = "dateWatering")
    var dateWatering: String = ""
)