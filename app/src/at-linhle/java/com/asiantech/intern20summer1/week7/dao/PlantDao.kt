package com.asiantech.intern20summer1.week7.dao

import androidx.room.Dao
import androidx.room.Query
import com.asiantech.intern20summer1.week7.models.Plant

@Dao
interface PlantDao {
    @Query("SELECT * FROM plant")
    fun getPlants(): List<Plant>
}