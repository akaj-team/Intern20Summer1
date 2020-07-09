package com.asiantech.intern20summer1.kotlin

import java.util.*

class SmoothSailing {
    fun allLongestStrings(inputArray: MutableList<String>): MutableList<String> {
        var max = 0
        for (i in 0 until inputArray.size) {
            max = maxOf(max, inputArray[i].length)
        }
        val outputArray: MutableList<String> = mutableListOf()
        for (k in 0 until inputArray.size) {
            if (inputArray[k].length == max) {
                outputArray.add(inputArray[k])
            }
        }
        return outputArray
    }

    fun commonCharacterCount(s1: String, s2: String): Int {
        var count = 0
        val arr: MutableList<Int> = mutableListOf()
        for (i in s1.indices) {
            for (j in s2.indices) {
                if (!arr.contains(j) && s1[i] == s2[j]) {
                    count++
                    arr.add(j)
                    break
                }
            }
        }
        return count
    }

    fun isLucky(n: Int): Boolean {
        val str = n.toString()
        var sum = 0
        for (i in 0 until str.length / 2) {
            sum += (str[i] - str[str.length - 1 - i])
        }
        return sum == 0
    }

    fun sortByHeight(a: MutableList<Int>): MutableList<Int> {
        for (i in 0 until a.size - 1) {
            for (j in i + 1 until a.size) {
                if (a[i] > a[j] && a[i] != -1 && a[j] != -1) {
                    a[i] = a[j].also { a[j] = a[i] }
                }
            }
        }
        return a
    }

    fun reverseInParentheses(inputString: String): String {
        val stack = ArrayDeque<StringBuilder>()
        var curr = StringBuilder()
        for (char in inputString) when (char) {
            '(' -> {
                stack.push(curr)
                curr = StringBuilder()
            }
            ')' -> {
                curr.reverse()
                val prev = stack.pop()
                prev.append(curr)
                curr = prev
            }
            else -> curr.append(char)
        }
        return curr.toString()
    }
}
