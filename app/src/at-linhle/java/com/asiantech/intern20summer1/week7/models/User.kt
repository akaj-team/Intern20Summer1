package com.asiantech.intern20summer1.week7.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int? = null,
    var avatar: String? = "",
    var userName: String? = "",
    var university: String? = "",
    var homeTown: String? = ""
)
