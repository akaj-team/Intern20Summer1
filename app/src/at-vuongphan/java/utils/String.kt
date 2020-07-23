package utils

import com.asiantech.intern20summer1.MainActivity.Companion.LENGTH

/*
 * Check Email by regex
 */
fun String.isValidEmail() =
    android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

/*
 * Check Password
 */
fun String.isValidPassword() =
    this.isNotEmpty() && (this[0] == this[0].toUpperCase() &&
            !this[0].isDigit() && this.length >= LENGTH)
