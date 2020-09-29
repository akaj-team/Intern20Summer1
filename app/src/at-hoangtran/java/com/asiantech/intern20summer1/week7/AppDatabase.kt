package com.asiantech.intern20summer1.week7

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
    entities = [User::class, Plant::class, Cultivation::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDAO(): UserDAO
    abstract fun getPlantDAO(): PlantDAO
    abstract fun getCultivationDAO(): CultivationDAO

    companion object {
        private var instance: AppDatabase? = null
        private const val DATABASE_NAME = "plant.db"
        private const val JSON_NAME = "plants.json"

        fun getInstance(context: Context): AppDatabase? {
            return if (instance == null) {
                buildDatabase(context)
            } else {
                instance
            }
        }

        private fun buildDatabase(context: Context): AppDatabase? {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Executors.newFixedThreadPool(2).execute {
                            context.assets.open(JSON_NAME).use { inputStream ->
                                JsonReader(inputStream.reader()).use { jsonReader ->
                                    val plantType = object : TypeToken<List<Plant>>() {}.type
                                    val plants: List<Plant> = Gson().fromJson(jsonReader, plantType)
                                    buildDatabase(context)?.getPlantDAO()?.insertPlant(plants)
                                }
                            }
                        }
                    }
                }).allowMainThreadQueries().build()
        }
    }
}
