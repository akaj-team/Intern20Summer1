package com.asiantech.intern20summer1.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    fun getUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE userId=:userId")
    fun getUserById(userId: Int): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Query("DELETE FROM user_table WHERE userId= :userId")
    suspend fun deleteUserById(userId: Long)
}