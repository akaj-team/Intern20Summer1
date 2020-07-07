package com.asiantech.intern20summer1.kotlinIntro

class TheJourneyBegin{
    fun add(param1: Int, param2: Int): Int = param1 + param2
    fun centuryFromYear(year: Int): Int {
        return (year-1)/100 +1
    }
    fun checkPalindrome(inputString: String): Boolean {
        for(i in 0..inputString.length/2){
            if(inputString[i]!=inputString[inputString.length-1-i]){
                return false
            }
        }
        return true
    }

}

fun main(args: Array<String>) {
    var nah = TheJourneyBegin()
    println(nah.add(2,3))
    println(nah.centuryFromYear(2020))
    println(nah.checkPalindrome("asdvv"))
}
