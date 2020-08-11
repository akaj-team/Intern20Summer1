package com.asiantech.intern20summer1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlants(plant: List<Plant>)

    @Query("select * from plants")
    fun getAllPlant(): List<Plant>

    @Query("select * from plants where plantId = :id")
    fun getPlant(id: String?): Plant

}
