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

    @Query("SELECT * FROM cultivations " +
            "INNER JOIN plants USING (plantId) " +
            "WHERE userGrowId = :userId ORDER BY id DESC")
    fun findCultivationByUserId(userId: Int): List<PlantAndCultivation>
}
