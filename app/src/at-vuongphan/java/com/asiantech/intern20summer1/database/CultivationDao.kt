package com.asiantech.intern20summer1.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CultivationDAO {

    @Insert
    fun addCultivation(cultivation: Cultivation)

    @Query("select * from cultivation")
    fun getAllCultivation(): List<Cultivation>

    @Query("select * from cultivation where id =:id")
    fun getCultivation(id: Int?): Cultivation

    @Delete
    fun deleteCultivation(cultivation: Cultivation)

    @Query("update cultivation set dateWatering = :time where id = :id")
    fun waterPlant(id: Int?, time: String?)
}
