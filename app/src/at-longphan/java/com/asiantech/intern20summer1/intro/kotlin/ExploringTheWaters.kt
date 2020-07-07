package com.asiantech.intern20summer1.intro.kotlin

import java.util.*

object ExploringTheWaters {
    @JvmStatic
    fun main(args: Array<String>) {
        //RUN main() with Coverage
        println("Ex 14: " + alternatingSums(mutableListOf(50, 60, 60, 45, 70)))

        println("Ex 15: ")
        println(addBorder(mutableListOf("abc", "ded")))

        println("Ex 16: " + areSimilar(mutableListOf(1, 2, 3), mutableListOf(2, 1, 3)))

        println("Ex 17: " + arrayChange(mutableListOf(-1000, 0, -2, 0)))

        println("Ex 18: " + palindromeRearranging("abbcabb"))
    }

    private fun alternatingSums(a: MutableList<Int>): MutableList<Int> {
        /**
         * Several people are standing in a row and need to be divided into two teams.
         * The first person goes into team 1, the second goes into team 2,
         * the third goes into team 1 again, the fourth into team 2, and so on.

        You are given an array of positive integers - the weights of the people.
        Return an array of two integers, where the first element is the total weight of team 1,
        and the second element is the total weight of team 2 after the division is complete.
         */
        var result: MutableList<Int> = mutableListOf(0, 0)
        for (i in a.indices) {
            if (i % 2 == 0) result[0] += a[i]
            else result[1] += a[i]
        }
        return result
    }

    private fun addBorder(picture: MutableList<String>): MutableList<String> {
        /**
         * Given a rectangular matrix of characters, add a border of asterisks(*) to it.
         */
        var a = MutableList(picture.size + 2) { "" }
        a[0] = "*".repeat(picture[0].length + 2)
        a[a.size - 1] = a[0]
        for (i in 1..a.size - 2) {
            a[i] = "*" + picture[i - 1] + "*"
        }
        return a
    }

    private fun areSimilar(A: MutableList<Int>, B: MutableList<Int>): Boolean {
        /**
         * Two arrays are called similar if one can be obtained from another by swapping at
         * most one pair of elements in one of the arrays.

        Given two arrays a and b, check whether they are similar.
         */
        var a = A
        var b = B
        var result = true
        if (a.size != b.size) {
            result = false
        } else {
            var count = 0
            var aDiff = 0
            var bDiff = 0
            var iDiff = 0
            for (i in 0 until a.size) {
                if (a[i] != b[i] && count == 0) {
                    aDiff = a[i]
                    bDiff = b[i]
                    iDiff = i
                    count++
                    continue
                }
                if (a[i] != b[i] && count == 1) {
                    if (a[i] != bDiff || b[i] != aDiff) {
                        result = false
                        break
                    } else {
                        a[iDiff] = a[i]
                        a[i] = aDiff
                        break
                    }
                }
            }
            a.forEachIndexed { i, v ->
                if (a[i] != b[i]) {
                    result = false
                }
            }
        }
        return result
    }

    private fun arrayChange(a: MutableList<Int>): Int {
        /**
         * You are given an array of integers. On each move you are allowed to increase
         * exactly one of its element by one. Find the minimal number of moves required
         * to obtain a strictly increasing sequence from the input.
         */
        var times = 0
        for (i in 1 until a.size) {
            if (a[i - 1] >= a[i]) {
                times += (a[i - 1] - a[i] + 1)
                a[i] = a[i - 1] + 1
            }
        }
        return times
    }

    private fun palindromeRearranging(a: String): Boolean {
        /**
         * Given a string, find out if its characters can be rearranged to form a palindrome.
         */
        val map = HashMap<Char, Int?>()
        for (i in a.indices) {
            val ch = a[i]
            if (map.containsKey(ch)) {
                map[ch] = map[ch]!! + 1
            } else {
                map[ch] = 1
            }
        }
        var count = 0
        for ((k, v) in map) {
            if (map[k]!! % 2 !== 0) {
                count++
                if (count == 2) return false
            }
        }
        return true
    }
}
