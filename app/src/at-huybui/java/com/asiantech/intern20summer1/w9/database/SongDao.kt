package com.asiantech.intern20summer1.w9.database

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.asiantech.intern20summer1.w9.models.Song

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is dao class for song item
 */

@Dao
interface SongDao {

    @Query("SELECT COUNT(*) FROM " + Song.TABLE_NAME)
    fun count(): Int

    @Query("SELECT * FROM " + Song.TABLE_NAME)
    fun selectAll(): Cursor

    @Insert
    fun insert(song: Song): Long

    @Query("DELETE FROM " + Song.TABLE_NAME + " WHERE " + Song.COLUMN_ID + " = :id")
    fun deleteById(id: Long): Int

    @Query("SELECT * FROM " + Song.TABLE_NAME + " WHERE " + Song.COLUMN_ID + " = :id")
    fun selectById(id: Long): Cursor

    @Update
    fun update(song: Song): Int


}
