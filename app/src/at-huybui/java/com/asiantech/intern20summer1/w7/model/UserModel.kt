package com.asiantech.intern20summer1.w7.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/04/20
 * This is data class for user model
 */

@Entity(tableName = "user")
data class UserModel(
    @PrimaryKey(autoGenerate = true) val userId: Int? = null,
    @ColumnInfo(name = "userName") val userName: String? = null,
    @ColumnInfo(name = "university") val university: String? = null,
    @ColumnInfo(name = "homeTown") val homeTown: String? = null,
    @ColumnInfo(name = "imgUri") val imgUri: String? = null
) : Serializable
