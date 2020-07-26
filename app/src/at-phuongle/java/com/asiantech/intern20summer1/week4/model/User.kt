package com.asiantech.intern20summer1.week4.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var fullName: String,
    var avatarUri: String,
    var email: String,
    var mobileNumber: String,
    var password: String
) : Parcelable
