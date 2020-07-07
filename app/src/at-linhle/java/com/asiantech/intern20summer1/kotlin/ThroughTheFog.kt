package com.asiantech.intern20summer1.kotlin

class ThroughTheFog {
    fun circleOfNumbers(n: Int, firstNumber: Int): Int {
        return when {
            (firstNumber > n / 2) -> firstNumber - n / 2
            (firstNumber < n / 2) -> n / 2 + firstNumber
            else -> 0
        }
    }

    fun depositProfit(deposit: Int, rate: Int, threshold: Int): Int =
        kotlin.math.ceil(
            kotlin.math.log(threshold / deposit.toDouble(), 1 + rate / 100.0)
        ).toInt()

    fun absoluteValuesSumMinimization(a: MutableList<Int>): Int {
        return a[(a.size - 1) / 2]
    }
}
