package com.asiantech.intern20summer1.w7.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.asiantech.intern20summer1.w7.model.CultivationModel

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/11/20
 * This is DAO for cultivation model
 */

@Dao
interface CultivationDAO {

    @Insert
    fun addCultivation(cultivation: CultivationModel)

    @Query("select * from cultivation")
    fun getAllCultivation(): List<CultivationModel>

    @Query("select * from cultivation where id =:id")
    fun getCultivation(id: Int?): CultivationModel

    @Delete
    fun deleteCultivation(cultivation: CultivationModel)

    @Query("update cultivation set dateWatering = :time where id = :id")
    fun waterPlant(id: Int?, time: String?)
}
