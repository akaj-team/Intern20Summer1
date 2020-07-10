package utils

/*
 * Check Email by regex
 */
fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/*
 * Check Password
 */
fun String.isValidPassword(): Boolean {
    if (this.isEmpty()) {
        return false
    }
    return (this[0] == this[0].toUpperCase() && !this[0].isDigit() && this.length >= LENGTH)
}