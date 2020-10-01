package com.asiantech.intern20summer1.week9

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Song(
    var name: String? = "",
    var artist: String? = "",
    var duration: Int = 0,
    var image: String? = "",
    var id: Long = 0,
    var path: String? = ""
):Parcelable
