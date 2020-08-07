package com.asiantech.intern20summer1.week7

import androidx.room.Dao
import androidx.room.Query

@Dao
interface UserDAO {
    @Query("SELECT*FROM user")
    fun getUser(): List<User>
}
