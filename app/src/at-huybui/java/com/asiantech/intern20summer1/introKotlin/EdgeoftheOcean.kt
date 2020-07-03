package com.asiantech.intern20summer1.introKotlin

class EdgeoftheOcean {

    /** 4 **
     * Given an array of integers, find the pair of adjacent elements
     * that has the largest product and return that product.
     */
    fun adjacentElementsProduct(inputArray: MutableList<Int>): Int {
        var mMaxValue = inputArray[0] * inputArray[1]
        for (i in 0 until inputArray.size - 1) {
            val newValue = inputArray[i] * inputArray[i + 1];
            if (mMaxValue < newValue) {
                mMaxValue = newValue
            }
        }
        return mMaxValue
    }

    /** 5 **
     *Below we will define an n-interesting polygon. Your task is to find the area of a polygon for a given n.
     */
    fun shapeArea(n: Int): Int {
        return n * n + (n - 1) * (n - 1)
    }

    /** 6 **
     *Ratiorg got statues of different sizes as a present from CodeMaster for his birthday,
     * each statue having an non-negative integer size. Since he likes to make things perfect,
     * he wants to arrange them from smallest to largest so that each statue will be bigger than
     * the previous one exactly by 1. He may need some additional statues to be able to accomplish that.
     * Help him figure out the minimum number of additional statues needed.
     */
    fun makeArrayConsecutive2(statues: MutableList<Int>): Int {
        return statues.max()!! - statues.min()!! - (statues.size - 1) // chấp nhận có thể dính null
    }

    /** 7 **
     *Given a sequence of integers as an array, determine whether it is possible to obtaina strictly
     * increasing sequence by removing no more than one element from the array.
     * Note: sequence a0, a1, ..., an is considered to be a strictly increasing if a0 < a1 < ... <
     * an. Sequence containing only one element is also considered to be strictly increasing.
     */

    fun almostIncreasingSequence(sequence: MutableList<Int>): Boolean {
        var flat = 0
        for (i in 0 until sequence.size - 1) {
            if (sequence[i] >= sequence[i + 1]) {
                flat++
                if (i >= 1 && i + 2 <= sequence.size - 1 && sequence[i] >= sequence[i + 2]) {
                    if (sequence[i - 1] >= sequence[i + 1]) {
                        return false
                    }
                }
            }
        }
        return flat <= 1
    }

    /** 8 ** đề bài tại link
     *https://app.codesignal.com/arcade/intro/level-2/xskq4ZxLyqQMCLshr
     */

    fun matrixElementsSum(matrix: MutableList<MutableList<Int>>): Int {
        var mSum = 0  // tạo biến tổng lưu số phòng trả lại
        for (i in 0 until matrix[0].size) { // quét theo chiều ngang
            for (j in matrix.indices) {    // quét theo chiều dọc
                mSum += if (matrix[j][i] != 0) { // nếu ko có ma thì tiếp tục cộng giá tri
                    matrix[j][i]
                } else {  // có ma thì break luôn
                    break
                }
            }
        }
        return mSum
    }
}