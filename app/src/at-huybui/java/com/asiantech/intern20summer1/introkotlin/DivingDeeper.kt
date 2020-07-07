package com.asiantech.intern20summer1.introkotlin

class DivingDeeper {

    /** 34 **
     * Given array of integers, remove each kth element from it.
     */
    fun extractEachKth(inputArray: MutableList<Int>, k: Int): MutableList<Int> {
        return inputArray.filterIndexed { index, i ->
            (index + 1) % k != 0
        }.toMutableList()
    }

    /** 35 **
     * Find the leftmost digit that occurs in a given string.
     */
    fun firstDigit(inputString: String): Char {
        inputString.forEach {
            if (it.isDigit()) return it
        }
        return '0'
    }

    /** 36 **
     *Given a string, find the number of different characters in it.
     */
    fun differentSymbolsNaive(s: String): Int {
        return ('a'..'z').map { it1 ->
            if (s.count { it2 ->
                    it1 == it2
                } > 0) 1 else 0
        }.sum()
    }

    /** 37 **
     *Given array of integers, find the maximal possible sum of some of its k consecutive elements.git
     */
    fun arrayMaxConsecutiveSum(inputArray: MutableList<Int>, k: Int): Int {
        var mSumOut = 0;
        inputArray.forEachIndexed { index1, i1 ->
            if (index1 <= inputArray.size - k) {
                val sumnew = inputArray.filterIndexed { index2, i2 -> (index2 < index1 + k) && (index2 >= index1) }.sum()
                if (sumnew > mSumOut) {
                    mSumOut = sumnew
                }
            }
        }
        return mSumOut
    }

}
