package com.asiantech.intern20summer1.week12.utils

import java.text.SimpleDateFormat
import java.util.*

class UtilsConvert {
    companion object {
        private const val FORMAT_CODE_BEFORE = "yyyy-MM-dd'T'HH:mm:ss"
        private const val FORMAT_CODE_AFTER = "HH:mm dd/MM/yyyy"
    }

    internal fun convertDate(date: String): String {
        var dateReturn = ""
        SimpleDateFormat(FORMAT_CODE_BEFORE, Locale.US).parse(date).let {
            @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            dateReturn = SimpleDateFormat(FORMAT_CODE_AFTER, Locale.US).format(it)
        }
        return dateReturn
    }
}
