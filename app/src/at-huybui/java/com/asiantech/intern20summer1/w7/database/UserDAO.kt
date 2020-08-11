package com.asiantech.intern20summer1.w7.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.asiantech.intern20summer1.w7.model.UserModel

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/11/20
 * This is DAO for user model
 */

@Dao
interface UserDAO {

    @Insert
    fun insertUser(vararg account: UserModel)

    @Query("select * from user ")
    fun getUser(): UserModel
}
