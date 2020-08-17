package com.asiantech.intern20summer1.week7.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asiantech.intern20summer1.week7.entity.Plant

@Dao
interface PlantDao {

    @Query("SELECT * FROM plants ORDER BY plantId ASC")
    fun getAll(): List<Plant>

    @Query("SELECT plantId FROM plants ORDER BY plantId ASC")
    fun getAllId(): MutableList<String>

    @Insert
    fun insert(plant: Plant)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(plants: List<Plant>)
}
