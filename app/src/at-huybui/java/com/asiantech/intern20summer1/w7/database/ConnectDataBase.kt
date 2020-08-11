package com.asiantech.intern20summer1.w7.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.asiantech.intern20summer1.w7.model.CultivationModel
import com.asiantech.intern20summer1.w7.model.PlantModel
import com.asiantech.intern20summer1.w7.model.UserModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import java.util.concurrent.Executors

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/11/20
 * This is entities for database
 */

@Database(entities = [UserModel::class, PlantModel::class,CultivationModel::class], version = 1, exportSchema = false)
abstract class ConnectDataBase : RoomDatabase() {

    abstract fun userDao(): UserDAO
    abstract fun plantDao(): PlantDAO
    abstract fun cultivationDao(): CultivationDAO

    companion object {
        private const val NAME_DATA_BASE = "plant.db"
        private const val JSON_NAME = "plants.json"
        private const val THREADS = 10

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

        fun providerDatabase(context: Context): ConnectDataBase? {
            return Room.databaseBuilder(context, ConnectDataBase::class.java, NAME_DATA_BASE)
                .allowMainThreadQueries()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Executors.newFixedThreadPool(THREADS).execute {
                            context.assets.open(JSON_NAME).use { inputStream ->
                                JsonReader(inputStream.reader()).use { jsonReader ->
                                    val plantType = object : TypeToken<List<PlantModel>>() {}.type
                                    val plants: List<PlantModel> =
                                        Gson().fromJson(jsonReader, plantType)
                                    providerDatabase(context)?.plantDao()?.insertPlants(plants)
                                }
                            }
                        }
                    }
                }).build()
        }
    }
}
