package com.asiantech.intern20summer1.w7.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val userName: String = "",
    val university: String = "",
    val homeTown: String = "",
    val imgUri: String? = null
) : Serializable
