package com.asiantech.intern20summer1.kotlin

object RainbowOfClarity {
    fun isDigit(symbol: Char): Boolean {
        return symbol.isDigit()
    }

    fun lineEncoding(s: String): String {
        var string: String = s
        string += "#"
        var count: Int = 1
        var result: String = ""

        for (i in 1 until string.length) {
            if (string[i] == string[i - 1]) {
                count++
            } else {
                if (count > 1) {
                    result += count
                }
                result += string[i - 1]
                count = 1
            }
        }

        return result
    }

    fun chessKnight(cell: String): Int {
        var matrix: Array<IntArray> = Array(8, { IntArray(8) })
        var count: Int = 0
        var i1Cell: Int = 7 - (cell[0].toInt() - 97)
        var i2Cell: Int = cell[1].toInt() - 49
        var X: Array<Int> = arrayOf(2, 1, -1, -2, -2, -1, 1, 2)
        var Y: Array<Int> = arrayOf(1, 2, 2, 1, -1, -2, -2, -1)

        for (element in matrix) {
            for (j in matrix[0].indices) {
                element[j] = 0
            }
        }

        for (i in 0 until 8) {

            var x: Int = i1Cell + X[i]
            var y: Int = i2Cell + Y[i]

            if (x >= 0 && y >= 0 && x < matrix.size && y < matrix[0].size
                && matrix[x][y] == 0
            )
                count++
        }

        return count
    }

    fun deleteDigit(n: Int): Int {
        var number: Int = n
        var temp: String = ""
        var newNumber: Int = 0
        var listNumbers: MutableList<Int> = mutableListOf()

        while (number > 0) {
            listNumbers.add(number % 10)
            number /= 10
        }

        for (i in 0 until listNumbers.size) {
            for (j in listNumbers.size - 1 downTo 0) {
                if (j != i) {
                    temp += listNumbers.get(j)
                }
            }

            if (temp.toInt() > newNumber) {
                newNumber = temp.toInt()
            }
            temp = ""
        }

        return newNumber
    }
}
