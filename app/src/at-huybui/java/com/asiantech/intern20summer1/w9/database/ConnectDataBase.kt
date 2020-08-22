package com.asiantech.intern20summer1.w9.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.asiantech.intern20summer1.w9.models.Song

/**
 * Created by Amanjeet Singh on 25/11/17.
 */
@Database(entities = [Song::class], version = 1, exportSchema = false)
abstract class ConnectDataBase : RoomDatabase() {

    abstract fun songDao(): SongDao

    companion object {

        private const val NAME_DATA_BASE = "music.db"
        private var INSTANCE: ConnectDataBase? = null

        fun dataBaseConnect(context: Context): ConnectDataBase? {
            return if (INSTANCE == null) {
                Room.databaseBuilder(context, ConnectDataBase::class.java, NAME_DATA_BASE)
                    .allowMainThreadQueries()
                    .build()
            } else {
                INSTANCE
            }
        }
    }
}
