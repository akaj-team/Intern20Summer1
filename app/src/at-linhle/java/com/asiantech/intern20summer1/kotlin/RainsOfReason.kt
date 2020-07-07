package com.asiantech.intern20summer1.kotlin

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
            if (temp % 10 % 2 != 0) {
                return false
            }
            temp /= 10
        }
        return true
    }

    fun variableName(name: String): Boolean {
        for (i in name.indices) {
            if (!(name[i] in 'a'..'z' ||
                        name[i] in 'A'..'Z' ||
                        name[i] in '0'..'9' ||
                        name[i] == '_')
            ) {
                return false
            }
        }
        if (name[0] in '0'..'9') {
            return false
        }
        return true
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
