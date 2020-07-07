package com.asiantech.intern20summer1.kotlin

object ThroughTheFog {
    fun circleOfNumbers(n: Int, firstNumber: Int): Int {
        var numbers1: IntArray = IntArray(n / 2)
        var numbers2: IntArray = IntArray(numbers1.size)
        var m: Int = 0
        var indexOfOpposite: Int = 0

        for (i in 0 until numbers1.size) {
            numbers1[i] = m
            m++
        }

        for (i in 0 until numbers2.size) {
            numbers2[i] = m
            m++
        }

        for (i in 0 until numbers1.size) {
            if (firstNumber == numbers1[i]) {
                indexOfOpposite = i
                return numbers2[indexOfOpposite]
            } else if (firstNumber == numbers2[i]) {
                indexOfOpposite = i
                return numbers1[indexOfOpposite]
            }
        }
        return 0
    }

    fun depositProfit(deposit: Int, rate: Int, threshold: Int): Int {
        var r: Double = rate / 100.0
        var myDeposit: Double = deposit.toDouble()
        var numberOfYear: Int = 0
        while (true) {
            myDeposit += (r * myDeposit)
            numberOfYear++
            if (myDeposit >= threshold) {
                break
            }
        }

        return numberOfYear
    }

}
