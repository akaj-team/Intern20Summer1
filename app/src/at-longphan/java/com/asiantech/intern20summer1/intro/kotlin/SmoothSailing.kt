package com.asiantech.intern20summer1.intro.kotlin

fun main() {
    //RUN main() with Coverage
    var obj: SmoothSailing = SmoothSailing()
    println("Ex 9: " + obj.allLongestStrings(mutableListOf("aba", "aa", "ad", "vcd", "aba")))

    println("Ex 10: " + obj.commonCharacterCount("aabcc", "adcaa"))

    println("Ex 11: " + obj.isLucky(1395))

    println("Ex 12: " + obj.sortByHeight(mutableListOf(-1, 150, 190, 170, -1, -1, 160, 180)))

    println("Ex 13: " + obj.reverseInParentheses("foo(bar)baz(blim)"))
}

class SmoothSailing {
    fun allLongestStrings(inputArray: MutableList<String>): Array<String> {
        /**
         * Given an array of strings, return another array containing all of its longest strings.
         */
        var array: String = ""
        var maxLength: Int = 0
        for (str in inputArray) {
            if (str.length > maxLength) {
                maxLength = str.length
                array = str + ","
            } else if (str.length == maxLength) {
                array += str + ","
            }
        }
        return array.substring(0, array.length - 1).split(",").toTypedArray()
    }

    fun commonCharacterCount(s1: String, s2: String): Int {
        /**
         * Given two strings, find the number of common characters between them.
         */
        var s2copy = s2
        var count: Int = 0
        var s1Length = s1.length
        for (i in 0 until s1Length) {
            var ch = s1[i]
            if (s2copy.indexOf(ch) != -1) {
                count++
                s2copy = charRemoveAt(s2copy, s2copy.indexOf(ch))
            }
        }
        return count
    }

    fun charRemoveAt(str: String, charIndex: Int): String {
        /**
         * Remove a char at index 'charIndex' in string 'String'
         */
        return str.substring(0, charIndex) + str.substring(charIndex + 1)
    }

    fun isLucky(n: Int): Boolean {
        /**
         * Ticket numbers usually consist of an even number of digits.
         * A ticket number is considered lucky if the sum of the first half
         * of the digits is equal to the sum of the second half.

        Given a ticket number n, determine if it's lucky or not.
         */
        var str: String = n.toString()
        var result: Int = 0
        var i: Int = 0
        while (i < str.length / 2) {
            result = result + str[i].toInt()
            i++
        }
        while (i < str.length) {
            result = result - str[i].toInt()
            i++
        }
        return result == 0
    }

    fun sortByHeight(a: MutableList<Int>): MutableList<Int> {
        /**
         * Some people are standing in a row in a park. There are trees between them which cannot
         * be moved. Your task is to rearrange the people by their heights in a non-descending
         * order without moving the trees. People can be very tall!
         */
        var b = mutableListOf<Int>()
        for (i in a) {
            if (i != -1) {
                b.add(i)
            }
        }
        b.sort()
        var listIndex: Int = 0
        for ((index, item) in a.withIndex()) {
            if (a[index] != -1) {
                a[index] = b.get(listIndex)
                listIndex++
            }
        }
        return a
    }

    fun reverseInParentheses(a: String): String {
        /**
         * Write a function that reverses characters in (possibly nested)
         * parentheses in the input string.

        Input strings will always be well-formed with matching ()s.
         */
        var aClone = a
        var firstIndex: Int = 0
        var lastIndex: Int = 0
        while (aClone.contains("(")) {
            firstIndex = aClone.lastIndexOf("(")
            lastIndex = aClone.indexOf(")", firstIndex)

            var reverseStr = aClone.substring(firstIndex + 1, lastIndex).reversed()
            aClone = aClone.substring(0, firstIndex) + reverseStr + aClone.substring(lastIndex + 1)
        }
        return aClone
    }
}
