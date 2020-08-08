package com.asiantech.intern20summer1.w7.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId") val userId: Int = 0,
    @ColumnInfo(name = "userName")val userName: String = "",
    @ColumnInfo(name = "university")val university: String = "",
    @ColumnInfo(name = "homeTown")val homeTown: String = "",
    @ColumnInfo(name = "imgUri")val imgUri: String? = null
) : Serializable