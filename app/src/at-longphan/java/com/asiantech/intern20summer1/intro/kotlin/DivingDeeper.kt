package com.asiantech.intern20summer1.intro.kotlin

object DivingDeeper {
    @JvmStatic
    fun main(args: Array<String>) {
        //RUN main() with Coverage
        println("Ex 34: " + extractEachKth(mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 3))

        println("Ex 35: " + firstDigit("var_1__Int"))

        println("Ex 36: " + differentSymbolsNaive("cabca"))

        println("Ex 37: " + arrayMaxConsecutiveSum(mutableListOf(2, 3, 5, 1, 6), 2))
    }

    private fun extractEachKth(inputArray: MutableList<Int>, k: Int): MutableList<Int> {
        /**
         * Find the leftmost digit that occurs in a given string.
         */
        var x = mutableListOf<Int>()
        run {
            var i = 0
            var j = 1
            while (i < inputArray.size) {
                if (j == k) {
                    j = 0
                } else {
                    x.add(inputArray[i])
                }
                i++
                j++
            }
        }
        var result = MutableList(x.size) { 0 }
        for (i in 0 until x.size) {
            result[i] = x[i]
        }
        return result
    }

    private fun firstDigit(inputString: String): Char {
        /**
         * Find the leftmost digit that occurs in a given string.
         */
        for (i in inputString.indices) {
            if (inputString[i].toString().matches("[0-9]".toRegex())) return inputString[i]
        }
        return inputString[0]
    }

    private fun differentSymbolsNaive(s: String): Int {
        /**
         * Given a string, find the number of different characters in it.
         */
        val map: HashMap<Char, Int?> = HashMap()
        for (i in s.indices) {
            if (!map.containsKey(s[i])) map[s[i]] = 0
        }
        return map.size
    }

    private fun arrayMaxConsecutiveSum(a: MutableList<Int>, k: Int): Int {
        /**
         * Given array of integers, find the maximal possible sum of some of its k consecutive elements.
         */
        var max = 0
        var previousSum = 0
        for (i in 0..a.size - k) {
            if (i == 0) {
                for (j in i until i + k) {
                    previousSum += a[j]
                }
            } else {
                previousSum = previousSum - a[i - 1] + a[i + k - 1]
            }
            if (previousSum > max) max = previousSum
        }
        return max
    }
}
