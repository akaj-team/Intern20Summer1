package com.asiantech.intern20summer1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import java.util.concurrent.Executors

@Database(
    entities = [User::class, Plant::class],
    version = 1, exportSchema = false
)
abstract class VegetableDB : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun plantDao(): PlantDao

    companion object {
        private const val NAME_DATA_BASE = "plant.db"
        private const val JSON_FILE = "plants.json"
        private const val THREADS = 10
        private var INSTANCE: VegetableDB? = null
        fun dataBaseConnect(context: Context): VegetableDB? {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VegetableDB::class.java,
                    NAME_DATA_BASE
                )
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }

    fun providerDatabase(context: Context): VegetableDB? {
        return Room.databaseBuilder(context, VegetableDB::class.java, NAME_DATA_BASE)
            .allowMainThreadQueries()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newFixedThreadPool(THREADS).execute {
                        context.assets.open(JSON_FILE).use { inputStream ->
                            JsonReader(inputStream.reader()).use { jsonReader ->
                                val plantType = object : TypeToken<List<Plant>>() {}.type
                                val plants: List<Plant> =
                                    Gson().fromJson(jsonReader, plantType)
                                providerDatabase(context)?.plantDao()?.insertPlants(plants)
                            }
                        }
                    }
                }
            }).build()
    }
}


