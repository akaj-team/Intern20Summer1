package com.asiantech.intern20summer1.week7

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDAO {
    @Insert
    fun insertUser(user: User)

    @Query("SELECT*FROM user")
    fun getUser(): User
}
