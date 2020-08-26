package com.asiantech.intern20summer1.week9

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Song(
    var duration: String = "",
    var name: String? = "",
    var author: String? = "",
    var imgUri: String? = "",
    var songId: Long = 0,
    var path: String? = ""
) : Parcelable
