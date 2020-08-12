package com.asiantech.intern20summer1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [User::class, Plant::class, Cultivation::class],
    version = 1, exportSchema = false
)
abstract class VegetableDB : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun plantDao(): PlantDao
    abstract fun cultivationDao(): CultivationDaO

    companion object {
        private const val NAME_DATA_BASE = "plant.db"
        private var INSTANCE: VegetableDB? = null

        fun dataBaseConnect(context: Context): VegetableDB? {
            return if (INSTANCE == null) {
                Room.databaseBuilder(context, VegetableDB::class.java, NAME_DATA_BASE)
                    .allowMainThreadQueries()
                    .build()
            } else {
                INSTANCE
            }
        }
    }
}
