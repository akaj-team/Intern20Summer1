package com.asiantech.intern20summer1.w7.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int? = null,
    @ColumnInfo(name = "username") val username: String? = null,
    @ColumnInfo(name = "university") val university: String? = null,
    @ColumnInfo(name = "homeTown") val homeTown: String? = null,
    @ColumnInfo(name = "imgUri") val imgUri: String? = null
) : Serializable
