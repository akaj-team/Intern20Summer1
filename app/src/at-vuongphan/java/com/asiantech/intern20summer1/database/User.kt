package com.asiantech.intern20summer1.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    var userId: Int = 0,
    @ColumnInfo(name = "userName")
    var userName: String = "",
    @ColumnInfo(name = "university")
    var university: String = "",
    @ColumnInfo(name = "homeTown")
    var homeTown: String = "",
    @ColumnInfo(name = "avatar")
    var avatar: String = ""
)
