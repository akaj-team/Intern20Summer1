package com.asiantech.intern20summer1.w7.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.asiantech.intern20summer1.w7.database.data.User

@Dao
interface UserDao{
    @Insert
    fun insertUser(vararg user : User)

    @Query("SELECT * FROM user")
    fun getAllUser() : User
}
