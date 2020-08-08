package com.asiantech.intern20summer1.w7.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.asiantech.intern20summer1.w7.model.PlantModel
import com.asiantech.intern20summer1.w7.model.UserModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import java.util.concurrent.Executors

@Database(entities = [UserModel::class, PlantModel::class], version = 1, exportSchema = false)
abstract class ConnectDataBase : RoomDatabase() {

    abstract fun userDao(): UserDAO
    abstract fun plantDao(): PlantDAO

    companion object {
        const val NAME_DATA_BASE = "plant.db"

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
                        Executors.newFixedThreadPool(10).execute {
                            Log.d("XXXX", "open file")
                            context.assets.open("plants.json").use { inputStream ->
                                JsonReader(inputStream.reader()).use { jsonReader ->
                                    val plantType = object : TypeToken<List<PlantModel>>() {}.type
                                    val plants: List<PlantModel> =
                                        Gson().fromJson(jsonReader, plantType)

                                    plants.forEach {
                                        Log.d("XXXX", it.toString())
                                    }
                                    providerDatabase(context)?.plantDao()?.insertPlants(plants)
                                }
                            }
                        }
                    }
                }).build()
        }
    }
}
