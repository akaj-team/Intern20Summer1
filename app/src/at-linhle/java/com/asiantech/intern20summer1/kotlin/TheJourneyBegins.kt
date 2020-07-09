package com.asiantech.intern20summer1.kotlin

private const val HUNDRED = 100

class TheJourneyBegins {
    fun add(param1: Int, param2: Int): Int {
        return param1 + param2
    }

    fun centuryFromYear(year: Int): Int {
        val number = HUNDRED
        return year / number + if (year % number != 0) 1 else 0
    }

    fun checkPalindrome(inputString: String): Boolean {
        return inputString == inputString.reversed()
    }
}
