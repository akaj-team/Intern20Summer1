package com.asiantech.intern20summer1.intro.kotlin

object RainsOfReason {
    @JvmStatic
    fun main(args: Array<String>) {
        //RUN main() with Coverage
        println("Ex 25: " + arrayReplace(mutableListOf(1, 2, 1), 1, 3))

        println("Ex 26: " + evenDigitsOnly(248622))

        println("Ex 27: " + variableName("var_1__Int"))

        println("Ex 28: " + alphabeticShift("crazy"))

        println("Ex 29: " + chessBoardCellColor("A1", "C3"))
    }

    private const val SPECIAL_CHAR: Int = 122

    private fun arrayReplace(
        a: MutableList<Int>,
        elemToReplace: Int,
        substitutionElem: Int
    ): MutableList<Int> {
        /**
         * Given an array of integers, replace all the occurrences of elemToReplace with substitutionElem.
         */
        for (i in a.indices) {
            if (a[i] == elemToReplace) {
                a[i] = substitutionElem
            }
        }
        return a
    }

    private fun evenDigitsOnly(n: Int): Boolean {
        /**
         * Check if all digits of the given integer are even.
         */
        val n2 = n.toString()
        for (element in n2) {
            if (Integer.valueOf(element.toInt()) % 2 != 0) {
                return false
            }
        }
        return true
    }

    private fun variableName(name: String): Boolean {
        /**
         * Correct variable names consist only of English letters,
         * digits and underscores and they can't start with a digit.

        Check if the given string is a correct variable name.
         */
        return name.matches("^[a-zA-Z_]{1}[a-zA-Z0-9_]{0,9}$".toRegex());
    }

    private fun alphabeticShift(a: String): String {
        /**
         * Given a string, your task is to replace each of its characters by the next one
         * in the English alphabet; i.e. replace a with b, replace b with c,
         * etc (z would be replaced by a).
         */
        var aClone = a
        for (i in aClone.indices) {
            aClone = if (aClone[i].toInt() == SPECIAL_CHAR) {
                val replace = 'a'
                aClone.substring(0, i) + replace + aClone.substring(i + 1)
            } else {
                val value = aClone[i].toInt()
                val replace = (value + 1).toChar()
                aClone.substring(0, i) + replace + aClone.substring(i + 1)
            }
        }
        return aClone
    }

    private fun chessBoardCellColor(cell1: String, cell2: String): Boolean {
        /**
         * Given two cells on the standard chess board,
         * determine whether they have the same color or not.
         */
        val alphabet1 = cell1[0].toInt()
        val alphabet2 = cell2[0].toInt()
        return (alphabet1 + cell1[1].toInt()) % 2 == (alphabet2 + cell2[1].toInt()) % 2
    }
}
