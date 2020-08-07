package com.asiantech.intern20summer1.w7.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class PlantModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "plantId") val plantId: Int = 0,
    @ColumnInfo(name = "userName")val plantName: String = "",
    @ColumnInfo(name = "university")val plantDescription: String = "",
    @ColumnInfo(name = "homeTown")val homeTown: String = "",
    @ColumnInfo(name = "imgUri")val imgUri: String? = null
) : Serializable