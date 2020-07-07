object TheJourneyBegins {
    private const val DIV = 100

    @JvmStatic
    fun main(args: Array<String>) {
        /**
         * Write a function that returns the sum of two numbers.
         */
        val result1 = add(5,6)
        println("1.Result: $result1")
        /**
         * Given a year, return the century it is in. The first century spans
         * from the year 1 up to and including the year 100, the second - from the year 101 up to and including the year 200, etc.
         */
        println("2.Result: ${centuryFromYear(2001)}")
        /**
         *Given the string, check if it is a palindrome.
         */
        println("3.Result: ${checkPalindrome("aabaa")}")

    }
    private fun add(param1: Int, param2: Int): Int {
        return param1 + param2
    }
    private fun centuryFromYear(year: Int): Int {
        if(year % 100 ==0){
            return year / DIV
        }else{
            return year / DIV +1
        }
    }
    private fun checkPalindrome(inputString: String):String{
        return (inputString.reversed()==inputString).toString()
    }
}
