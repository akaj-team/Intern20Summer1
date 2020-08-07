package com.asiantech.intern20summer1.week7

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PlantDao {
    @Query("SELECT * FROM plant")
    fun getPlants(): List<Plant>
}
