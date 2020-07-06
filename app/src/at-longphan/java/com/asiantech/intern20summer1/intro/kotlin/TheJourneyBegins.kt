package com.asiantech.intern20summer1.intro.kotlin

fun main() {
    //RUN main() with Coverage
    var obj : TheJourneyBegins = TheJourneyBegins()
    println("Ex 1: " + obj.add(3, 6))

    println("Ex 2: " + obj.centuryFromYear(2001))

    println("Ex 3: " + obj.checkPalindrome("acbca"))
}

class TheJourneyBegins{
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
        if (year / 100.0 == (year / 100).toDouble()) result = year / 100
        else result = year / 100 + 1
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
