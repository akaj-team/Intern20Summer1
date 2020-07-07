package com.asiantech.intern20summer1.intro.kotlin

object RainbowOfClarity {
    @JvmStatic
    fun main(args: Array<String>) {
        //RUN main() with Coverage
        println("Ex 48: " + isDigit('0'))

        println("Ex 49: " + lineEncoding("aabbbc"))

        println("Ex 50: " + chessKnight("a1"))

        println("Ex 51: " + deleteDigit(152))
    }

    private const val MIN_OF_RANGE: Int = 48
    private const val MAX_OF_RANGE: Int = 57
    private const val INIT_MOVES: Int = 8

    private fun isDigit(symbol: Char): Boolean {
        /**
         * Determine if the given character is a digit or not.
         */
        return symbol.toInt() in MIN_OF_RANGE..MAX_OF_RANGE
    }

    private fun lineEncoding(s: String): String {
        /**
         * Given a string, return its encoding defined as follows:

        First, the string is divided into the least possible number of disjoint substrings
        consisting of identical characters
        for example, "aabbbc" is divided into ["aa", "bbb", "c"]
        Next, each substring with length greater than one is replaced with a concatenation of
        its length and the repeating character
        for example, substring "bbb" is replaced by "3b"
        Finally, all the new strings are concatenated together in the same order and a new string is returned.
         */
        val input = "$s "
        var result = ""
        var current = s[0]
        var counter = 0
        input.forEach {
            if (it == current) {
                counter++
            } else {
                if (counter > 1) {
                    result += counter
                }
                result += current
                current = it
                counter = 1
            }
        }
        return result
    }

    private fun chessKnight(cell: String): Int {
        /**
         * Given a position of a knight on the standard chessboard, find the number of
         * different moves the knight can perform.

        The knight can move to a square that is two squares horizontally and one square vertically,
        or two squares vertically and one square horizontally away from it.
        The complete move therefore looks like the letter L. Check out the image below
        to see all valid moves for a knight piece that is placed on one of the central squares.
         */
        var moves: Int = INIT_MOVES
        if (cell[0] == 'b' || cell[0] == 'g') moves -= 2
        if (cell[1] == '2' || cell[1] == '7') moves -= 2
        if (cell[0] == 'a' || cell[0] == 'h') moves /= 2
        if (cell[1] == '1' || cell[1] == '8') moves /= 2
        return moves
    }

    private fun deleteDigit(n: Int): Int {
        /**
         * Given some integer, find the maximal number you can obtain by deleting exactly one digit of the given number.
         */
        val stringN = n.toString()
        val length = stringN.length
        val x = IntArray(length)
        for (i in 0 until length) {
            val stringDeleted = stringN.substring(0, i) + stringN.substring(i + 1)
            x[i] = Integer.valueOf(stringDeleted)
        }
        var max = 0
        for (item in x) {
            max = if (item > max) item else max
        }
        return max
    }
}
