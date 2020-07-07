package com.asiantech.intern20summer1.introkotlin

class RainbowofClarity {

    /** 48 **
     * Determine if the given character is a digit or not.
     */
    fun isDigit(symbol: Char): Boolean {
        return symbol.isDigit()
    }

    /** 49 **
     * Given a string, return its encoding defined as follows:
     * First, the string is divided into the least possible number of disjoint substrings
     * consisting of identical characters
     * For example, "aabbbc" is divided into ["aa", "bbb", "c"]
     * Next, each substring with length greater than one is replaced with a concatenation of
     * its length and the repeating character
     * For example, substring "bbb" is replaced by "3b"
     * Finally, all the new strings are concatenated together in the same order and a new string is returned.
     */
    fun lineEncoding(s: String): String {
        var mCharBuf = s[0]
        var mCount = 0;
        var mOutPutString = ""
        s.forEachIndexed { index, c ->
            if (mCharBuf == c) {
                mCount++
            } else {
                if (mCount == 1) {
                    mOutPutString += mCharBuf.toString()
                } else {
                    mOutPutString += mCount.toString() + mCharBuf.toString()
                }
                mCharBuf = c
                mCount = 1
            }
            if (index == s.length - 1) {
                if (mCount == 1) {
                    mOutPutString += mCharBuf.toString()
                } else {
                    mOutPutString += mCount.toString() + mCharBuf.toString()
                }
            }
        }
        return mOutPutString
    }

    /** 50 **
     *  Given a position of a knight on the standard chessboard, find the number of different moves the knight can perform.
     *  The knight can move to a square that is two squares horizontally and one square vertically, or two squares vertically
     *  and one square horizontally away from it. The complete move therefore looks like the letter L.
     *  Check out the image below to see all valid moves for a knight piece that is placed on one of the central squares.
     */
    fun chessKnight(cell: String): Int {
        /** regex
         * 8 ^[c-f][3-6]$
         * 6 ^[c-f][27]|[bg][3-6]$
         * 4 ^[ah][3-6]|[c-f][18]|[bg][27]$
         * 3 ^[ah][27]|[bg][18]$
         * 2 ^[ah][18]$
         */
        return if (cell.matches("^[c-f][3-6]$".toRegex())) {
            8
        } else if (cell.matches("^[c-f][27]|[bg][3-6]$".toRegex())) {
            6
        } else if (cell.matches("^[ah][3-6]|[c-f][18]|[bg][27]$".toRegex())) {
            4
        } else if (cell.matches("^[ah][27]|[bg][18]$".toRegex())) {
            3
        } else if (cell.matches("^[ah][18]$".toRegex())) {
            2
        } else 1
    }


    /** 51 **
     *Given some integer, find the maximal number you can
     * obtain by deleting exactly one digit of the given number.
     */
    fun deleteDigit(n: Int): Int {
        var mMaxValue = 0;  // số lớn nhất xuất ra
        n.toString().forEachIndexed { index, c ->  // quét chuỗi n ->String để bỏ từng phần tử
            val newValue = n.toString().filterIndexed { index2, c2 -> index2 != index }.toInt()
            // hàm trên thực hiện lọc chuỗi n để bỏ phần tử index. sau đó ta gán lại Int ta được new value
            if (newValue > mMaxValue) { // nếu new value lớn hơn max valua => max value = new value
                mMaxValue = newValue
            }
        }
        return mMaxValue
    }

}
