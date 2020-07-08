package com.asiantech.intern20summer1.introkotlin

class LandOfLogic {
    /** 52 **
     *Define a word as a sequence of consecutive English letters.
     *Find the longest word from the given string.
     */
    fun longestWord(text: String): String {
        val mArray = text.split("[^a-zA-Z]+".toRegex())//  tách chuỗi lấy các từ bằng regex
        var mOutput = ""  // chuỗi đầu ra
        mArray.forEach {  // quét mảng lấy từ có độ dài dài nhất
            if (it.length > mOutput.length) {
                mOutput = it
            }
        }
        return mOutput
    }

    /** 53 **
     *Check if the given string is a correct time representation of the 24-hour clock.
     */
    fun validTime(time: String): Boolean {
        return time.matches("""(([01][\d])|([2][0-3]))[:][0-5][\d]""".toRegex()) // regex của đồng hồ
    }

    /** 54 **
     *CodeMaster has just returned from shopping.
     * He scanned the check of the items he bought and gave the resulting string
     * to Ratiorg to figure out the total number of purchased items. Since Ratiorg
     * is a bot he is definitely going to automate it, so he needs a program that sums up
     * all the numbers which appear in the given input.
     * Help Ratiorg by writing a function that returns the sum of numbers that appear in the given inputString.
     */
    fun sumUpNumbers(inputString: String): Int {
        val mArrayNumber =
            inputString.split("[^0-9]+".toRegex()) // tach các số ra khỏi chuỗi bằng regex
        return mArrayNumber.map { if (it == "") 0 else it.toInt() }
            .sum() // tổng các số đó lại. ta dùng map để chuyển mảng string về mảng Int

    }

    /** 55 **
     *Given a rectangular matrix containing only digits,
     * calculate the number of different 2 × 2 squares in it.
     */
    fun differentSquares(matrix: MutableList<MutableList<Int>>): Int {
        val set = HashSet<String>() // tạo hashset bộ đệm lưu các giá trị khác nhau
        for (i in 0 until matrix.size - 1) { // lồng 2 vòng pho để tạo các matrix con 2x2
            for (j in 0 until matrix[0].size - 1) {
                val st =
                    "[" + matrix[i][j] + matrix[i][j + 1] + matrix[i + 1][j] + matrix[i + 1][j + 1] + "]"
                set.add(st) // đưa maxtrix con vào hashset
            }
        }
        return set.size // số matrix con được đưa vào hashset thành công là sô matrix khác nhau
    }

    /** 56 **
     *Given an integer product, find the smallest positive (i.e. greater than 0) integer the product
     *of whose digits is equal to product. If there is no such integer, return -1 instead.
     */
    fun digitsProduct(product: Int): Int {
        var product = product
        if (product == 0) {  // điều kiện đặc biệt
            return 10
        } else if (product < 10) { // điều kiện đặc biệt
            return product
        }
        var Output = ""
        for (i in 9 downTo 2) {
            while (product % i == 0) {
                Output = i.toString() + Output
                product /= i
            }
        }
        return if (product == 1) {
            Output.toInt()
        } else {
            -1
        }
    }

    /** 57 **
     * You are given an array of desired filenames in the order of their creation. Since two files
     * cannot have equal names, the one which comes later will have an addition to its name in a form
     * of (k), where k is the smallest positive integer such that the obtained name is not used yet.
     * Return an array of names that will be given to the files.
     */
    fun fileNaming(names: MutableList<String>): MutableList<String> {
        val mListName: MutableList<String> = mutableListOf()  // tạo mảng đầu ra
        for (st in names) { // quyets mảng đầu vào
            var mCount = 0  // biến đếm số ghi phía sau
            var buf = st // chuỗi đệm
            while (true) {  // quét vòng lặp vô hạn cho tới khi gặp break
                mCount++  // mỗi lần vô cộng thêm 1 giá trị
                if (mListName.contains(buf)) {  // nếu trong mListName chứa tên rồi thì cộng thêm số phía sau
                    buf = "$st($mCount)"
                    println(buf)
                } else {
                    mListName.add(buf) // nếu chưa chứa thì add vào List
                    break // thoát khỏi while
                }
            }
        }
        return mListName // xuất mảng
    }

    /** 58 **
     *You are taking part in an Escape Room challenge designed specifically for programmers.
     * In your efforts to find a clue, you've found a binary code written on the wall behind a vase,
     * and realized that it must be an encrypted message. After some thought, your first guess is that
     * each consecutive 8 bits of the code stand for the character with the corresponding extended ASCII code.
     * Assuming that your hunch is correct, decode the message.
     */
    fun messageFromBinaryCode(code: String): String {
        var mOut = ""
        var i = 0
        while (i < code.length) {
            val x = code.substring(i, i + 8).toInt(2)
            mOut += x.toChar().toString()
            i += 8
        }
        return mOut
    }

    /** 59 **
     *Construct a square matrix with a size N × N containing integers from 1 to N * N in a spiral
     * order, starting from top-left and in clockwise direction.
     */
    fun spiralNumbers(n: Int): MutableList<MutableList<Int>> {
        val mArrayOutput = Array(n) { IntArray(n).toMutableList() }.toMutableList()
        var mLeft = 0
        var mRight = n - 1
        var mTop = 0
        var mDown = n - 1
        var count = 1
        while (count <= n * n) {
            for (x in mLeft..mRight) {
                mArrayOutput[mTop][x] = count++
            }
            mTop++
            for (y in mTop..mDown) {
                mArrayOutput[y][mRight] = count++
            }
            mRight--
            for (x in mRight downTo mLeft) {
                mArrayOutput[mDown][x] = count++
            }
            mDown--
            for (y in mDown downTo mTop) {
                mArrayOutput[y][mLeft] = count++
            }
            mLeft++
        }
        return mArrayOutput
    }

    /** 60 **
     * Sudoku is a number-placement puzzle. The objective is to fill a 9 × 9 grid with digits so that
     * each column, each row, and each of the nine 3 × 3 sub-grids that compose the grid contains all
     * of the digits from 1 to 9.
     * This algorithm should check if the given grid of numbers represents a correct solution to Sudoku.
     */
    fun sudoku(grid: MutableList<MutableList<Int>>): Boolean {
        for (i in 0..8) { // quet kiểm tra theo chiều ngang
            val buf = HashSet<Int>()
            for (j in 0..8) {
                if (!buf.add(grid[i][j])) {
                    return false
                }
            }
        }
        for (i in 0..8) {// quet kiểm tra theo chiều dọc
            val buf = HashSet<Int>()
            for (j in 0..8) {
                if (!buf.add(grid[j][i])) {
                    return false
                }
            }
        }
        for (i in 0..2) {// quet kiểm tra theo grid
            for (j in 0..2) {
                val buf = HashSet<Int>()
                for (k in 0..2) {
                    for (h in 0..2) {
                        if (!buf.add(grid[i * 3 + k][j * 3 + h])) {
                            return false
                        }
                    }
                }
            }
        }
        return true
    }
}
