package com.asiantech.intern20summer1.week7

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.asiantech.intern20summer1.week7.dao.CultivationDao
import com.asiantech.intern20summer1.week7.dao.PlantDao
import com.asiantech.intern20summer1.week7.dao.UserDao
import com.asiantech.intern20summer1.week7.entity.Cultivation
import com.asiantech.intern20summer1.week7.entity.Plant
import com.asiantech.intern20summer1.week7.entity.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import java.util.concurrent.Executors

@Database(
    entities = [User::class, Plant::class, Cultivation::class],
    version = 1
)
abstract class PlantRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun plantDao(): PlantDao
    abstract fun cultivationDao(): CultivationDao

    companion object {
        private const val NAME_DATA_BASE = "plant.db"
        private var plantRoomInstance: PlantRoomDatabase? = null

        fun provideDatabase(context: Context): PlantRoomDatabase {
            return Room.databaseBuilder(context, PlantRoomDatabase::class.java, NAME_DATA_BASE)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Executors.newFixedThreadPool(2).execute {
                            context.assets.open("plants.json").use { inputStream ->
                                JsonReader(inputStream.reader()).use { jsonReader ->
                                    val plantType = object : TypeToken<List<Plant>>() {}.type
                                    val plants: List<Plant> = Gson().fromJson(jsonReader, plantType)
                                    provideDatabase(context).plantDao().insertList(plants)
                                }
                            }
                        }
                    }
                }).build()
        }

        fun getDatabase(context: Context): PlantRoomDatabase?{
            return if (plantRoomInstance == null) {
                Room.databaseBuilder(context, PlantRoomDatabase::class.java, NAME_DATA_BASE)
                    .allowMainThreadQueries()
                    .build()
            } else {
                plantRoomInstance
            }
        }
    }
}