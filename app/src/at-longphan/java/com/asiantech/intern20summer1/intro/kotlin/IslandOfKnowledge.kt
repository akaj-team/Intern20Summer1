package com.asiantech.intern20summer1.intro.kotlin

import kotlin.math.abs
import kotlin.math.floor

fun main() {
    //RUN main() with Coverage
    var obj: IslandOfKnowledge = IslandOfKnowledge()
    println("Ex 19: " + obj.areEquallyStrong(10, 15, 15, 10))

    println("Ex 20: " + obj.arrayMaximalAdjacentDifference(mutableListOf(2, 4, 1, 0)))

    println("Ex 21: " + obj.isIPv4Address("172.316.254.1"))

    println("Ex 22: " + obj.avoidObstacles(mutableListOf(5, 3, 6, 7, 9)))

    println(
        "Ex 23: " + obj.boxBlur(
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
    println(obj.minesweeper(
        mutableListOf(
            mutableListOf(true, false, false),
            mutableListOf(false, true, false),
            mutableListOf(false, false, false)
        )
    ).forEach { e -> println("$e ") }
    )
}

class IslandOfKnowledge {
    fun areEquallyStrong(
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

    fun arrayMaximalAdjacentDifference(a: MutableList<Int>): Int {
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

    fun isIPv4Address(a: String): Boolean {
        /**
         * An IP address is a numerical label assigned to each device
         * (e.g., computer, printer) participating in a computer network that uses
         * the Internet Protocol for communication. There are two versions of the Internet protocol,
         * and thus two versions of addresses. One of them is the IPv4 address.

        Given a string, find out if it satisfies the IPv4 address naming rules.
         */
        val b = a.split("[.]".toRegex()).toTypedArray()
        if (b.size != 4) return false
        try {
            for (item in b) {
                if (item.matches("[0][1-9]".toRegex()) || item.matches("[0][0]".toRegex())) {
                    return false
                }
                if (item.toInt() < 0 || item.toInt() > 255) {
                    return false
                }
            }
        } catch (e: NumberFormatException) {
            return false
        }
        return true
    }

    fun avoidObstacles(a: MutableList<Int>): Int {
        /**
         * You are given an array of integers representing coordinates of obstacles situated on a straight line.

        Assume that you are jumping from the point with coordinate 0 to the right.
        You are allowed only to make jumps of the same length represented by some integer.

        Find the minimal length of the jump enough to avoid all the obstacles.
         */
        a.sort()
        val max = a[a.size - 1]
        var x = 2
        var isExistInArray = false
        while (true) {
            isExistInArray = false
            for (item in a) {
                if (item == x) {
                    isExistInArray = true
                    break
                }
            }
            if (isExistInArray) {
                x++
                continue
            }
            var jump = 1
            while (x * jump <= max) {
                for (item in a) {
                    if (item == x * jump) {
                        isExistInArray = true
                        break
                    }
                }
                if (isExistInArray) {
                    break
                }
                jump++
            }
            return if (isExistInArray) {
                x++
                continue
            } else {
                x
            }
            x++
        }
    }

    fun boxBlur(image: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
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
        val row = image.size
        val col: Int = image[0].size
        val rowX = row - 2
        val colX = col - 2
        val x = MutableList(rowX) { MutableList(colX) { 0 } }
        for (i in 0 until rowX) {
            for (j in 0 until colX) {
                for (i2 in i..i + 2) {
                    for (j2 in j..j + 2) {
                        x[i][j] += image[i2][j2]
                    }
                }
                x[i][j] = floor(x[i][j] / 9.toDouble()).toInt()
            }
        }
        return x
    }

    fun minesweeper(matrix: MutableList<MutableList<Boolean>>): MutableList<MutableList<Int>> {
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
