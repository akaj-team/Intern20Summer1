package com.asiantech.intern20summer1.intro.kotlin

fun main() {
    //RUN main() with Coverage
    var obj: ThroughTheFog = ThroughTheFog()
    println("Ex 30: " + obj.circleOfNumbers(10, 2))

    println("Ex 31: " + obj.depositProfit(100, 20, 170))

    println("Ex 32: " + obj.absoluteValuesSumMinimization(mutableListOf(2, 4, 7)))

    println("Ex 33: " + obj.stringsRearrangement(mutableListOf("aba", "bbb", "bab")))
}

class ThroughTheFog {
    fun circleOfNumbers(n: Int, firstNumber: Int): Int {
        /**
         * Consider integer numbers from 0 to n - 1 written down along the circle in such a way
         * that the distance between any two neighboring numbers is equal (note that 0 and n - 1 are neighboring, too).

        Given n and firstNumber, find the number which is written in the radially opposite position to firstNumber.
         */
        return when {
            firstNumber < n / 2 -> firstNumber + n / 2
            firstNumber == n / 2 -> 0
            else -> firstNumber - n / 2
        }
    }

    fun depositProfit(deposit: Int, rate: Int, threshold: Int): Int {
        /**
         * You have deposited a specific amount of money into your bank account.
         * Each year your balance increases at the same growth rate.
         * With the assumption that you don't make any additional deposits,
         * find out how long it would take for your balance to pass a specific threshold.
         */
        var money = deposit.toDouble()
        var year = 1
        while (true) {
            money = money * (rate + 100) / 100
            if (money >= threshold) {
                return year
            }
            year++
        }
    }

    fun absoluteValuesSumMinimization(a: MutableList<Int>): Int {
        /**
         * Given a sorted array of integers a, your task is to determine which element of
         * a is closest to all other values of a. In other words, find the element x in a,
         * which minimizes the following sum:

        abs(a[0] - x) + abs(a[1] - x) + ... + abs(a[a.length - 1] - x)
        (where abs denotes the absolute value)

        If there are several possible answers, output the smallest one.
         */
        return if (a.size % 2 == 1) {
            a[a.size / 2]
        } else {
            a[a.size / 2 - 1]
        }
    }

    fun stringsRearrangement(inputArray: MutableList<String>): Boolean {
        /**
         * Given an array of equal-length strings, you'd like to know if it's possible to
         * rearrange the order of the elements in such a way that each consecutive pair
         * of strings differ by exactly one character. Return true if it's possible, and false if not.

        Note: You're only rearranging the order of the strings,
        not the order of the letters within the strings!
         */
        val used = BooleanArray(inputArray.size)
        findSequence(inputArray, null, used, 0)
        return success
    }

    var success = false // modified by findSequence

    private fun findSequence(
        a: MutableList<String>,
        prev: String?,
        used: BooleanArray,
        n: Int
    ) {
        /**
         * recursive backtracking procedure to find admissible sequence of strings in the array.
         * String prev is the previous string in the sequence, used[] keeps track of
         * which strings have been used so far, and n is the current length of the sequence.
         */
        if (n == a.size) {
            success = true
            return
        }
        for (i in a.indices) {
            if (!used[i] && (prev == null || differByOne(prev, a[i]))) {
                used[i] = true
                findSequence(a, a[i], used, n + 1)
                used[i] = false
            }
        }
    }

    private fun differByOne(a: String, b: String): Boolean {
        /**
         * return true if exist only 1 different char
         */
        var count = 0
        for (i in a.indices) {
            if (a[i] != b[i]) {
                count++
            }
        }
        return count == 1
    }
}
