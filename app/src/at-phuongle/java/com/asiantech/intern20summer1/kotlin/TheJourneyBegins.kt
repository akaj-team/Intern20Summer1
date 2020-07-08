package com.asiantech.intern20summer1.kotlin

object TheJourneyBegins {
    private const val CENTURY = 100

    fun add(param1 : Int, param2 : Int): Int {
        return param1 + param2;
    }

    fun centuryFromYear(year : Int) :Int {
        var result : Int;
        if (year <= CENTURY) {
            result = 1
        } else {
            if(year % CENTURY == 0) {
                result = year / CENTURY
            } else {
                result = year / CENTURY + 1
            }
        }

        return result
    }

    fun checkPalindrome(inputString : String) : Boolean {
        var n = inputString.length
        for (i in 0..(n / 2)) {
            var a1 : Char = inputString[i]
            var a2 : Char = inputString[n - i - 1]

            if(a1 != a2) {
                return false
            }
        }
        return true
    }

}
