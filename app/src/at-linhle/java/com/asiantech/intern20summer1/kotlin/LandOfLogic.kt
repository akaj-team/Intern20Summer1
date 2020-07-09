package com.asiantech.intern20summer1.kotlin

private const val HOUR = 23
private const val MINUTE = 23
private const val NINE = 9
private const val THREE = 3

class LandOfLogic {
    fun longestWord(text: String): String? {
        var longest = ""
        var temp = ""
        for (i in text.indices) {
            if ((text[i] in 'A'..'Z')
                || (text[i] in 'a'..'z')
            ) {
                temp += text[i]
            } else {
                if (temp.length > longest.length) {
                    longest = temp
                }
                temp = ""
            }
        }
        return if (temp.length > longest.length) temp else longest
    }

    fun validTime(time: String): Boolean {
        val temp = time.split(":")
        if (temp[0].toInt() !in 0..HOUR
            || temp[1].toInt() !in 0..MINUTE
        ) {
            return false
        }
        return true
    }

    fun sumUpNumbers(inputString: String): Int {
        var sum = 0
        var temp = ""
        for (i in inputString.indices) {
            if (Character.isDigit(inputString[i])) {
                temp += inputString[i]
            } else {
                if (temp.isNotEmpty()) {
                    sum += temp.toInt()
                    temp = ""
                }
            }
        }
        return if (temp.isNotEmpty()) sum + temp.toInt() else sum
    }

    fun differentSquares(matrix: MutableList<MutableList<Int>>): Int {
        val lStrings = arrayListOf<String>()
        for (i in 0 until matrix.size - 1) {
            for (j in 0 until matrix[0].size - 1) {
                var str = matrix[i][j].toString()
                str += matrix[i][j + 1]
                str += matrix[i + 1][j]
                str += matrix[i + 1][j + 1]
                if (!lStrings.contains(str)) {
                    lStrings.add(str)
                }
            }
        }
        return lStrings.size
    }

    fun digitsProduct(product: Int): Int {
        if (product == 0) {
            return 10
        }
        if (product == 1) {
            return 1
        }
        var str = ""
        var temp = product
        for (i in NINE downTo 2) {
            while (temp % i == 0) {
                temp /= i
                str = i.toString() + str
            }
        }
        if (temp > 1) {
            return -1
        }
        return str.toInt()
    }

    fun fileNaming(names: MutableList<String>): MutableList<String> {
        val set = HashSet<String>()
        for (i in 0 until names.size) {
            val temp = names[i]
            if (!set.contains(names[i])) {
                set.add(names[i])
            } else {
                var count = 1
                var check = temp
                while (set.contains(check)) {
                    check = "$temp($count)"
                    count++
                }
                set.add(check)
                names[i] = check
            }
        }
        return names
    }

    fun messageFromBinaryCode(code: String): String {
        var temp = ""
        var str = code
        while (str.isNotEmpty()) {
            temp += Integer.parseInt(str.substring(0, 8), 2).toChar()
            str = str.substring(8)
        }
        return temp
    }

    fun spiralNumbers(n: Int): MutableList<MutableList<Int>> {
        if (n == 0) {
            return mutableListOf()
        }
        val matrix = Array(n) { IntArray(n) }
        var start = 0
        var end = n - 1
        var num = 1
        while (start < end) {
            for (col in start until end) {
                matrix[start][col] = num++
            }
            for (row in start until end) {
                matrix[row][end] = num++
            }
            for (col in end downTo start + 1) {
                matrix[end][col] = num++
            }
            for (row in end downTo start + 1) {
                matrix[row][start] = num++
            }
            start++
            end--
        }
        if (start == end) {
            matrix[start][start] = num
        }
        return matrix.map { it.toMutableList() }.toMutableList()
    }

    fun sudoku(grid: MutableList<MutableList<Int>>): Boolean {
        for (i in 0 until grid.size) {
            for (j in 0 until grid[0].size - 1) {
                for (k in j + 1 until grid[0].size) {
                    if (grid[i][j] == grid[i][k]) {
                        return false
                    } else if (grid[j][i] == grid[k][i]) {
                        return false
                    }
                }
            }
        }
        var lb = 0
        while (lb < NINE) {
            var cb = 0
            while (cb < NINE) {
                val number = HashSet<Int>()
                for (i in lb until lb + THREE) {
                    for (j in cb until cb + THREE) {
                        if (!number.add(grid[i][j])) {
                            return false
                        }
                    }
                }
                cb += THREE
            }
            lb += THREE
        }
        return true
    }
}
