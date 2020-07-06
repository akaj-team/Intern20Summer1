package com.asiantech.intern20summer1.intro.kotlin

object TheJourneyBegins{
    @JvmStatic
    fun main(args: Array<String>) {
        //RUN main() with Coverage
        println("Ex 1: " + add(3, 6))

        println("Ex 2: " + centuryFromYear(2001))

        println("Ex 3: " + checkPalindrome("acbca"))
    }

    private const val HUNDRED: Int = 100

    fun add(param1: Int, param2: Int): Int {
        /**
         * Write a function that returns the sum of two numbers.
         */
        return param1 + param2
    }

    fun centuryFromYear(year: Int): Int {
        /**
         * Given a year, return the century it is in. T
         * he first century spans from the year 1 up to and including the year 100,
         * the second - from the year 101 up to and including the year 200, etc.
         */
        var result = 0
        if (year / HUNDRED.toDouble() == (year / HUNDRED).toDouble()) result = year / HUNDRED
        else result = year / HUNDRED + 1
        return result
    }

    fun checkPalindrome(inputString: String): Boolean {
        /**
         * Given the string, check if it is a palindrome.
         */
        var result = true
        val length = inputString.length
        for (i in 0 until length / 2) {
            if (inputString[i] != inputString[length - 1 - i]) {
                result = false
                break
            }
        }
        return result
    }
}
