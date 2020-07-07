package com.asiantech.intern20summer1.kotlin

import kotlin.math.abs

class IslandOfKnowledge {
    /**
     * Call two arms equally strong if the heaviest weights they each are able to lift are equal.
     * Call two people equally strong if their strongest arms are equally strong (the strongest arm
     * can be both the right and the left), and so are their weakest arms.
     * Given your and your friend's arms' lifting capabilities find out if you two are equally
     * strong.
     */
    fun areEquallyStrong(
        yourLeft: Int,
        yourRight: Int,
        friendsLeft: Int,
        friendsRight: Int
    ): Boolean {
        if (yourLeft == friendsLeft && yourRight == friendsRight) {
            return true
        } else if (yourLeft != friendsLeft && yourRight != friendsRight) {
            if (yourLeft == friendsRight && yourRight == friendsLeft) {
                return true
            }
        }
        return false
    }

    /**
     * Given an array of integers, find the maximal absolute difference between any two of its
     * adjacent elements.
     */
    fun arrayMaximalAdjacentDifference(inputArray: MutableList<Int>): Int {
        var max = 0
        for (i in 0 until inputArray.size - 1) {
            max = maxOf(max, abs(inputArray[i] - inputArray[i + 1]))
        }
        return max
    }

    /**
     * An IP address is a numerical label assigned to each device (e.g., computer, printer)
     * participating in a computer network that uses the Internet Protocol for communication.
     * There are two versions of the Internet protocol, and thus two versions of addresses.
     * One of them is the IPv4 address.
     * Given a string, find out if it satisfies the IPv4 address naming rules.
     */
    fun isIPv4Address(inputString: String): Boolean {
        val pieces = inputString.split(".")
        for (i in pieces.indices) {
            try {
                if (Integer.parseInt(pieces[i]) == 0) {
                    if (pieces[i].length > 1) {
                        return false
                    }
                } else {
                    if (pieces[i][0] == '0') {
                        return false
                    }
                }
                val num = Integer.parseInt(pieces[i])
                if (pieces.size != 4 || num > 255) return false
            } catch (e: NumberFormatException) {
                return false
            }
        }
        return true
    }

    /**
     * You are given an array of integers representing coordinates of obstacles situated on a
     * straight line.
     * Assume that you are jumping from the point with coordinate 0 to the right. You are allowed
     * only to make jumps of the same length represented by some integer.
     * Find the minimal length of the jump enough to avoid all the obstacles.
     */
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

    /**
     * Last night you partied a little too hard. Now there's a black and white photo of you that's
     * about to go viral! You can't let this ruin your reputation, so you want to apply the box
     * blur algorithm to the photo to hide its content.
     * The pixels in the input image are represented as integers. The algorithm distorts the input
     * image in the following way: Every pixel x in the output image has a value equal to the
     * average value of the pixel values from the 3 × 3 square that has its center at x, including
     * x itself. All the pixels on the border of x are then removed.
     * Return the blurred image as an integer, with the fractions rounded down.
     */
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

    /**
     * In the popular Minesweeper game you have a board with some mines and those cells that don't
     * contain a mine have a number in it that indicates the total number of mines in the
     * neighboring cells. Starting off with some arrangement of mines we want to create a
     * Minesweeper game setup.
     */
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
