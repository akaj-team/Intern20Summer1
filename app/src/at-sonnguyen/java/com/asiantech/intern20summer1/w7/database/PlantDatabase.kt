package com.asiantech.intern20summer1.w7.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.asiantech.intern20summer1.w7.database.dao.CultivationDao
import com.asiantech.intern20summer1.w7.database.dao.PlantDao
import com.asiantech.intern20summer1.w7.database.dao.UserDao
import com.asiantech.intern20summer1.w7.database.data.Cultivation
import com.asiantech.intern20summer1.w7.database.data.Plant
import com.asiantech.intern20summer1.w7.database.data.User

@Database(entities = [User :: class,Plant::class,Cultivation::class],version = 1,exportSchema = false)
abstract class PlantDatabase :RoomDatabase(){
    abstract fun userDao() : UserDao
    abstract fun plantDao() : PlantDao
    abstract fun cultivationDao() : CultivationDao

    companion object{
        private const val DATABASE_NAME = "plant.db"
        private var instance : PlantDatabase? = null
        fun getInstance(context: Context) : PlantDatabase? {
            return if (instance == null){
                Room.databaseBuilder(context,PlantDatabase::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build()
            }else{
                instance
            }
        }
    }
}
