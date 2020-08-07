package com.asiantech.intern20summer1.database

import android.content.Context
import android.util.JsonReader
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.reflect.TypeToken
import java.util.concurrent.Executors

@Database(
    entities = arrayOf(User::class, Plant::class, Cultivation::class),
    version = 1
)
abstract class VegetableDB : RoomDatabase() {
    abstract fun plantDao(): PlantDao
    fun instance(context: Context): VegetableDB {
        return Room.databaseBuilder(context, VegetableDB::class.java, "plant.db")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newFixedThreadPool(2).execute {
                        context.assets.open("plants.json").use { inputStream ->
                            JsonReader(inputStream.reader()).use { _ ->
                                val planType = object : TypeToken<List<Plant>>() {}.type
                            }
                        }
                    }
                }
            }).build()
    }
}

