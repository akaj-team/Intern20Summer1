package com.asiantech.intern20summer1.w7.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.asiantech.intern20summer1.w7.model.UserModel

@Dao
interface PlantDAO {

    @Insert
    fun insertUser(vararg account: UserModel)

    @Query("select * from user ")
    fun getUser(): UserModel
}
