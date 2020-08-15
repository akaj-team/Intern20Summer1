package com.asiantech.intern20summer1.week7.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var avatar: String,
    var userName: String,
    var university: String,
    var homeTown: String
) : Parcelable