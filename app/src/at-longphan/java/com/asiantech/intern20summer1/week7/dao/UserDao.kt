package com.asiantech.intern20summer1.week7.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.asiantech.intern20summer1.week7.entity.User

@Dao
interface UserDao {
    @Query("SELECT COUNT(userId) FROM users WHERE userId = :userId LIMIT 1")
    fun isExist(userId: Int): Int

    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM users ORDER BY userId DESC LIMIT 1")
    fun getLastInsert(): User
}