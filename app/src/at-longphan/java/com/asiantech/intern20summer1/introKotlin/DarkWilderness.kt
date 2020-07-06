package com.asiantech.intern20summer1.introKotlin

fun main() {
    //RUN main() with Coverage
    var obj: DarkWilderness = DarkWilderness()
    println("Ex 38: " + obj.growingPlant(100, 10, 90))

    println("Ex 39: " + obj.knapsackLight(10, 5, 6, 4, 8))

    println("Ex 40: " + obj.longestDigitsPrefix("123aa1"))

    println("Ex 41: " + obj.digitDegree(5))

    println("Ex 42: " + obj.bishopAndPawn("a1", "c3"))
}

class DarkWilderness {
    /**
     * Caring for a plant can be hard work, but since you tend to it regularly,
     * you have a plant that grows consistently. Each day, its height increases
     * by a fixed amount represented by the integer upSpeed. But due to lack of sunlight,
     * the plant decreases in height every night, by an amount represented by downSpeed.

    Since you grew the plant from a seed, it started at height 0 initially.
    Given an integer desiredHeight, your task is to find how many days it'll take for
    the plant to reach this height.
     */
    fun growingPlant(upSpeed: Int, downSpeed: Int, desiredHeight: Int): Int {
        var height = 0
        var day = 1
        while (true) {
            height += upSpeed
            if (height >= desiredHeight) return day
            height -= downSpeed
            day++
        }
    }

    fun knapsackLight(value1: Int, weight1: Int, value2: Int, weight2: Int, maxW: Int): Int {
        /**
         * You found two items in a treasure chest! The first item weighs weight1 and is worth value1,
         * and the second item weighs weight2 and is worth value2. What is the total maximum value of
         * the items you can take with you, assuming that your max weight capacity is maxW
         * and you can't come back for the items later?

        Note that there are only two items and you can't bring more than one item of each type,
        i.e. you can't take two first items or two second items.
         */
        if (maxW >= weight1 + weight2) return value1 + value2
        if (maxW < weight1 && maxW < weight2) return 0
        if (weight1 > maxW) return value2
        if (weight2 > maxW) return value1
        return Math.max(value1, value2)
    }

    fun longestDigitsPrefix(a: String): String? {
        /**
         * Given a string, output its longest prefix which contains only digits.
         */
        var result = ""
        for (i in a.indices) {
            if (Character.isDigit(a[i])) {
                result += a[i]
            } else {
                break
            }
        }
        return result
    }

    fun digitDegree(n: Int): Int {
        /**
         * Let's define digit degree of some positive integer as the number of times
         * we need to replace this number with the sum of its digits until we get to a one digit number.

        Given an integer, find its digit degree.
         */
        var nClone = n
        var count = 0
        var sum = 0
        while (true) {
            if (nClone.toString().length == 1) return count
            for (element in nClone.toString()) {
                sum += Character.getNumericValue(element)
            }
            nClone = sum
            sum = 0
            count++
        }
    }

    fun bishopAndPawn(bishop: String, pawn: String): Boolean {
        /**
         * Given the positions of a white bishop and a black pawn on the standard chess board,
         * determine whether the bishop can capture the pawn in one move.

        The bishop has no restrictions in distance for each move, but is limited to diagonal movement.
         */
        val bishopValue = (bishop[0] + bishop[1].toInt()).toInt()
        val pawnValue = (pawn[0] + pawn[1].toInt()).toInt()
        if (bishopValue == pawnValue) return true
        return if (pawn[0] > bishop[0]) {
            bishopValue == pawnValue - (pawn[0] - bishop[0]) * 2
        } else {
            bishopValue == pawnValue + (bishop[0] - pawn[0]) * 2
        }
    }
}