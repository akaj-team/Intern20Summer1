package com.asiantech.intern20summer1.week4.other

fun isValidEmail(email: String) =
    androidx.core.util.PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()

fun isValidPassword(password: String) = password.matches(PASSWORD_REGEX.toRegex())

fun isValidMobileNumber(mobileNumber: String) = mobileNumber.matches(MOBILE_NUMBER_REGEX.toRegex())
