package com.asiantech.intern20summer1.week7.entity

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int,
    val userName: String,
    val university: String,
    val homeTown: String,
    @Nullable val avatar: String?
)
