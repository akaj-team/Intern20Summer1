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
        val stack = Stack<String>()
        var str = ""
        var temp = ""
        for (i in inputString.indices) {
            if (inputString[i] == '(') {
                stack.push(inputString[i].toString())
            } else {
                if (!stack.isEmpty()) {
                    if (inputString[i] == ')') {
                        while (stack.peek() != '('.toString()) {
                            temp += stack.peek()
                            stack.pop()
                        }
                        stack.pop()
                        if (!stack.isEmpty()) {
                            for (j in temp.indices) {
                                stack.push(temp[j].toString())
                            }

                        } else {
                            str += temp
                        }
                        temp = ""
                    } else {
                        stack.push(inputString[i].toString())
                    }
                } else {
                    str += inputString[i]
                }
            }
        }
        return str
    }
}
