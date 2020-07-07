package com.asiantech.intern20summer1.intro.kotlin

object LandOfLogic {
    @JvmStatic
    fun main(args: Array<String>) {
        //RUN main() with Coverage
        println("Ex 52: " + longestWord("Ready, steady, go!"))

        println("Ex 53: " + validTime("13:58"))

        println("Ex 54: " + sumUpNumbers("2 apples, 12 oranges"))

        println(
            "Ex 55: " + differentSquares(
                mutableListOf(
                    mutableListOf(1, 2, 1),
                    mutableListOf(2, 2, 2),
                    mutableListOf(2, 2, 2),
                    mutableListOf(1, 2, 3),
                    mutableListOf(2, 2, 1)
                )
            )
        )

        println("Ex 56: " + digitsProduct(12))

        println("Ex 57: " + fileNaming(mutableListOf("doc", "doc", "image", "doc(1)", "doc")))

        println("Ex 58: " + messageFromBinaryCode("010010000110010101101100011011000110111100100001"))

        println("Ex 59: " + spiralNumbers(3))

        println(
            "Ex 60: " + sudoku(
                mutableListOf(
                    mutableListOf(1, 3, 2, 5, 4, 6, 9, 8, 7),
                    mutableListOf(4, 6, 5, 8, 7, 9, 3, 2, 1),
                    mutableListOf(7, 9, 8, 2, 1, 3, 6, 5, 4),
                    mutableListOf(9, 2, 1, 4, 3, 5, 8, 7, 6),
                    mutableListOf(3, 5, 4, 7, 6, 8, 2, 1, 9),
                    mutableListOf(6, 8, 7, 1, 9, 2, 5, 4, 3),
                    mutableListOf(5, 7, 6, 9, 8, 1, 4, 3, 2),
                    mutableListOf(2, 4, 3, 6, 5, 7, 1, 9, 8),
                    mutableListOf(8, 1, 9, 3, 2, 4, 7, 6, 5)
                )
            )
        )
    }

    private const val INDEX_OF_MINUTE: Int = 3
    private const val MAX_HOUR: Int = 23
    private const val MAX_MINUTE: Int = 59
    private const val SPECIAL_HOUR: Int = 24
    private const val TO: Int = 4
    private const val SPECIAL_PRODUCT: Int = 10
    private const val MAX_DIGIT: Int = 9
    private const val SIZE_BIT: Int = 8
    private const val SKIP: Int = 3


    private fun longestWord(text: String): String {
        /**
         *Define a word as a sequence of consecutive English letters. Find the longest word from the given string.
         */
        val x = text.split("[^a-zA-Z]".toRegex()).toTypedArray()
        var longestWord = ""
        var max = 0
        for (i in x.indices) {
            if (x[i].length > max) {
                max = x[i].length
                longestWord = x[i]
            }
        }
        return longestWord
    }

    private fun validTime(time: String): Boolean {
        /**
         *Check if the given string is a correct time representation of the 24-hour clock.
         */
        val h = Integer.valueOf(time.substring(0, 2))
        val m = Integer.valueOf(time.substring(INDEX_OF_MINUTE))

        return when {
            h < 0 || h > MAX_HOUR -> false
            m < 0 || m > MAX_MINUTE -> false
            else -> !(h == SPECIAL_HOUR && m == 0)
        }
    }

    private fun sumUpNumbers(inputString: String): Int {
        /**
         * CodeMaster has just returned from shopping. He scanned the check of the items he bought
         * and gave the resulting string to Ratiorg to figure out the total number of purchased items.
         * Since Ratiorg is a bot he is definitely going to automate it, so he needs a program
         * that sums up all the numbers which appear in the given input.

        Help Ratiorg by writing a function that returns the sum of numbers that appear in the given inputString.
         */
        val str = inputString.split("[^0-9]".toRegex()).toTypedArray()
        var sum = 0
        for (i in str.indices) {
            if (str[i] != "") {
                sum += Integer.valueOf(str[i])
            }
        }
        return sum
    }

    private fun differentSquares(matrix: MutableList<MutableList<Int>>): Int {
        /**
         * Given a rectangular matrix containing only digits,
         * calculate the number of different 2 × 2 squares in it.
         */
        val matrixSize = matrix.size
        val vectorSize = matrix[0].size
        var unique: MutableSet<String> = mutableSetOf()

        for (i in 1 until matrixSize) {
            for (j in 1 until vectorSize) {
                val a = matrix[i - 1][j - 1]
                val b = matrix[i - 1][j]
                val c = matrix[i][j - 1]
                val d = matrix[i][j]
                unique.add("$a,$b,$c,$d")
            }
        }
        return unique.size
    }

    private fun digitsProduct(product: Int): Int {
        /**
         * Given an integer product, find the smallest positive (i.e. greater than 0)
         * integer the product of whose digits is equal to product. If there is no such integer,
         * return -1 instead.
         */
        var p = product
        if (product < SPECIAL_PRODUCT) {
            return if (product > 0)
                product
            else
                SPECIAL_PRODUCT
        }
        var r = ""
        for (i in MAX_DIGIT downTo 2) {
            while (p % i == 0) {
                p /= i
                r = ('0' + i).toString() + r
            }
        }
        return when {
            p != 1 -> -1
            else -> r.toInt()
        }
    }

    private fun fileNaming(names: MutableList<String>): MutableList<String> {
        /**
         * You are given an array of desired filenames in the order of their creation. Since two files cannot have equal names, the one which comes later will have an addition to its name in a form of (k), where k is the smallest positive integer such that the obtained name is not used yet.

        Return an array of names that will be given to the files.
         */
        val newNames = mutableListOf<String>()
        names.forEach {
            var counter = 0
            var name = it
            while (name in newNames) {
                counter++
                name = "$it($counter)"
            }
            newNames.add(name)
        }
        return newNames
    }

    private fun messageFromBinaryCode(code: String): String {
        /**
         * You are taking part in an Escape Room challenge designed specifically for programmers.
         * In your efforts to find a clue, you've found a binary code written on the wall behind a vase,
         * and realized that it must be an encrypted message. After some thought,
         * your first guess is that each consecutive 8 bits of the code stand for the character
         * with the corresponding extended ASCII code.

        Assuming that your hunch is correct, decode the message.
         */
        var result: String = ""
        for (i in 0 until code.length / SIZE_BIT) {
            val a = code.substring(i * SIZE_BIT, (i + 1) * SIZE_BIT).toInt(2)
            result += a.toChar()
        }
        return result
    }

    private fun spiralNumbers(n: Int): MutableList<MutableList<Int>> {
        /**
         * Construct a square matrix with a size N × N containing integers from 1 to N * N
         * in a spiral order, starting from top-left and in clockwise direction.
         */
        val a = MutableList(n) { MutableList(n) { 0 } }
        var top = 0
        var right = n - 1
        var left = 0
        var bot = n - 1
        var count = 1
        while (count <= n * n) {
            for (x in left..right) {
                a[top][x] = count++
            }
            top++
            for (y in top..bot) {
                a[y][right] = count++
            }
            right--
            for (x in right downTo left) {
                a[bot][x] = count++
            }
            bot--
            for (y in bot downTo top) {
                a[y][left] = count++
            }
            left++
        }
        return a
    }

    private fun sudoku(grid: MutableList<MutableList<Int>>): Boolean {
        /**
         * Sudoku is a number-placement puzzle. The objective is to fill a 9 × 9 grid with digits
         * so that each column, each row, and each of the nine 3 × 3 sub-grids
         * that compose the grid contains all of the digits from 1 to 9.

        This algorithm should check if the given grid of numbers represents a correct solution to Sudoku.
         */
        val gridSize = grid.size
        val validRows = grid.all { it.distinct().count() == gridSize }
        val cols = (0 until gridSize).map { grid.map { r -> r[it] } }
        val validColumns = cols.all { it.distinct().count() == gridSize }

        for (i in 0 until gridSize) {
            val set = mutableSetOf<Int>()
            val k = i * SKIP
            val x = (k / gridSize) * SKIP
            val y = k % gridSize
            for (x1 in 0 until SKIP) {
                for (y1 in 0 until SKIP) {
                    set.add(grid[x + x1][y + y1])
                }
            }
            if (set.size != gridSize) return false
        }
        return validRows && validColumns
    }
}
