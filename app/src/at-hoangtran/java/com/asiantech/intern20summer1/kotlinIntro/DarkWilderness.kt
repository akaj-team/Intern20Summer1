package com.asiantech.intern20summer1.kotlinIntro

class DarkWilderness {
    fun growingPlant(upSpeed: Int, downSpeed: Int, desiredHeight: Int): Int {
//        Caring for a plant can be hard work, but since you tend to it regularly, you have a plant
//        that grows consistently. Each day, its height increases by a fixed amount represented by the
//        integer upSpeed. But due to lack of sunlight, the plant decreases in height every night,
//        by an amount represented by downSpeed.
//
//        Since you grew the plant from a seed, it started at height 0 initially. Given an integer
//        desiredHeight, your task is to find how many days it'll take for the plant to reach this height.
        var height = 0
        var count = 1
        while (height < desiredHeight) {
            height += upSpeed
            if (height >= desiredHeight) {
                break
            }
            height -= downSpeed
            count++
        }
        return count
    }

    fun knapsackLight(value1: Int, weight1: Int, value2: Int, weight2: Int, maxW: Int): Int {
//        You found two items in a treasure chest! The first item weighs weight1 and is worth value1,
//        and the second item weighs weight2 and is worth value2. What is the total maximum value of
//        the items you can take with you, assuming that your max weight capacity is maxW and you can't
//        come back for the items later?
//
//        Note that there are only two items and you can't bring more than one item of each type,
//        i.e. you can't take two first items or two second items.
        if (weight1 + weight2 <= maxW) {
            return value1 + value2
        } else if (weight2 > maxW && weight1 > maxW) {
            return 0
        } else if (weight1 > maxW) {
            return value2
        } else if (weight2 > maxW) {
            return value1
        } else if (value1 > value2) {
            return value1
        }
        return value2
    }

    fun longestDigitsPrefix(inputString: String): String {
//        Given a string, output its longest prefix which contains only digits.
        if (inputString[0] == ' ') {
            return ""
        }
        var s = ""
        if (inputString[0].isDigit()) {
            for (i in inputString.indices) {
                if (!inputString[i].isDigit()) {
                    break
                }
                s += inputString[i]
            }
        }
        return s
    }

    fun digitDegree(n: Int): Int {
//        Let's define digit degree of some positive integer as the number of times we need
//        to replace this number with the sum of its digits until we get to a one digit number.

//        Given an integer, find its digit degree.
        var count = 0
        var sum = 0
        var n = n.toString()
        while (n.length > 1) {
            sum = n.map { (it - '0') }.sum()
            count++
            n = sum.toString()
        }
        return count
    }

    fun bishopAndPawn(bishop: String, pawn: String): Boolean {
//        Given the positions of a white bishop and a black pawn on the standard chess board,
//        determine whether the bishop can capture the pawn in one move.
//
//        The bishop has no restrictions in distance for each move, but is limited to diagonal movement.
//        Check out the example below to see how it can move:
        var diff1 = Math.abs(bishop[0] - pawn[0])
        var diff2 = Math.abs(bishop[1] - pawn[1])
        return (diff1 == diff2)
    }

}

fun main(args: Array<String>) {
    var nah = DarkWilderness()
    println(nah.growingPlant(12, 5, 50))
    println(nah.knapsackLight(2, 4, 5, 3, 5))
    println(nah.longestDigitsPrefix("asdsdf456sf"))
    println(nah.digitDegree(345))
    println(nah.bishopAndPawn("a3", "f4"))
}
