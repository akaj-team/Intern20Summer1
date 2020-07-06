package com.asiantech.intern20summer1.kotlin

object TheJourneyBegins {
    fun add(param1 : Int, param2 : Int): Int {
        return param1 + param2;
    }

    fun centuryFromYear(year : Int) :Int {
        var result : Int;
        if (year <= 100) {
            result = 1
        } else {
            if(year % 100 == 0) {
                result = year / 100
            } else {
                result = year / 100 + 1
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
