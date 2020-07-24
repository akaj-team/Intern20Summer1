package com.asiantech.intern20summer1.week4

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var name: String = "",
    var pass: String ="",
    var mobile: String = "",
    var email: String = "",
    var avatar: String = ""
) : Parcelable
