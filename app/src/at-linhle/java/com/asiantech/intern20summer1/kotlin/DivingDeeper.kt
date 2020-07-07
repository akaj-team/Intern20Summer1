package com.asiantech.intern20summer1.kotlin

class DivingDeeper {
    fun extractEachKth(inputArray: MutableList<Int>, k: Int): MutableList<Int> {
        var j = 0
        val outputArray = Array(inputArray.size - inputArray.size / k) { 1 }
        for (i in 0 until inputArray.size) {
            if ((i + 1) % k != 0) {
                outputArray[j] = inputArray[i]
                j++
            }
        }
        return outputArray.toMutableList()
    }

    fun firstDigit(inputString: String): Char {
        for (i in inputString.indices) {
            if (inputString[i] in '0'..'9') {
                return inputString[i]
            }
        }
        return 'a'
    }

    fun differentSymbolsNaive(s: String): Int {
        val temp = HashSet<Char>()
        for (i in s.indices) {
            temp.add(s[i])
        }
        return temp.size
    }

    fun arrayMaxConsecutiveSum(inputArray: MutableList<Int>, k: Int): Int {
        var max = 0
        var curSum = 0

        for (i in 0 until inputArray.size) {
            if (i - k >= 0)
                curSum -= inputArray[i - k]
            curSum += inputArray[i]
            max = maxOf(max, curSum)
        }
        return max
    }
}
