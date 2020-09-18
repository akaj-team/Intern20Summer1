package com.asiantech.intern20summer1.w11.utils

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.coroutineContext

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 01/09/2020.
 * This is AppUtils class. this contain method util for project
 */
class AppUtils {
    companion object {
        private const val FORMAT_CODE_BEFORE = "yyyy-MM-dd'T'HH:mm:ss"
        private const val FORMAT_CODE_AFTER = "HH:mm dd/MM/yyyy"
    }

    internal fun showToast(context: Context, any: Any, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, any.toString(), duration).show()
    }

    internal fun convertDate(date: String): String {
        var dateReturn = ""
        SimpleDateFormat(FORMAT_CODE_BEFORE, Locale.US).parse(date)?.let {
            dateReturn = SimpleDateFormat(FORMAT_CODE_AFTER, Locale.US).format(it)
        }
        return dateReturn
    }
}
