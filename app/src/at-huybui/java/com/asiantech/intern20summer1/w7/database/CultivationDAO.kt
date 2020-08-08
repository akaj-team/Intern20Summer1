package com.asiantech.intern20summer1.w7.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.asiantech.intern20summer1.w7.model.CultivationModel

@Dao
interface CultivationDAO {

    @Insert
    fun addCultivation(cultivation: CultivationModel)

    @Query("select * from cultivation")
    fun getAllCultivation(): List<CultivationModel>

//    @Delete
//    fun deleteCultivation(cultivation: CultivationModel)
}
