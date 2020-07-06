package com.asiantech.intern20summer1.kotlin

object TheJourneyBegins{
    /*
    *   returns the sum of two numbers.
    */
    private fun add(param1: Int, param2: Int) = param1+param2
    /*
    *   Given a year, return the century it is in.
    */
    private fun centuryFromYear(year: Int): Int = if(year%100 == 0 ) year/100 else year/100 +1
    /*
    *   Given the string, check if it is a palindrome.
    */
    private fun checkPalindrome(inputString: String): Boolean = (inputString.reversed()  == inputString)
    @JvmStatic
    fun main(args: Array<String>) {
        var num1 = 15
        var num2 = 30
        var years : Int = 2000
        var inputString : String = "abacaba"
        println("1:  $num1 + $num2 = ${add(num1,num2)}")
        println("2. $years in ${centuryFromYear(years)} century ")
        println("3. $inputString is a palindome ? ${checkPalindrome(inputString)}")
    }

}