package com.asiantech.intern20summer1.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int? = null,
    @ColumnInfo(name = "userName") val userName: String? = null,
    @ColumnInfo(name = "university") val university: String? = null,
    @ColumnInfo(name = "homeTown") val homeTown: String? = null,
    @ColumnInfo(name = "imgUri") val imgUri: String? = null
)
