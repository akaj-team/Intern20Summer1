package com.asiantech.intern20summer1.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CultivationDao {
    @Query("SELECT * FROM  cultivation_table")
    fun getCultivation(): LiveData<List<Cultivation>>

    @Query("SELECT * FROM cultivation_table WHERE userGrowId=:userGrowId AND id =:id ORDER BY dateCultivation DESC")
    fun getCultivations(userGrowId: Int, id: Int): LiveData<List<Cultivation>>

    @Query("SELECT * FROM cultivation_table WHERE id =:id")
    fun getCultivation(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cultivation: Cultivation)

    @Update
    fun updateCultivation(cultivation: Cultivation)

    @Query("DELETE FROM cultivation_table")
    suspend fun deleteAll()

    @Query("DELETE FROM cultivation_table WHERE id =:id")
    fun deleteCultivationId(id: Int)
}
