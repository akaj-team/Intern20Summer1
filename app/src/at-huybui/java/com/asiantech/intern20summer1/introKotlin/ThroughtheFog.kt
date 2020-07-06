package com.asiantech.intern20summer1.introKotlin

class ThroughtheFog {
    /**
     * 30
     * Consider integer numbers from 0 to n - 1 written down along the circle in such a way
     * that the distance between any two neighboring numbers is equal (note that 0 and n - 1 are neighboring, too).
     * Given n and firstNumber, find the number which is written in the radially opposite position to firstNumber.
     */
    fun circleOfNumbers(n: Int, firstNumber: Int): Int {
        return if (firstNumber < n / 2) {
            firstNumber + n / 2
        } else {
            firstNumber - n / 2
        }
    }


    /**
     * 31
     * You have deposited a specific amount of money into your bank account.
     * Each year your balance increases at the same growth rate.
     * With the asmSumption that you don't make any additional deposits,
     * find mOut how long it would take for your balance to pass a specific threshold.
     */
    fun depositProfit(deposit: Int, rate: Int, threshold: Int): Int {
        var mYear = 0 // khai báo số năm
        var newDeposit =
            deposit.toDouble() // Ép deposit qua double để tránh ép kiểu nhiều lần tốn chu kỳ máy
        while (newDeposit < threshold) {
            newDeposit += newDeposit * (rate / 100.0)
            mYear++
        }
        return mYear
    }

    /** 32 **
     *Given a sorted array of integers a, your task is to determine which element
     * of a is closest to all other values of a.
     * In other words, find the element x in a, which minimizes the following sum:
     */
    fun absoluteValuesSumMinimization(a: MutableList<Int>): Int {
        var mSum = a.map { Math.abs(a[0] - it) }.sum()
        var mXout = a[0]
        a.forEach { i ->
            val sumNew = a.map { Math.abs(i - it) }.sum()
            if (sumNew < mSum) {
                mSum = sumNew
                mXout = i
            }
        }
        return mXout
    }

}
