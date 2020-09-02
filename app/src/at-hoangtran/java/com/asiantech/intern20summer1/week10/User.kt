package com.asiantech.intern20summer1.week10

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var name: String = "",
    var pass: String = "",
    var mobile: String = "",
    var email: String = ""
) : Parcelable
