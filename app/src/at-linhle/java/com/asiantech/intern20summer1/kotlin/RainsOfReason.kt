package com.asiantech.intern20summer1.kotlin

import java.util.regex.Pattern

private const val TEN = 10

class RainsOfReason {
    fun arrayReplace(
        inputArray: MutableList<Int>,
        elemToReplace: Int,
        substitutionElem: Int
    ): MutableList<Int> {
        for (i in 0 until inputArray.size) {
            if (inputArray[i] == elemToReplace) {
                inputArray[i] = substitutionElem
            }
        }
        return inputArray
    }

    fun evenDigitsOnly(n: Int): Boolean {
        var temp = n
        while (temp != 0) {
            if (temp % TEN % 2 != 0) {
                return false
            }
            temp /= TEN
        }
        return true
    }

    fun variableName(name: String): Boolean {
        val p = Pattern.compile("[a-zA-Z_][0-9a-zA-Z_]*")
        val m = p.matcher(name)
        return m.matches()
    }

    fun alphabeticShift(inputString: String): String {
        var temp = ""
        for (i in inputString.indices) {
            temp += if (inputString[i] == 'z') {
                'a'
            } else {
                inputString[i] + 1
            }
        }
        return temp
    }

    fun chessBoardCellColor(cell1: String, cell2: String): Boolean {
        val column1 = cell1[0]
        val column2 = cell2[0]
        val row1 = cell1[1]
        val row2 = cell2[1]
        return (column1.toInt() + row1.toInt()) % 2 == (column2.toInt() + row2.toInt()) % 2
    }
}
