package com.asiantech.intern20summer1.introKotlin

fun main() {
    //RUN main() with Coverage
    var obj: RainbowOfClarity = RainbowOfClarity()
    println("Ex 48: " + obj.isDigit('0'))

    println("Ex 49: " + obj.lineEncoding("aabbbc"))

    println("Ex 50: " + obj.chessKnight("a1"))

    println("Ex 51: " + obj.deleteDigit(152))
}

class RainbowOfClarity {
    /**
     * Determine if the given character is a digit or not.
     */
    fun isDigit(symbol: Char): Boolean {
        val symbolRange = 48..57
        return symbol.toInt() in symbolRange
    }

    fun lineEncoding(s: String): String? {
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
        var size = 1
        for (i in 1 until s.length) {
            if (s[i] != s[i - 1]) {
                size++
            }
        }
        val letters = arrayOfNulls<Char>(size)
        letters[0] = s[0]
        var indexLetters = 0
        for (i in 1 until s.length) {
            if (s[i] == letters[indexLetters]) {
            } else {
                indexLetters++
                letters[indexLetters] = s[i]
            }
        }
        val repeats = IntArray(size)
        repeats[0] = 1
        var indexRepeats = 0
        for (i in 1 until s.length) {
            if (s[i] == s[i - 1]) {
                repeats[indexRepeats]++
            } else {
                indexRepeats++
                repeats[indexRepeats]++
            }
        }
        var result = ""
        for (i in letters.indices) {
            result += if (repeats[i] == 1) {
                (letters[i]!!).toString()
            } else {
                repeats[i].toString() + (letters[i]!!).toString()
            }
        }
        return result
    }

    fun chessKnight(cell: String): Int {
        /**
         * Given a position of a knight on the standard chessboard, find the number of
         * different moves the knight can perform.

        The knight can move to a square that is two squares horizontally and one square vertically,
        or two squares vertically and one square horizontally away from it.
        The complete move therefore looks like the letter L. Check out the image below
        to see all valid moves for a knight piece that is placed on one of the central squares.
         */
        var moves = 8
        if (cell[0] == 'b' || cell[0] == 'g') moves -= 2
        if (cell[1] == '2' || cell[1] == '7') moves -= 2
        if (cell[0] == 'a' || cell[0] == 'h') moves /= 2
        if (cell[1] == '1' || cell[1] == '8') moves /= 2
        return moves
    }

    fun deleteDigit(n: Int): Int {
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