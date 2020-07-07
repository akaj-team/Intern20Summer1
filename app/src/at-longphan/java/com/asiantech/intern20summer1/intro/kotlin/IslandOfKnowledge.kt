package com.asiantech.intern20summer1.intro.kotlin

import kotlin.math.abs

object IslandOfKnowledge {
    @JvmStatic
    fun main(args: Array<String>) {
        //RUN main() with Coverage
        println("Ex 19: " + areEquallyStrong(10, 15, 15, 10))

        println("Ex 20: " + arrayMaximalAdjacentDifference(mutableListOf(2, 4, 1, 0)))

        println("Ex 21: " + isIPv4Address("172.316.254.1"))

        println("Ex 22: " + avoidObstacles(mutableListOf(5, 3, 6, 7, 9)))

        println(
            "Ex 23: " + boxBlur(
                mutableListOf(
                    mutableListOf(1, 1, 1),
                    mutableListOf(1, 7, 1),
                    mutableListOf(1, 1, 1)
                )
            )
        )

        println(
            "Ex 24: "
        )
        println(minesweeper(
            mutableListOf(
                mutableListOf(true, false, false),
                mutableListOf(false, true, false),
                mutableListOf(false, false, false)
            )
        ).forEach { e -> println("$e ") }
        )
    }

    private const val VALID_SIZE: Int = 4
    private const val MAX_ITEM: Int = 255

    private fun areEquallyStrong(
        yourLeft: Int,
        yourRight: Int,
        friendsLeft: Int,
        friendsRight: Int
    ): Boolean {
        /**
         * Call two arms equally strong if the heaviest weights they each are able to lift are equal.

        Call two people equally strong if their strongest arms are equally strong (the strongest
        arm can be both the right and the left), and so are their weakest arms.

        Given your and your friend's arms' lifting capabilities find out if you two are equally strong.
         */
        return (yourLeft == friendsLeft && yourRight == friendsRight)
                || yourLeft == friendsRight && yourRight == friendsLeft
    }

    private fun arrayMaximalAdjacentDifference(a: MutableList<Int>): Int {
        /**
         * Given an array of integers, find the maximal absolute difference between any
         * two of its adjacent elements.
         */
        var max = 0
        for (i in 0..a.size - 2) {
            if (abs(a[i] - a[i + 1]) > max) {
                max = abs(a[i] - a[i + 1])
            }
        }
        return max
    }

    private fun isIPv4Address(a: String): Boolean {
        /**
         * An IP address is a numerical label assigned to each device
         * (e.g., computer, printer) participating in a computer network that uses
         * the Internet Protocol for communication. There are two versions of the Internet protocol,
         * and thus two versions of addresses. One of them is the IPv4 address.

        Given a string, find out if it satisfies the IPv4 address naming rules.
         */
        val b = a.split("[.]".toRegex()).toTypedArray()
        var result = true
        if (b.size != VALID_SIZE) {
            result = false
        } else {
            try {
                for (item in b) {
                    if (item.matches("[0][1-9]".toRegex()) || item.matches("[0][0]".toRegex())) {
                        result = false
                        break
                    }
                    if (item.toInt() < 0 || item.toInt() > MAX_ITEM) {
                        result = false
                        break
                    }
                }
            } catch (e: NumberFormatException) {
                return false
            }
        }
        return result
    }

    private fun avoidObstacles(a: MutableList<Int>): Int {
        /**
         * You are given an array of integers representing coordinates of obstacles situated on a straight line.

        Assume that you are jumping from the point with coordinate 0 to the right.
        You are allowed only to make jumps of the same length represented by some integer.

        Find the minimal length of the jump enough to avoid all the obstacles.
         */
        return (1..Int.MAX_VALUE).first { jump -> a.all { it % jump != 0 } }
    }

    private fun boxBlur(image: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
        /**
         *Last night you partied a little too hard. Now there's a black and white photo of you that's
         *  about to go viral! You can't let this ruin your reputation, so you want to apply
         *  the box blur algorithm to the photo to hide its content.

        The pixels in the input image are represented as integers. The algorithm distorts
        the input image in the following way: Every pixel x in the output image has a value equal
        to the average value of the pixel values from the 3 × 3 square that has its center at x,
        including x itself. All the pixels on the border of x are then removed.

        Return the blurred image as an integer, with the fractions rounded down.
         */
        var xMax = image[0].size - 3
        var yMax = image.size - 3
        var sum = 0
        var result: MutableList<MutableList<Int>> = mutableListOf()
        for (i in 0..yMax) {
            var x: MutableList<Int> = mutableListOf()
            for (j in 0..xMax) {
                sum = image[i].subList(j, j + 3).sum() +
                        image[i + 1].subList(j, j + 3).sum() +
                        image[i + 2].subList(j, j + 3).sum()
                x.add(sum / 9)
            }
            result.add(x)
        }
        return result
    }

    private fun minesweeper(matrix: MutableList<MutableList<Boolean>>): MutableList<MutableList<Int>> {
        /**
         * In the popular Minesweeper game you have a board with some mines and those cells
         * that don't contain a mine have a number in it that indicates the total number of mines
         * in the neighboring cells. Starting off with some arrangement of mines
         * we want to create a Minesweeper game setup.
         */
        val row = matrix.size
        val col: Int = matrix[0].size
        val x =
            MutableList(row) { MutableList(col) { 0 } }
        for (i in 0 until row) {
            for (j in 0 until col) {
                for (i2 in i - 1..i + 1) {
                    for (j2 in j - 1..j + 1) {
                        if (i2 < 0 || j2 < 0 || i2 == i && j2 == j || i2 >= row || j2 >= col) {
                            continue
                        } else {
                            x[i][j] = if (matrix[i2][j2]) x[i][j] + 1 else x[i][j]
                        }
                    }
                }
            }
        }
        return x
    }
}
