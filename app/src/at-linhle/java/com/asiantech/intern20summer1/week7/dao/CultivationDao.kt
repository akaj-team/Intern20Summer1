package com.asiantech.intern20summer1.week7.dao

import androidx.room.*
import com.asiantech.intern20summer1.week7.models.Cultivation

@Dao
interface CultivationDao {
    @Insert
    fun insertCultivation(cultivation: Cultivation)

    @Query("SELECT * FROM cultivation")
    fun getAllCultivation(): List<Cultivation>

    @Query("SELECT * FROM cultivation WHERE id =:id")
    fun getCultivation(id: Int?): Cultivation

    @Delete
    fun deleteCultivation(cultivation: Cultivation)
}
