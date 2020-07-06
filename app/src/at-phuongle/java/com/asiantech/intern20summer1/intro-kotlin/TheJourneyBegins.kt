package com.asiantech.intern20summer1.`intro-kotlin`

object TheJourneyBegins {
    fun add(param1 : Int, param2 : Int): Int {
        return param1 + param2;
    }

    fun centuryFromYear(year : Int) :Int {
        if (year <= 100) {
            return 1
        } else {
            if(year % 100 == 0) {
                return year / 100
            } else {
                return year / 100 + 1
            }
        }
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