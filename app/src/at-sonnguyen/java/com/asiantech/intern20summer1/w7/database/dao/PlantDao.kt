package com.asiantech.intern20summer1.w7.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asiantech.intern20summer1.w7.database.data.Plant

@Dao
interface PlantDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlants(plants : List<Plant>)

    @Query("SELECT * FROM plants")
    fun getAllPlant() : List<Plant>

    @Query("SELECT * FROM plants WHERE plantId = :id")
    fun getPlant(id:String?) : Plant

    @Query("UPDATE plants SET imageUri = :imageUri WHERE plants.plantId = :plantId")
    fun updateImageUri(imageUri : String, plantId : String)
}