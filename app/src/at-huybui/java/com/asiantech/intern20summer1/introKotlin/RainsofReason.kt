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


}