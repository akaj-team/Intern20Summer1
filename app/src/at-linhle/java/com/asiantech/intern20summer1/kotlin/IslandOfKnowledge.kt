package com.asiantech.intern20summer1.kotlin

import kotlin.math.abs

private const val FOUR = 4
private const val NUMBER = 255

class IslandOfKnowledge {
    fun areEquallyStrong(
        yourLeft: Int,
        yourRight: Int,
        friendsLeft: Int,
        friendsRight: Int
    ): Boolean {
        var flag = false
        if (yourLeft == friendsLeft && yourRight == friendsRight) {
            flag = true
        } else if (yourLeft != friendsLeft && yourRight != friendsRight) {
            if (yourLeft == friendsRight && yourRight == friendsLeft) {
                flag = true
            }
        }
        return flag
    }

    fun arrayMaximalAdjacentDifference(inputArray: MutableList<Int>): Int {
        var max = 0
        for (i in 0 until inputArray.size - 1) {
            max = maxOf(max, abs(inputArray[i] - inputArray[i + 1]))
        }
        return max
    }

    fun isIPv4Address(inputString: String): Boolean {
        val tokens = inputString.split(".")
        return tokens.size == 4 && tokens.all { it.toIntOrNull() in 0..255 }
    }

    fun avoidObstacles(inputArray: MutableList<Int>): Int {
        var i = 1
        while (i != 0) {
            var count = 0
            for (j in 0 until inputArray.size) {
                if (inputArray[j] % i == 0) {
                    break
                }
                count++
                if (count >= inputArray.size) {
                    return i
                }
            }
            i++
        }
        return 0
    }

    fun boxBlur(image: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
        val result = mutableListOf<MutableList<Int>>()
        for (i in 0 until image.size - 2) {
            val row = mutableListOf<Int>()
            for (j in 0 until image[0].size - 2) {
                row.add(
                    (image[i][j] + image[i][j + 1] + image[i][j + 2]
                            + image[i + 1][j] + image[i + 1][j + 1] + image[i + 1][j + 2]
                            + image[i + 2][j] + image[i + 2][j + 1] + image[i + 2][j + 2]) / 9
                )
            }
            result.add(row)
        }
        return result
    }

    fun minesweeper(matrix: MutableList<MutableList<Boolean>>): MutableList<MutableList<Int>> {
        val col = matrix.size
        val row = matrix[0].size
        val mine = mutableListOf<MutableList<Int>>()
        for (i in 0 until col) {
            val temp = mutableListOf<Int>()
            for (j in 0 until row) {
                var count = 0
                if (i > 0 && j > 0 && matrix[i - 1][j - 1]) {
                    count++
                }
                if (i > 0 && matrix[i - 1][j]) {
                    count++
                }
                if (i > 0 && j + 1 < row && matrix[i - 1][j + 1]) {
                    count++
                }
                if (j > 0 && matrix[i][j - 1]) {
                    count++
                }
                if (j + 1 < row && matrix[i][j + 1]) {
                    count++
                }
                if (i + 1 < col && j > 0 && matrix[i + 1][j - 1]) {
                    count++
                }
                if (i + 1 < col && matrix[i + 1][j]) {
                    count++
                }
                if (i + 1 < col && j + 1 < row && matrix[i + 1][j + 1]) {
                    count++
                }
                temp.add(count)
            }
            mine.add(temp)
        }
        return mine
    }
}
