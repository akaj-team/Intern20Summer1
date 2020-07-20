package extension

import activity.MainActivity.Companion.LENGTH
import fragment.FragmentSignIn.Companion.pattern
import fragment.FragmentSignUp.Companion.LENGTH_PHONE_NUMBER

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

fun String.isPhoneNumber(): Boolean = this.length == LENGTH_PHONE_NUMBER

fun String.isValidPasswordW4(): Boolean = pattern.matcher(this).matches()

fun String.isFullName() = this.isNotEmpty()
