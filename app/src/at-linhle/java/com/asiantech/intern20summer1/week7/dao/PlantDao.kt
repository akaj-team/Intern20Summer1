package com.asiantech.intern20summer1.week7.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asiantech.intern20summer1.week7.models.Plant

@Dao
interface PlantDao {
    @Insert
    fun insertPlants(plant: List<Plant>)

    @Query("SELECT * FROM plant")
    fun getPlants(): List<Plant>

    @Query("select * from plant where id = :id")
    fun getPlant(id: String?): Plant
}
