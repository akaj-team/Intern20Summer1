package com.asiantech.intern20summer1.w0

import java.text.SimpleDateFormat
import java.util.*

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 05/09/2020.
 * This is TestClass TODO("Not yet implemented")
 */


fun main() {
    print(TestClass().convertDate("2020-08-31T17:07:29.000000Z"))
}

class TestClass {
    companion object {
        private const val FORMAT_CODE_BEFORE = "yyyy-MM-dd'T'HH:mm:ss"
        private const val DATE = "2020-08-31T17:07:29.000000Z"
        private const val FORMAT_CODE_AFTER = "HH:mm dd/MM/yyyy"
    }

    internal fun convertDate(date: String): String {
        var dateReturn = ""
        SimpleDateFormat(FORMAT_CODE_BEFORE, Locale.US).parse(date)?.let {
            dateReturn = SimpleDateFormat(FORMAT_CODE_AFTER, Locale.US).format(it)
        }
        return dateReturn
    }
}
