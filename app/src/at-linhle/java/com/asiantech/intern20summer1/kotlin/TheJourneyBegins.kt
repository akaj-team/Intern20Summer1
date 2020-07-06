package com.asiantech.intern20summer1.kotlin

class TheJourneyBegins {
    /**
     * Write a function that returns the sum of two numbers.
     */
    fun add(param1: Int, param2: Int): Int {
        return param1 + param2
    }

    /**
     * Given a year, return the century it is in. The first century spans from the year 1 up to
     * and including the year 100, the second - from the year 101 up to and including the year
     * 200, etc.
     */
    fun centuryFromYear(year: Int): Int {
        val number = 100
        return year / number + if (year % number != 0) 1 else 0
    }

    /**
     * Given the string, check if it is a palindrome.
     */
    fun checkPalindrome(inputString: String): Boolean {
        return inputString == inputString.reversed()
    }
}
