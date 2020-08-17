package com.asiantech.intern20summer1.week7

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CultivationDAO {
    @Insert
    fun insertCultivation(cultivation: Cultivation)

    @Query("SELECT*FROM cultivation")
    fun getAllCultivation(): List<Cultivation>

    @Query("SELECT*FROM cultivation WHERE id = :id")
    fun getCultivation(id: Int): Cultivation

    @Delete
    fun deleteCultivation(cultivation: Cultivation)
}
