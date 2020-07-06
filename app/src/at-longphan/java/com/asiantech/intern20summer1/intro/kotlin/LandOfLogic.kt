package com.asiantech.intern20summer1.intro.kotlin

fun main() {
    //RUN main() with Coverage
    var obj: LandOfLogic = LandOfLogic()
    println("Ex 52: " + obj.longestWord("Ready, steady, go!"))

    println("Ex 53: " + obj.validTime("13:58"))

    println("Ex 54: " + obj.sumUpNumbers("2 apples, 12 oranges"))

    println(
        "Ex 55: " + obj.differentSquares(
            mutableListOf(
                mutableListOf(1, 2, 1),
                mutableListOf(2, 2, 2),
                mutableListOf(2, 2, 2),
                mutableListOf(1, 2, 3),
                mutableListOf(2, 2, 1)
            )
        )
    )

    println("Ex 56: " + obj.digitsProduct(12))

    println("Ex 57: " + obj.fileNaming(mutableListOf("doc", "doc", "image", "doc(1)", "doc")))

    println("Ex 58: " + obj.messageFromBinaryCode("010010000110010101101100011011000110111100100001"))

    println("Ex 59: " + obj.spiralNumbers(3))

    println(
        "Ex 60: " + obj.sudoku(
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

class LandOfLogic {
    fun longestWord(text: String): String {
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

    fun validTime(time: String): Boolean {
        /**
         *Check if the given string is a correct time representation of the 24-hour clock.
         */
        val h = Integer.valueOf(time.substring(0, 2))
        val m = Integer.valueOf(time.substring(3))
        val maxHour = 23
        val maxMinute = 59
        val exceptionHour = 24
        return when {
            h < 0 || h > maxHour -> false
            m < 0 || m > maxMinute -> false
            else -> !(h == exceptionHour && m == 0)
        }
    }

    fun sumUpNumbers(inputString: String): Int {
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

    fun differentSquares(matrix: MutableList<MutableList<Int>>): Int {
        /**
         * Given a rectangular matrix containing only digits, calculate the number of
         * different 2 × 2 squares in it.
         */
        val row = matrix.size
        val col: Int = matrix[0].size
        //list contain value of each different square
        val list: ArrayList<String> = ArrayList()
        //loop to get value of all squares
        for (i in 0 until row - 1) {
            for (j in 0 until col - 1) {
                // take each 4 digits to a square
                var square = ""
                var toFour = 0
                val to = 4
                for (i2 in i until i + 2) {
                    for (j2 in j until j + 2) {
                        toFour++
                        square += matrix[i2][j2]
                        if (toFour == to) {
                            var isDuplicate = true
                            //check if duplicate
                            for (k in 0 until list.size) {
                                if (square == list[k]) {
                                    isDuplicate = false
                                    break
                                }
                            }
                            if (isDuplicate) {
                                list.add(square)
                            }
                        }
                    }
                }
            }
        }
        return list.size
    }

    fun digitsProduct(product: Int): Int {
        /**
         * Given an integer product, find the smallest positive (i.e. greater than 0)
         * integer the product of whose digits is equal to product. If there is no such integer,
         * return -1 instead.
         */
        var product: Int = product
        val exceptionProduct = 10
        if (product == 0) return exceptionProduct
        if (product < exceptionProduct) return product
        var str: String = ""
        for (i in 9 downTo 2) {
            while (product % i == 0) {
                str = i.toString() + str
                product /= i
            }
        }
        return if (product == 1) str.toInt() else -1
    }

    fun fileNaming(names: MutableList<String>): MutableList<String> {
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

    fun messageFromBinaryCode(code: String): String {
        /**
         * You are taking part in an Escape Room challenge designed specifically for programmers.
         * In your efforts to find a clue, you've found a binary code written on the wall behind a vase,
         * and realized that it must be an encrypted message. After some thought,
         * your first guess is that each consecutive 8 bits of the code stand for the character
         * with the corresponding extended ASCII code.

        Assuming that your hunch is correct, decode the message.
         */
        val sizeBit = 8
        var result: String = ""
        for (i in 0 until code.length / sizeBit) {
            val a = code.substring(i * sizeBit, (i + 1) * sizeBit).toInt(2)
            result += a.toChar()
        }
        return result
    }

    fun spiralNumbers(n: Int): MutableList<MutableList<Int>> {
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

    fun sudoku(grid: MutableList<MutableList<Int>>): Boolean {
        /**
         * Sudoku is a number-placement puzzle. The objective is to fill a 9 × 9 grid with digits
         * so that each column, each row, and each of the nine 3 × 3 sub-grids
         * that compose the grid contains all of the digits from 1 to 9.

        This algorithm should check if the given grid of numbers represents a correct solution to Sudoku.
         */
        val n = grid.size
        for (i in 0 until n) {
            val oneToN: ArrayList<Int> = ArrayList()
            for (j in 0 until n) {
                if (oneToN.contains(grid[i][j])) {
                    return false
                } else {
                    oneToN.add(grid[i][j])
                }
            }
        }
        for (i in 0 until n) {
            val oneToN: ArrayList<Int> = ArrayList()
            for (j in 0 until n) {
                if (oneToN.contains(grid[j][i])) {
                    return false
                } else {
                    oneToN.add(grid[j][i])
                }
            }
        }
        val skip = 3
        for (i in 0 until n) {
            for (j in 0 until n) {
                if (i % skip == 0 && j % skip == 0) {
                    val oneToN: ArrayList<Int> = ArrayList()
                    for (i2 in i until i + skip) {
                        for (j2 in j until j + skip) {
                            if (oneToN.contains(grid[i2][j2])) {
                                return false
                            } else {
                                oneToN.add(grid[i2][j2])
                            }
                        }
                    }
                }
            }
        }
        return true
    }
}
