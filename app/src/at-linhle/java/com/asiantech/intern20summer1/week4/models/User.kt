package com.asiantech.intern20summer1.week4.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var avatar: String? = "",
    var fullName: String? = "",
    var email: String? = "",
    var phone: String? = "",
    var password: String? = ""
) : Parcelable
