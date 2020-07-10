package utils

fun String.isValidPassword(): Boolean {
    if (this.isEmpty()) {
        return false
    }
    return (this[0] == this[0].toUpperCase() && !this[0].isDigit() && this.length >= 6)
}
