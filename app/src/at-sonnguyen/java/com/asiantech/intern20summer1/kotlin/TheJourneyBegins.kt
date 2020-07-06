package com.asiantech.intern20summer1.kotlin

object TheJourneyBegins{
    private const val YEARS_IN_CENTURY = 100
    private const val YEAR = 2000
    private const val NUMBER1 = 15
    private const val NUMBER2 = 30
    /*
    *   returns the sum of two numbers.
    */
    private fun add(param1: Int, param2: Int) = param1+param2
    /*
    *   Given a year, return the century it is in.
    */
    private fun centuryFromYear(year: Int): Int = if(year% YEARS_IN_CENTURY == 0 ) year/ YEARS_IN_CENTURY else year/ YEARS_IN_CENTURY +1
    /*
    *   Given the string, check if it is a palindrome.
    */
    private fun checkPalindrome(inputString: String): Boolean = (inputString.reversed()  == inputString)
    @JvmStatic
    fun main(args: Array<String>) {
        var num1 = NUMBER1
        var num2 = NUMBER2
        var years : Int = YEAR
        var inputString : String = "abacaba"
        println("1:  $num1 + $num2 = ${add(num1,num2)}")
        println("2. $years in ${centuryFromYear(years)} century ")
        println("3. $inputString is a palindome ? ${checkPalindrome(inputString)}")
    }
}
