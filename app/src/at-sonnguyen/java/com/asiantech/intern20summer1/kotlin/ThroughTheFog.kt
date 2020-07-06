package com.asiantech.intern20summer1.kotlin

object ThroughTheFog{
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
            money += money*rate/100
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
    private fun stringsRearrangement(inputArray:  MutableList<String>): Boolean {
        return p(inputArray.size, inputArray.size, inputArray, false)
    }

    private fun p(n: Int, s: Int, a:  MutableList<String>, b: Boolean): Boolean {
        var b = b
        if (s == 1) {
            var c = 0
            for (i in 0 until n - 1) {
                for (j in a[i].indices) {
                    if (a[i].substring(j, j + 1) != a[i + 1].substring(j, j + 1)) {
                        c++
                    }
                }
                if (a[i] == a[i + 1]) {
                    c += 10
                }
            }
            if (c == a.size - 1) {
                return true
            }
        }
        for (i in 0 until s) {
            b = p(n, s - 1, a, b)
            if (s % 2 == 0) {
                val t = a[i]
                a[i] = a[s - 1]
                a[s - 1] = t
            } else {
                val t = a[0]
                a[0] = a[s - 1]
                a[s - 1] = t
            }
        }
        return b
    }
    @JvmStatic
    fun main(args: Array<String>) {
        val deposit = 100
        val rate = 20
        val threshold = 200
        val intArray : MutableList<Int> = mutableListOf(-10, 100, 200, 300, 400, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500)
        val inputStringArray : MutableList<String> = mutableListOf("ab","bb","aa")
        println("30.result: ${circleOfNumbers(15,10)}")
        println("31. spend ${depositProfit(100,20,200)} month to gain $threshold from $deposit with rate = $rate")
        println("32. min of absolute value Sum in $intArray is ${absoluteValuesSumMinimization(intArray)}")
        println("33. Result : ${stringsRearrangement(inputStringArray)}")
    }
}