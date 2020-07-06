package com.asiantech.intern20summer1.KotlinIntro

import java.util.*
import kotlin.String as String1

class SmoothSailing {
    fun allLongestStrings(inputArray: MutableList<String1>): MutableList<String1> {
//        Given an array of strings, return another array containing all of its longest strings.
        var max = 0
        var longest = mutableListOf<String1>()
        for (i in 0..inputArray.size - 1) {
            if (max < inputArray.get(i).length) {
                max = inputArray.get(i).length
            }
        }
        for (i in 0..inputArray.size - 1) {
            if (max == inputArray.get(i).length) {
                longest.add(inputArray.get(i))
            }
        }
        return longest
    }

    fun commonCharacterCount(s1: String1, s2: String1): Int {
//        Given two strings, find the number of common characters between them.
        var a = 0
        for (i in 0 until s2.length) {
            var count = 0
            for (j in 0 until s1.length) {
                if (s2[i] == s1[j]) {
                    count++
                }
            }
            var dup = 0
            for (j in 0 until i) {
                if (s2[j] == s2[i]) {
                    dup++
                }
            }
            if (dup < count) {
                a++
            }
        }
        return a
    }

    fun isLucky(n: Int): Boolean {
//        Ticket numbers usually consist of an even number of digits. A ticket number is considered
//        lucky if the sum of the first half of the digits is equal to the sum of the second half.
//
//        Given a ticket number n, determine if it's lucky or not.
        var s = n.toString()
        var head = 0
        var tail = 0
        for (i in 0..(s.length - 1) / 2) {
            head += s[i].toInt()
            tail += s[s.length - 1 - i].toInt()
        }
        return (head == tail)
    }

    fun sortByHeight(a: MutableList<Int>): MutableList<Int> {
//        Some people are standing in a row in a park . There are trees between them which cannot
//                be moved . Your task is to rearrange the people by their heights in a non-descending
//        order without moving the trees. People can be very tall!
        var tmp = 0
        for (i in 0..a.size - 2) {
            if (a[i] != -1) {
                for (j in i + 1..a.size - 1) {
                    if (a[j] != -1) {
                        if (a[i] > a[j]) {
                            tmp = a[i]
                            a[i] = a[j]
                            a[j] = tmp
                        }
                    }
                }
            }

        }
        return a
    }

    fun reverseInParentheses(inputString: String1): String1 {
//        Write a function that reverses characters in (possibly nested) parentheses in the input string.
//
//        Input strings will always be well-formed with matching ()s.
        var stack = Stack<String1>()
        var str = ""
        var tmp = ""
        for (i in 0..inputString.length - 1) {
            if (inputString[i] == '(') {
                stack.push(inputString[i].toString())
            } else if (!stack.isEmpty()) {
                if (inputString[i] != ')') {
                    stack.push(inputString[i].toString())
                } else {
                    while (stack.peek() != '('.toString()) {
                        tmp += stack.peek()
                        stack.pop()
                    }
                    stack.pop()
                    if (!stack.isEmpty()) {
                        for (j in 0..tmp.length - 1) {
                            stack.push(tmp[j].toString())
                        }
                    } else {
                        str += tmp
                    }
                    tmp = ""
                }
            } else {
                str += inputString[i].toString()
            }
        }
        return str
    }


}

fun main(args: Array<String1>) {
    var nah = SmoothSailing()
    val s = mutableListOf<String1>(
        "aba",
        "aa",
        "ad",
        "vcd",
        "aba"
    )
    val n = mutableListOf<Int>(-1, 150, 190, 170, -1, -1, 160, 180)
    println(nah.allLongestStrings(s))
    println(nah.commonCharacterCount("abcsdf", "xcvadg"))
    println(nah.isLucky(34536))
    println(nah.sortByHeight(n))
    println(nah.reverseInParentheses("foo(bar(baz))blim"))
}