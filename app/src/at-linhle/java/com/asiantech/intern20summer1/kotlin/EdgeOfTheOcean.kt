package com.asiantech.intern20summer1.kotlin

import kotlin.math.abs

private const val FOUR = 4

class EdgeOfTheOcean {
    fun adjacentElementsProduct(inputArray: MutableList<Int>): Int {
        var max = inputArray[0] * inputArray[1]
        for (i in 1 until inputArray.size - 1) {
            max = maxOf(max, inputArray[i] * inputArray[i + 1])
        }
        return max
    }

    private fun shapeArea(n: Int): Int {
        if (n == 1) {
            return 1
        }
        return shapeArea(n - 1) + (FOUR * (n - 1))
    }

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
