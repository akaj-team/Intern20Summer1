package com.asiantech.intern20summer1.w7.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asiantech.intern20summer1.w7.model.PlantModel

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/11/20
 * This is DAO for plant model
 */

@Dao
interface PlantDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlants(plant: List<PlantModel>)

    @Query("select * from plants")
    fun getAllPlant(): List<PlantModel>

    @Query("select * from plants where plantId = :id")
    fun getPlant(id: String?): PlantModel

    @Query("update plants set imageUri = :imageUri where plants.plantId = :plantId")
    fun editUri(imageUri: String, plantId: String?)
}
