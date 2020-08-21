package com.asiantech.intern20summer1.data.w9

import android.net.Uri

data class Music(
    var duration: Int,
    var name: String,
    var uri: Uri,
    var artist: String,
    var image: Uri
)
