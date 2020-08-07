package com.asiantech.intern20summer1.w7.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.asiantech.intern20summer1.w7.model.UserModel

@Database(
    entities = [UserModel::class],
    version = 1,
    exportSchema = false
)

abstract class ConnectDataBase : RoomDatabase() {
    companion object {
        private var INSTANCE: ConnectDataBase? = null
        fun getInMemoryDatabase(context: Context): ConnectDataBase? {
            return if (INSTANCE == null)
                Room.databaseBuilder(context, ConnectDataBase::class.java, "plan.db")
                    .allowMainThreadQueries()
                    .build()
            else INSTANCE
        }
    }

    abstract fun accountDao(): UserDAO
}
