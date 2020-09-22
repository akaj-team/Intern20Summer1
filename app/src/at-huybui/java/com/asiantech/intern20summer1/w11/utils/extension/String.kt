package com.asiantech.intern20summer1.w11.utils.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 22/09/2020.
 * This is String TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */

fun String.convertDate(): String {
    var dateReturn = ""
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(this)?.let {
        dateReturn = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.US).format(it)
    }
    return dateReturn
}