package com.asiantech.intern20summer1.extension

import com.asiantech.intern20summer1.activity.MainActivity.Companion.LENGTH
import java.util.regex.Pattern

val patternPassword: Pattern = Pattern.compile("^(?=.*[0-9]).{8,16}\$")
val patternPhone: Pattern = Pattern.compile("^([0-9]){10}\$")

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

fun String.isPhoneNumber(): Boolean = patternPhone.matcher(this).matches()

fun String.isValidPasswordW4(): Boolean = patternPassword.matcher(this).matches()

fun String.isFullName() = this.isNotEmpty()

fun String.isConfirmPassword(password: String) = this == password
