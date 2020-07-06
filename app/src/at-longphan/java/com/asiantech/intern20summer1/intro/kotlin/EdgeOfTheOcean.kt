package com.asiantech.intern20summer1.intro.kotlin

object EdgeOfTheOcean {
    @JvmStatic
    fun main(args: Array<String>) {
        //RUN main() with Coverage
        println("Ex 4: " + adjacentElementsProduct(intArrayOf(3, 6, -2, -5, 7, 3)))

        println("Ex 5: " + shapeArea(5))

        println("Ex 6: " + makeArrayConsecutive2(mutableListOf(6, 2, 3, 8)))

        println("Ex 7: " + almostIncreasingSequence(mutableListOf(1, 3, 2, 1)))

        println(
            "Ex 8: " + matrixElementsSum(
                mutableListOf(
                    mutableListOf(0, 1, 1, 2),
                    mutableListOf(0, 5, 0, 0),
                    mutableListOf(2, 0, 3, 3)
                )
            )
        )
    }

    private fun adjacentElementsProduct(inputArray: IntArray): Int {
        /**
         * Given an array of integers, find the pair of adjacent elements
         * that has the largest product and return that product.
         */
        var max = inputArray[0] * inputArray[1]
        for (i in 1 until inputArray.size - 1) {
            if (inputArray[i] * inputArray[i + 1] > max) {
                max = inputArray[i] * inputArray[i + 1]
            }
        }
        return max
    }

    private fun shapeArea(n: Int): Int {
        /**
         * Below we will define an n-interesting polygon.
         * Your task is to find the area of a polygon for a given n.
         *
         * A 1-interesting polygon is just a square with a side of length 1.
         * An n-interesting polygon is obtained by taking the n - 1-interesting polygon
         * and appending 1-interesting polygons to its rim, side by side.
         * You can see the 1-, 2-, 3- and 4-interesting polygons in the picture below.
         */
        return n * n + (n - 1) * (n - 1)
    }

    private fun makeArrayConsecutive2(statues: MutableList<Int>): Int {
        /**
         * Ratiorg got statues of different sizes as a present from CodeMaster for his birthday,
         * each statue having an non-negative integer size. Since he likes to make things perfect,
         * he wants to arrange them from smallest to largest so that each statue
         * will be bigger than the previous one exactly by 1. He may need some additional statues
         * to be able to accomplish that. Help him figure out the minimum number of additional statues needed.
         */
        statues.sort()
        val result = statues[statues.size - 1] - statues[0] - 1 - (statues.size - 2)
        return result
    }

    private fun almostIncreasingSequence(a: MutableList<Int>): Boolean {
        /**
         * Given a sequence of integers as an array, determine whether it is possible to obtain
         * a strictly increasing sequence by removing no more than one element from the array.

        Note: sequence a0, a1, ..., an is considered to be a strictly increasing
        if a0 < a1 < ... < an. Sequence containing only one element is also considered
        to be strictly increasing.
         */
        if (a.size == 2) return true
        var count: Int = 0
        var i: Int = 0
        while (i < a.size - 1) {
            if (a[i] >= a[i + 1]) {
                count++
                if (count == 2) {
                    return false
                }
                if (i > 0 && i < a.size - 2 ) {
                    if(a[i + 2] <= a[i] && a[i + 1] <= a[i - 1]){
                        return false
                    }
                }
            }
            i++
        }
        return true
    }

    private fun matrixElementsSum(matrix: MutableList<MutableList<Int>>): Int {
        /**
         * After becoming famous, the CodeBots decided to move into a new building together.
         * Each of the rooms has a different cost, and some of them are free,
         * but there's a rumour that all the free rooms are haunted! Since the CodeBots are quite superstitious,
         * they refuse to stay in any of the free rooms, or any of the rooms below any of the free rooms.

        Given matrix, a rectangular matrix of integers, where each value represents the cost of the room,
        your task is to return the total sum of all rooms that are suitable for the CodeBots
        (ie: add up all the values that don't appear below a 0).
         */
        var row: Int = matrix.size
        var col: Int = matrix[0].size
        var sum: Int = 0
        for (i in 0 until col) {
            for (j in 0 until row) {
                if (matrix[j][i] == 0) {
                    break
                }
                sum += matrix[j][i]
            }
        }
        return sum
    }
}

