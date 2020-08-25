package com.asiantech.intern20summer1.week9.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Song(
    var songName: String? = "",
    var artist: String? = "",
    var duration: Int = 0,
    var imgUri: String? = "",
    var songId: Long = 0,
    var path: String? = ""
) : Parcelable
