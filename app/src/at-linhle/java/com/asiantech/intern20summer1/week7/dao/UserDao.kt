package com.asiantech.intern20summer1.week7.dao

import androidx.room.Dao
import androidx.room.Query
import com.asiantech.intern20summer1.week7.models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUsers(): List<User>
}