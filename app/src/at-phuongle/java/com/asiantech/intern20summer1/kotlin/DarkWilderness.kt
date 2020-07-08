package com.asiantech.intern20summer1.kotlin

object DarkWilderness {
    private const val TEN = 10
    private const val SEVEN = 7
    private const val a = 97
    private const val TO_NUMBER = 49

    fun growingPlant(upSpeed: Int, downSpeed: Int, desiredHeight: Int): Int {
        var height: Int = 0
        var day: Int = 0

        while (true) {
            height += upSpeed
            day++
            if (height >= desiredHeight) {
                break
            } else {
                height -= downSpeed
            }
        }

        return day
    }

    fun knapsackLight(value1: Int, weight1: Int, value2: Int, weight2: Int, maxW: Int): Int {
        var maxWeight: Int = maxW
        var result: Int = 0
        var values: Array<Int> = arrayOf(value1, value2)
        var map: MutableMap<Int, Int> = hashMapOf()

        values.sort()
        map[value1] = weight1
        map[value2] = weight2

        for (i in (values.size - 1) downTo 0) {
            if (maxWeight >= map.getValue(values[i])) {
                result += values[i]
                maxWeight -= map.getValue(values[i])
            }
        }

        return result
    }

    fun longestDigitsPrefix(inputString: String): String {
        var result: String = ""
        for (i in inputString.indices) {
            if (inputString[i].isDigit()) {
                result += inputString[i]
            } else {
                break
            }
        }

        return result
    }

    fun sumOfDigits(n: Int): Int {
        var number: Int = n
        var sum: Int = 0

        while (number > 0) {
            sum += (number % TEN)
            number /= TEN
        }

        return sum
    }

    fun digitDegree(n: Int): Int {
        var number: Int = n
        var count: Int = 0

        while (number >= TEN) {
            count++
            number = sumOfDigits(number)
        }

        return count
    }

    fun bishopAndPawn(bishop: String, pawn: String): Boolean {
        var check: Boolean = false
        var i1Bishop: Int = SEVEN - (bishop[0].toInt() - a)
        var i2Bishop: Int = bishop[1].toInt() - TO_NUMBER
        var i1Pawn: Int = SEVEN - (pawn[0].toInt() - a)
        var i2Pawn: Int = pawn[1].toInt() - TO_NUMBER

        if (i1Pawn > i1Bishop && i2Pawn > i2Bishop) {
            if ((i1Pawn - i1Bishop) == (i2Pawn - i2Bishop)) {
                check = true
            }
        } else if (i1Pawn < i1Bishop && i2Pawn < i2Bishop) {
            if ((i1Bishop - i1Pawn) == (i2Bishop - i2Pawn)) {
                check = true
            }
        } else if (i1Pawn > i1Bishop && i2Pawn < i2Bishop) {
            if ((i1Pawn - i1Bishop) == (i2Bishop - i2Pawn)) {
                check = true
            }
        } else if (i1Pawn < i1Bishop && i2Pawn > i2Bishop) {
            if ((i1Bishop - i1Pawn) == (i2Pawn - i2Bishop)) {
                check = true
            }
        }

        return check
    }
}
