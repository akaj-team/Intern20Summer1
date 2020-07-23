package com.asiantech.intern20summer1.w4.extension

import com.asiantech.intern20summer1.w4.fragment.RegisterFragment

fun isValidEmail(string: String) =
    android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches()

fun isValidPassword(string: String): Boolean {
    if (string.length !in RegisterFragment.MIN_LENGTH_PASSWORD..RegisterFragment.MAX_LENGTH_PASSWORD) {
        return false
    } else {
        for (i in string.indices) {
            if (string[i].isDigit()) return true
        }
    }
    return false
}

fun isValidPhoneNumber(string: String): Boolean {
    if (string.length != RegisterFragment.PHONE_NUMBER_LENGTH) {
        return false
    }
    for (i in string.indices) {
        if (!string[i].isDigit()) {
            return false
        }
    }
    return true
}
