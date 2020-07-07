package com.asiantech.intern20summer1.kotlinIntro

class RainOfReason {
    fun arrayReplace(
        inputArray: MutableList<Int>,
        elemToReplace: Int,
        substitutionElem: Int
    ): MutableList<Int> {
//        Given an array of integers, replace all the occurrences of elemToReplace with substitutionElem.
        for (i in 0..inputArray.size - 1) {
            if (inputArray[i] == elemToReplace) {
                inputArray[i] = substitutionElem
            }
        }
        return inputArray
    }

    fun evenDigitsOnly(n: Int): Boolean {
//        Check if all digits of the given integer are even.
        val s: String = n.toString()
        for (i in 0..s.length - 1) {
            if (s[i].toInt() % 2 != 0) {
                return false
            }
        }
        return true
    }

    fun variableName(name: String): Boolean {
//        Correct variable names consist only of English letters, digits and underscores and
//        they can't start with a digit.
//
//        Check if the given string is a correct variable name.
        return name.matches("[A-Za-z_]\\w*".toRegex())
    }

    fun alphabeticShift(inputString: String): String {
//        Given a string, your task is to replace each of its characters by the next one
//        in the English alphabet; i.e. replace a with b, replace b with c, etc (z would be replaced by a).
        var s = ""
        for (i in 0..inputString.length - 1) {
            if (inputString[i] == 'z') {
                s += 'a'
            } else {
                var n = inputString[i].toChar()
                n++
                s += n
            }
        }
        return s
    }

    fun chessBoardCellColor(cell1: String, cell2: String): Boolean {
//        Given two cells on the standard chess board, determine whether they have the same color or not.
        var diff1 = Math.abs(cell1[0] - cell2[0])
        var diff2 = Math.abs(cell1[1] - cell2[1])
        if (diff1 % 2 == 1 && diff2 % 2 == 1) {
            return true
        }
        if (diff1 % 2 == 0 && diff2 % 2 == 0) {
            return true
        }
        return false
    }


}

fun main(args: Array<String>) {
    val nah = RainOfReason()
    val elemToReplace = 3
    val substitutionElem = 2
    val intArray = mutableListOf(2, 4, 6, 7, 9, 0)
    println(nah.arrayReplace(intArray, elemToReplace, substitutionElem))
    println(nah.evenDigitsOnly(446556765))
    println(nah.variableName("dfgdgjs56547!"))
    println(nah.alphabeticShift("ghjzf"))
    println(nah.chessBoardCellColor("A1", "B3"))
}
