package com.asiantech.intern20summer1.kotlin

object ThroughTheFog{
    private const val DEPOSIT = 100
    private const val RATE = 20
    private const val THRESHOLD = 200
    private const val NUMBER_TO_GET_PERCENT = 100
    private const val STEP_INDEX = 10
    /*
    *   Given n and firstNumber, find the number which is written in the radially opposite position to firstNumber.
    */
    private fun circleOfNumbers(n: Int, firstNumber: Int): Int = (firstNumber+n/2)%n
    /*
    *   how long it would take for your balance to pass a specific threshold
    */
    private fun depositProfit(deposit: Int, rate: Int, threshold: Int): Int {
        var result : Int = 0
        var money : Double = deposit + 0.0
        while (money < threshold){
            result++
            money += money*rate/ NUMBER_TO_GET_PERCENT
        }
        return result
    }
    /*
    *   Given a sorted array of integers a,
    *   task is to determine which element of a is closest to all other values of a.
    *   In other words, find the element x in a, which minimizes the following sum:
    */
    private fun absoluteValuesSumMinimization(a: MutableList<Int>): Int = a[(a.size-1)/2]
    /*
    *   Given an array of equal-length strings, you'd like to know
    * if it's possible to rearrange the order of the elements
    * in such a way that each consecutive pair of strings differ by exactly one character.
    * Return true if it's possible, and false if not.
    */
    private fun stringsRearrangement(inputArray: MutableList<String>): Boolean = inputArray
        .run {
            var margins = 0
            var max = 0
            var nextMax = 0
            forEach { s1 ->
                val sum = inputArray.fold(0) { acc, s2 ->
                    acc + if (s1.filterIndexed { index, c -> c != s2[index] }.count() == 1) 1 else 0
                }
                print("$sum ")
                if (sum == 0) return false
                if (sum == 1 && this[0].count() > 1) {
                    if (margins == 2) return false
                    margins++
                }
                if (max < sum) max = sum
                if (nextMax < sum && max != sum) nextMax = sum
            }

            return max == inputArray.count() - 1 || max - nextMax < 3
        }
    @JvmStatic
    fun main(args: Array<String>) {
        val deposit = DEPOSIT
        val rate = RATE
        val threshold = THRESHOLD
        val intArray : MutableList<Int> = mutableListOf(-10, 100, 200, 300, 400, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500)
        val inputStringArray : MutableList<String> = mutableListOf("ab","bb","aa")
        println("30.result: ${circleOfNumbers(15,10)}")
        println("31. spend ${depositProfit(deposit,rate,threshold)} month to gain $threshold from $deposit with rate = $rate")
        println("32. min of absolute value Sum in $intArray is ${absoluteValuesSumMinimization(intArray)}")
        println("33. Result : ${stringsRearrangement(inputStringArray)}")
    }
}
