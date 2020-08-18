package com.asiantech.intern20summer1.week7.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.asiantech.intern20summer1.week7.dao.CultivationDao
import com.asiantech.intern20summer1.week7.dao.PlantDao
import com.asiantech.intern20summer1.week7.dao.UserDao
import com.asiantech.intern20summer1.week7.models.Cultivation
import com.asiantech.intern20summer1.week7.models.Plant
import com.asiantech.intern20summer1.week7.models.User
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
    abstract fun getUserDao(): UserDao
    abstract fun getPlantDao(): PlantDao
    abstract fun getCultivationDao(): CultivationDao

    companion object {

        private const val NAME_PLANT_DATABASE = "plant.db"
        private const val FILE_NAME_JSON = "plants.json"
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            return if (instance == null) {
                buildDatabase(context)
            } else {
                instance
            }
        }


        private fun buildDatabase(context: Context): AppDatabase? {
            return Room.databaseBuilder(context, AppDatabase::class.java, NAME_PLANT_DATABASE)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Executors.newFixedThreadPool(2).execute {
                            context.assets.open(FILE_NAME_JSON).use { inputStream ->
                                JsonReader(inputStream.reader()).use { jsonReader ->
                                    val plantType = object : TypeToken<List<Plant>>() {}.type
                                    val plants: List<Plant> = Gson().fromJson(jsonReader, plantType)

                                    buildDatabase(context)?.getPlantDao()?.insertPlants(plants)
                                }
                            }
                        }
                    }
                }).allowMainThreadQueries().build()
        }
    }
}
