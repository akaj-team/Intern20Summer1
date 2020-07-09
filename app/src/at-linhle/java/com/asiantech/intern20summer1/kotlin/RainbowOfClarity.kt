package com.asiantech.intern20summer1.kotlin

private const val TEN = 10
class RainbowOfClarity {
    fun isDigit(symbol: Char): Boolean {
        return Character.isDigit(symbol)
    }

    fun lineEncoding(s: String): String {
        var s1 = ""
        var count = 1
        var n = 0
        for (i in 1 until s.length) {
            n = i
            if (s[i] == s[i - 1]) count++
            else {
                if (count == 1) s1 += s[i - 1]
                else {
                    s1 += count
                    s1 += s[i - 1]
                    count = 1
                }
            }
        }
        if (count == 1) s1 += s[n]
        else {
            s1 += count
            s1 += s[n]
        }
        return s1
    }

    fun chessKnight(cell: String): Int {
        var count = 0
        if ((cell[0] - 1 in 'a'..'h')
            && (cell[1] - 2 in '1'..'8')
        ) {
            count++
        }
        if ((cell[0] - 2 in 'a'..'h')
            && (cell[1] - 1 in '1'..'8')
        ) {
            count++
        }
        if ((cell[0] + 1 in 'a'..'h')
            && (cell[1] + 2 in '1'..'8')
        ) {
            count++
        }
        if ((cell[0] + 2 in 'a'..'h')
            && (cell[1] + 1 in '1'..'8')
        ) {
            count++
        }
        if ((cell[0] - 1 in 'a'..'h')
            && (cell[1] + 2 in '1'..'8')
        ) {
            count++
        }
        if ((cell[0] - 2 in 'a'..'h')
            && (cell[1] + 1 in '1'..'8')
        ) {
            count++
        }
        if ((cell[0] + 1 in 'a'..'h')
            && (cell[1] - 2 in '1'..'8')
        ) {
            count++
        }
        if ((cell[0] + 2 in 'a'..'h')
            && (cell[1] - 1 in '1'..'8')
        ) {
            count++
        }
        return count
    }

    fun deleteDigit(n: Int): Int {
        var max = 0
        var t = 1
        while (t < n) {
            max = maxOf(n / TEN / t * t + n % t, max)
            t *= TEN
        }
        return max
    }
}
