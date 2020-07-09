package com.asiantech.intern20summer1.kotlin

object DivingDeeper {
    fun extractEachKth(inputArray: Array<Int>, k: Int): Array<Int> {
        var count: Int = 1
        var invalidCases: MutableList<Int> = mutableListOf()
        var newArray: MutableList<Int> = mutableListOf()

        for (element in inputArray) {
            newArray.add(element)
        }

        for (element in inputArray) {
            if (count == k) {
                invalidCases.add(element)
                count = 0
            }
            count++
        }

        for (i in 0 until invalidCases.size) {
            if (newArray.contains(invalidCases.get(i))) {
                newArray.remove(invalidCases.get(i))
            }
        }

        return newArray.toTypedArray()
    }

    fun firstDigit(inputString: String): Char {
        for (i in inputString.indices) {
            if (inputString[i].isDigit()) {
                return inputString[i]
            }
        }

        return '0'
    }

    fun differentSymbolsNaive(s: String): Int {
        var listDifferentChar: MutableList<Char> = mutableListOf()

        for (element in s.indices) {
            if (!listDifferentChar.contains(s[element])) {
                listDifferentChar.add(s[element])
            }
        }

        return listDifferentChar.size
    }

    fun arrayMaxConsecutiveSum(inputArray: Array<Int>, k: Int): Int {
        var sum: Int
        var count: Int
        var maxSum: Int = 0
        for (i in inputArray.indices) {
            sum = 0
            count = k
            try {
                while (count > 0) {
                    sum += inputArray[i + count - 1]
                    count--
                }

            } catch (ex4: IndexOutOfBoundsException) {
                println("IndexOutOfBoundsException")
            }
            if (sum > maxSum) {
                maxSum = sum
            }
        }

        return maxSum
    }
}
