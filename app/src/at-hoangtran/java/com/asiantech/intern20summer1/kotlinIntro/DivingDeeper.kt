package com.asiantech.intern20summer1.kotlinIntro

class DivingDeeper {
    fun extractEachKth(inputArray: MutableList<Int>, k: Int): MutableList<Int> {
//        Given array of integers, remove each kth element from it.
        var a = mutableListOf<Int>()
        for (i in inputArray.indices) {
            if ((i + 1) % k != 0) {
                a.add(inputArray[i])
            }
        }
        return a
    }

    fun firstDigit(inputString: String): Char {
//        Find the leftmost digit that occurs in a given string.
        var c = ' '
        for (i in inputString.indices) {
            if (inputString[i].isDigit()) {
                c = inputString[i]
                break
            }
        }
        return c
    }

    fun differentSymbolsNaive(s: String): Int {
//        Given a string, find the number of different characters in it.
        var tmp = mutableListOf<Char>()
        var count = 0
        for (i in s.indices) {
            if (!tmp.contains(s[i])) {
                tmp.add(s[i])
                count++
            }
        }
        return count
    }

    fun arrayMaxConsecutiveSum(inputArray: MutableList<Int>, k: Int): Int {
//        Given array of integers, find the maximal possible sum of some of its k consecutive elements.
        var max = 0
        for (i in 0..inputArray.size - k) {
            var sum: Int = 0
            for (j in 0..k - 1) {
                sum += inputArray[i + j]
            }
            if (max < sum) {
                max = sum
            }
        }
        return max
    }

}

fun main(args: Array<String>) {
    var nah = DivingDeeper()
    val intArray = mutableListOf(2, 4, 5, 6, 8, 23, 6, 7, 875, 2)
    println(nah.extractEachKth((intArray), 6))
    println(nah.firstDigit("fgdg1"))
    println(nah.differentSymbolsNaive("dfgdfhcb"))
    println(nah.arrayMaxConsecutiveSum(intArray, 2))
}
