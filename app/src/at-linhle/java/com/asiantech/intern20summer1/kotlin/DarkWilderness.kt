package com.asiantech.intern20summer1.kotlin

import kotlin.math.abs

class DarkWilderness {
    fun growingPlant(upSpeed: Int, downSpeed: Int, desiredHeight: Int): Int {
        return when {
            (upSpeed > desiredHeight) -> 1
            (desiredHeight - upSpeed) < (upSpeed - downSpeed)
            -> (desiredHeight - upSpeed) / (upSpeed - downSpeed) + 2
            else -> (desiredHeight - upSpeed) / (upSpeed - downSpeed) + 1
        }
    }

    fun knapsackLight(value1: Int, weight1: Int, value2: Int, weight2: Int, maxW: Int): Int {
        if (weight1 > maxW && weight2 > maxW) return 0
        if (weight1 + weight2 <= maxW) return value1 + value2
        if (weight1 > maxW) return value2
        if (weight2 > maxW) return value1
        return maxOf(value1, value2)
    }

    fun longestDigitsPrefix(inputString: String): String {
        var result = ""
        for (i in inputString.indices) {
            if (inputString[i] in '0'..'9') {
                result += inputString[i]
            } else break
        }
        return result
    }

    private fun digitDegree(n: Int): Int {
        var temp = n
        if (temp / 10 == 0)
            return 0
        var num = 0
        while (temp != 0) {
            num += temp % 10
            temp /= 10
        }
        return 1 + digitDegree(num)
    }

    fun bishopAndPawn(bishop: String, pawn: String): Boolean {
        return (abs(bishop[0] - pawn[0]) == abs(bishop[1] - pawn[1]))
    }
}