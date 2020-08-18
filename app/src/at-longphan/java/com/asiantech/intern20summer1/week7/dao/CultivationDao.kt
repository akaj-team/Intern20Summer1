package com.asiantech.intern20summer1.week7.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.asiantech.intern20summer1.week7.dto.PlantAndCultivation
import com.asiantech.intern20summer1.week7.entity.Cultivation

@Dao
interface CultivationDao {

    @Insert
    fun insert(cultivation: Cultivation)

    @Query(
        "SELECT * FROM cultivations " +
                "INNER JOIN plants USING (plantId) " +
                "WHERE userGrowId = :userId ORDER BY id DESC"
    )
    fun getAllCultivationByUserId(userId: Int): List<PlantAndCultivation>

    @Query(
        "SELECT * FROM cultivations " +
                "INNER JOIN plants USING (plantId) " +
                "WHERE id = :id LIMIT 1"
    )
    fun getById(id: Int): PlantAndCultivation

    @Query(
        "UPDATE cultivations " +
                "SET dateWatering = :dateWatering " +
                "WHERE id = :id"
    )
    fun updateDateWateringById(id: Int, dateWatering: String?)

    @Query(
        "DELETE FROM cultivations " +
                "WHERE id = :id"
    )
    fun deleteById(id: Int)
}
