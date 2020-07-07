package com.asiantech.intern20summer1.introkotlin

class DarkWilderness {

    /** 38 **
     *Caring for a plant can be hard work, but since you tend to it regularly, you have a plant that grows consistently.
     * Each day, its height increases by a fixed amount represented by the integer upSpeed.
     * But due to lack of sunlight, the plant decreases in height every night, by an amount represented by downSpeed.
     * Since you grew the plant from a seed, it started at height 0 initially.
     * Given an integer desiredHeight, your task is to find how many days it'll take for the plant to reach this height.
     */
    fun growingPlant(upSpeed: Int, downSpeed: Int, desiredHeight: Int): Int {
        var mCurrent = 0
        var dayNumber = 0
        while (mCurrent < desiredHeight) {
            dayNumber++
            mCurrent += upSpeed
            if (mCurrent >= desiredHeight) {
                break
            }
            mCurrent -= downSpeed
        }
        return dayNumber
    }

    /** 39 **
     *You found two items in a treasure chest! The first item weighs weight1 and is worth value1,
     * and the second item weighs weight2 and is worth value2. What is the total maximum value
     * of the items you can take with you, assuming that your max weight capacity is maxW and
     * you can't come back for the items later?
     * Note that there are only two items and you can't bring more than one item of each type,
     * i.e. you can't take two first items or two second items.
     */
    fun knapsackLight(value1: Int, weight1: Int, value2: Int, weight2: Int, maxW: Int): Int {
        return if (maxW >= weight1 + weight2) {
             value1 + value2
        } else if (value1 >= value2 && maxW >= weight1) {
             value1
        } else if (value1 >= value2 && maxW >= weight2) {
             value2
        } else if (value1 < value2 && maxW >= weight2) {
             value2
        } else if (value1 < value2 && maxW >= weight1) {
             value1
        } else 0
    }

    /** 40 **
     * Given a string, output its longest prefix which contains only digits.
     */
    fun longestDigitsPrefix(inputString: String): String {
        val mArray =
            inputString.split("[^\\d]+".toRegex()) //regex tách ra một mảng chỉ có các chuỗi số
        return if (inputString.first()
                .isDigit()
        ) { // kiểm tra xem ký tự đầu tiên của chuỗi có phải số ko
            mArray[0] // nếu là số thì xuất ra chuỗi số đầu tiên
        } else "" // ko thì xuất chuỗi rỗng
    }

    /** 41 **
     * Let's define digit degree of some positive integer as the number of times we need to replace
     * this number with the sum of its digits until we get to a one digit number.
     * Given an integer, find its digit degree.
     */
    fun digitDegree(n: Int): Int {
        var m = n
        var mCount = 0
        while (m >= 10) {
            mCount++
            m = m.toString().toCharArray().sumBy { char -> char.toString().toInt() }
        }
        return mCount
    }

    /** 42 **
     * Given the positions of a white bishop and a black pawn on the standard chess board,
     * determine whether the bishop can capture the pawn in one move.
     * The bishop has no restrictions in distance for each move, but is limited to diagonal movement.
     * Check out the example below to see how it can move:
     */
    fun bishopAndPawn(bishop: String, pawn: String): Boolean {
        return Math.abs(bishop[0] - pawn[0]) == Math.abs(
            bishop[1] - pawn[1]
        )
    }
}
