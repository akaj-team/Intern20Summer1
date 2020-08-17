package com.asiantech.intern20summer1.week7

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlantDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlant(plant: List<Plant>)

    @Query("SELECT * FROM plant")
    fun getPlants(): List<Plant>

    @Query("SELECT*FROM plant WHERE id = :id")
    fun getPlant(id: String?): Plant

    @Query("UPDATE plant set imageUrl = :uri where id = :id")
    fun updateImageUrl(uri: String, id: String)
}
