package com.asiantech.intern20summer1.introKotlin

class RainsofReason {


    /** 25 **
     *Given an array of integers, replace all the occurrences of elemToReplace with substitutionElem.
     */
    fun arrayReplace(
        inputArray: MutableList<Int>,
        elemToReplace: Int,
        substitutionElem: Int
    ): MutableList<Int> {
        return inputArray.map { i -> if (i == elemToReplace) substitutionElem else i }
            .toMutableList()
    }

    /** 26 **
     * Check if all digits of the given integer are even.
     */
    fun evenDigitsOnly(n: Int): Boolean {
        n.toString().forEach { it ->
            if (it.toInt() % 2 == 1) {
                return false
            }
        }
        return true
    }

    /** 27 **
     * Correct variable names consist only of English letters,
     * digits and underscores and they can't start with a digit.
     * Check if the given string is a correct variable name.
     */
    fun variableName(name: String): Boolean {
        val regex = """[a-z|_][\w|_]*""".toRegex() // regex của name
        return regex.matches(name)
    }

    /** 28 **
     *Given a string, your task is to replace each of its characters by the next one in the English alphabet;
     * i.e. replace a with b, replace b with c, etc (z would be replaced by a).
     */
    fun alphabeticShift(inputString: String): String {
        var mOutputString = ""
        inputString.forEach {
            when {
                it == 'z' -> mOutputString += "a"
                else -> mOutputString += it + 1
            }
        }
        return mOutputString
    }

    /** 29 **
     * Given two cells on the standard chess board,
     * determine whether they have the same color or not.
     */
    fun chessBoardCellColor(cell1: String, cell2: String): Boolean {
        return cell1.map { char -> char.toByte() }.sum() % 2 ==
                cell1.map { char -> char.toByte() }.sum() % 2
    }
}
