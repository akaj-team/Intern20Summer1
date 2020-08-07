package com.asiantech.intern20summer1.w7.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asiantech.intern20summer1.w7.model.PlantModel

@Dao
interface PlantDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlants(plant: List<PlantModel>)

    @Query("select * from plants")
    fun getAllPlant(): List<PlantModel>
}
