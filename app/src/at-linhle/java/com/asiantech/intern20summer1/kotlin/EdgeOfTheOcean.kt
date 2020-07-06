package com.asiantech.intern20summer1.kotlin

import kotlin.math.abs

class EdgeOfTheOcean {
    /**
     * Given an array of integers, find the pair of adjacent elements that has the largest product
     * and return that product.
     */
    fun adjacentElementsProduct(inputArray: MutableList<Int>): Int {
        var max = inputArray[0] * inputArray[1]
        for (i in 1 until inputArray.size - 1) {
            max = maxOf(max, inputArray[i] * inputArray[i + 1])
        }
        return max
    }

    /**
     * Below we will define an n-interesting polygon. Your task is to find the area of a polygon
     * for a given n.
     * A 1-interesting polygon is just a square with a side of length 1. An n-interesting polygon
     * is obtained by taking the n - 1-interesting polygon and appending 1-interesting polygons to
     * its rim, side by side.
     */
    private fun shapeArea(n: Int): Int {
        if (n == 1) {
            return 1
        }
        return shapeArea(n - 1) + (4 * (n - 1))
    }

    /**
     * Ratiorg got statues of different sizes as a present from CodeMaster for his birthday,
     * each statue having an non-negative integer size. Since he likes to make things perfect,
     * he wants to arrange them from smallest to largest so that each statue will be bigger than
     * the previous one exactly by 1. He may need some additional statues to be able to
     * accomplish that. Help him figure out the minimum number of additional statues needed.
     */
    fun makeArrayConsecutive2(statues: MutableList<Int>): Int {
        var min = statues[0]
        var max = statues[0]
        for (i in 1 until statues.size) {
            min = minOf(min, statues[i])
            max = maxOf(max, statues[i])
        }
        return if (min == 0)
            abs((max - min) - statues.size + 1)
        else abs((max - min + 1) - statues.size)
    }

    /**
     * Given a sequence of integers as an array, determine whether it is possible to obtain a strictly
     * increasing sequence by removing no more than one element from the array.
     */
    fun almostIncreasingSequence(sequence: MutableList<Int>): Boolean {
        var numErr = 0
        for (i in 0 until sequence.size - 1) {
            if (sequence[i] - sequence[i + 1] >= 0) {
                numErr += 1
                if (i - 1 >= 0 && i + 2 <= sequence.size - 1
                    && sequence[i] - sequence[i + 2] >= 0
                    && sequence[i - 1] - sequence[i + 1] >= 0
                ) return false
            }
        }
        return numErr <= 1
    }

    /**
     * Given matrix, a rectangular matrix of integers, where each value represents the cost of the room,
     * your task is to return the total sum of all rooms that are suitable for the CodeBots (ie: add up
     * all the values that don't appear below a 0).
     */
    fun matrixElementsSum(matrix: MutableList<MutableList<Int>>): Int {
        var sum = 0
        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[0].size) {
                if (matrix[i][j] == 0) {
                    if (i < matrix.size - 1) {
                        matrix[i + 1][j] = 0
                    } else {
                        continue
                    }
                } else {
                    sum += matrix[i][j]
                }
            }
        }
        return sum
    }
}
