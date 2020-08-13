package com.asiantech.intern20summer1.w7.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.asiantech.intern20summer1.w7.database.data.Cultivation

@Dao
interface CultivationDao {
    @Insert
    fun insertCultivation(cultivation: Cultivation)

    @Query("SELECT * FROM cultivation")
    fun getAllCultivation(): List<Cultivation>
}

