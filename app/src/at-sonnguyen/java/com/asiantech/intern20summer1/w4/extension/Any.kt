package com.asiantech.intern20summer1.w4.extension

import com.asiantech.intern20summer1.w4.fragment.RegisterFragment

fun isValidEmail(string: String) =
    android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches()

fun isValidPassword(string: String): Boolean {
    val passwordRegex = "^(?=.*[a-z])(?=.*\\d)[a-zA-Z\\d]{8,16}$".toRegex()
    return string.matches(passwordRegex)
}

fun isValidPhoneNumber(string: String) =
    string.length == RegisterFragment.PHONE_NUMBER_LENGTH
