package com.asiantech.intern20summer1.w7.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.asiantech.intern20summer1.w7.model.CultivationModel
import com.asiantech.intern20summer1.w7.model.PlantModel
import com.asiantech.intern20summer1.w7.model.UserModel

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/11/20
 * This is entities for database
 */

@Database(
    entities = [UserModel::class, PlantModel::class, CultivationModel::class],
    version = 1,
    exportSchema = false
)
abstract class ConnectDataBase : RoomDatabase() {

    abstract fun userDao(): UserDAO
    abstract fun plantDao(): PlantDAO
    abstract fun cultivationDao(): CultivationDAO

    companion object {
        private const val NAME_DATA_BASE = "plant.db"
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
