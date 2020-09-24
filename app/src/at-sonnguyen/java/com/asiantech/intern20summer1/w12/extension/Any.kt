package com.asiantech.intern20summer1.w12.extension

import android.content.Context
import android.widget.Toast
import com.asiantech.intern20summer1.w12.ui.home.HomActivity.Companion.BAD_REQUEST_CODE
import com.asiantech.intern20summer1.w12.ui.home.HomActivity.Companion.REQUEST_TIMEOUT_CODE
import com.asiantech.intern20summer1.w12.ui.home.HomActivity.Companion.SERVER_NOT_FOUND_CODE

fun isValidEmail(string: String) =
    android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches()

fun isValidPassword(string: String): Boolean {
    val passwordRegex = "^(?=.*[a-z])(?=.*\\d)[a-zA-Z\\d]{8,16}$".toRegex()
    return string.matches(passwordRegex)
}

fun makeToastError(int: Int, context: Context) {
    when (int) {
        BAD_REQUEST_CODE -> {
            Toast.makeText(
                context,
                "mat khau hoac tai khoan khong dung",
                Toast.LENGTH_SHORT
            ).show()
        }
        SERVER_NOT_FOUND_CODE -> {
            Toast.makeText(
                context,
                "Server khong kha dung",
                Toast.LENGTH_SHORT
            ).show()
        }
        REQUEST_TIMEOUT_CODE -> {
            Toast.makeText(
                context,
                "Request Time out",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
