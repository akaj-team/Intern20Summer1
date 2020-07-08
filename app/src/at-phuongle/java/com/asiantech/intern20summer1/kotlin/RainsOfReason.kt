package com.asiantech.intern20summer1.kotlin

object RainsOfReason {
    private const val TEN = 10
    private const val CURLY_BRACKETS = 123
    private const val a = 97
    private const val A = 65
    private const val TO_NUMBER = 49

    fun arrayReplace(
        inputArray: Array<Int>,
        elemToReplace: Int,
        substitutionElem: Int
    ): Array<Int> {
        for (i in inputArray.indices) {
            if (inputArray[i] == elemToReplace) {
                inputArray[i] = substitutionElem
            }
        }
        return inputArray
    }

    fun evenDigitsOnly(n: Int): Boolean {
        var number: Int = n
        while (number > 0) {
            var digit: Int = number % TEN
            if (digit % 2 != 0) {
                return false
            }
            number /= TEN
        }
        return true
    }

    fun variableName(name: String): Boolean {
        var regex = "\\w*"
        var firstChar: Char = name[0]

        if (firstChar.isDigit()) {
            return false
        }

        return name.matches(regex.toRegex())
    }

    fun alphabeticShift(inputString: String): String {
        var newString: String = ""
        for (element in inputString) {
            var temp: Char = element
            var n: Int = (temp + 1).toInt()
            if (n == CURLY_BRACKETS) {
                n = a
            }
            temp = n.toChar()
            newString += temp
        }

        return newString
    }

    fun chessBoardCellColor(cell1: String, cell2: String): Boolean {
        var i1Cell1: Int = (cell1[0]).toInt() - A
        var i2Cell1: Int = (cell1[1]).toInt() - TO_NUMBER
        var i1Cell2: Int = (cell2[0]).toInt() - A
        var i2Cell2: Int = (cell2[1]).toInt() - TO_NUMBER
        var check1: Char
        var check2: Char

        if ((i1Cell1 % 2 == 0 && i2Cell1 % 2 == 0) || (i1Cell1 % 2 == 1 && i2Cell1 % 2 == 1)) {
            check1 = 'X'
        } else {
            check1 = 'O'
        }
        if ((i1Cell2 % 2 == 0 && i2Cell2 % 2 == 0) || (i1Cell2 % 2 == 1 && i2Cell2 % 2 == 1)) {
            check2 = 'X'
        } else {
            check2 = 'O'
        }

        if (check1 == check2) {
            return true
        }

        return false
    }
}
