package com.asiantech.intern20summer1.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlantDao {
    @Query("SELECT * FROM  plant_table")
    fun getPlant(): LiveData<List<Plant>>

    @Query("SELECT * FROM plant_table WHERE plantId =:plantId")
    fun getPlantById(plantId: String): LiveData<Plant>

    @Update
    fun update(plant: Plant)

    @Query("DELETE FROM plant_table")
    suspend fun deleteAll()

    @Insert
    fun insertPlant(plant: List<Plant>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plant: List<Plant>)

    @Query("DELETE FROM plant_table WHERE plantId= :id")
    suspend fun deletePlanById(id: String)
}