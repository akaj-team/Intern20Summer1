package com.asiantech.intern20summer1.week12.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
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

    internal fun getPath(uri: Uri?, context: Context): String? {
        val result: String
        val cursor = uri?.let { context.contentResolver?.query(it, null, null, null, null) }
        if (cursor == null) {
            result = uri?.path.toString()
        } else {
            cursor.moveToFirst()
            @Suppress("DEPRECATION") val idx =
                cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }
}
