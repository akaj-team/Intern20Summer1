package com.asiantech.intern20summer1.kotlinIntro

class RainbowOfClarity {
    fun isDigit(symbol: Char): Boolean {
//        Determine if the given character is a digit or not.
        return symbol.isDigit()
    }

    fun lineEncoding(s: String): String {
//        Given a string, return its encoding defined as follows:
//
//        First, the string is divided into the least possible number of disjoint substrings
//        consisting of identical characters
//        for example, "aabbbc" is divided into ["aa", "bbb", "c"]
//        Next, each substring with length greater than one is replaced with a concatenation of
//        its length and the repeating character
//        for example, substring "bbb" is replaced by "3b"
//        Finally, all the new strings are concatenated together in the same order and a new string is returned.
        var tmp = ""
        var count = 1
        for (i in 0..s.length - 1) {
            if (i != s.length - 1 && s[i] == s[i + 1]) {
                print(count)
                count++
            } else if (count > 1) {
                tmp += count.toString() + s[i].toString()
                count = 1
            } else {
                tmp += s[i].toString()
                count = 1
            }
        }
        return tmp
    }

    fun chessKnight(cell: String): Int {
//        Given a position of a knight on the standard chessboard, find the number of different moves
//        the knight can perform.
//
//        The knight can move to a square that is two squares horizontally and one square vertically,
//        or two squares vertically and one square horizontally away from it. The complete move therefore
//                looks like the letter L. Check out the image below to see all valid moves for a knight
//                piece that is placed on one of the central squares.
        var move = 8
        if (cell[1] == '2' || cell[1] == '7') {
            move -= 2
        }
        if (cell[0] == 'b' || cell[0] == 'g') {
            move -= 2
        }
        if (cell[1] == '1' || cell[1] == '8') {
            move /= 2
        }
        if (cell[0] == 'a' || cell[0] == 'h') {
            move /= 2
        }

        return move
    }

    fun deleteDigit(n: Int): Int {
//        Given some integer, find the maximal number you can obtain by deleting exactly one digit
//        of the given number
        var s = n.toString()
        var tmp = ""
        var max = 0
        for (i in s.indices) {
            tmp = s.substring(0, i) + s.substring(i + 1, s.length)
            if (max < tmp.toInt()) {
                max = tmp.toInt()
            }
        }
        return max
    }

}

fun main(args: Array<String>) {
    var nah = RainbowOfClarity()
    println(nah.isDigit('g'))
    println(nah.lineEncoding("asdcvbaavv"))
    println(nah.chessKnight("a2"))
    println(nah.deleteDigit(5467))
}
