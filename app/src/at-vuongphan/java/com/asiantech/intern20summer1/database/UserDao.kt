package com.asiantech.intern20summer1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun insertUser(vararg account: User?)

    @Query("select * from user ")
    fun getUser(): User
}
