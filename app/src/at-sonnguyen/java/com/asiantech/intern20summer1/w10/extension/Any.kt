package com.asiantech.intern20summer1.w10.extension

fun isValidEmail(string: String) =
    android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches()

fun isValidPassword(string: String): Boolean {
    val passwordRegex = "^(?=.*[a-z])(?=.*\\d)[a-zA-Z\\d]{8,16}$".toRegex()
    return string.matches(passwordRegex)
}
