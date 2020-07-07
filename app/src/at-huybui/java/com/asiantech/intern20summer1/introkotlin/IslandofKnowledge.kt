package com.asiantech.intern20summer1.introkotlin

class IslandofKnowledge {

    /** 19 **
     * Call two arms equally strong if the heaviest weights they each are able to lift are equal.
     * Call two people equally strong if their strongest arms are equally strong (the strongest arm
     * can be both the right and the left), and so are their weakest arms.
     * Given your and your friend's arms' lifting capabilities find out if you two are equally strong.
     */
    fun areEquallyStrong(
        yourLeft: Int,
        yourRight: Int,
        friendsLeft: Int,
        friendsRight: Int
    ): Boolean {
        return (yourLeft == friendsLeft && yourRight == friendsRight) ||
                (yourLeft == friendsRight && yourRight == friendsLeft)
    }

    /** 20 **
     *Given an array of integers,
     *find the maximal absolute difference between any two of its adjacent elements.
     */
    fun arrayMaximalAdjacentDifference(inputArray: MutableList<Int>): Int {
        var mOutput = 0
        inputArray.forEachIndexed { index, i ->
            if (index > 0 && mOutput < Math.abs(inputArray[index] - inputArray[index - 1])) {
                mOutput = Math.abs(inputArray[index] - inputArray[index - 1])
            }
        }
        return mOutput
    }

    /** 21 **
     *An IP address is a numerical label assigned to each device (e.g., computer, printer) participating
     * in a computer network that uses the Internet Protocol for communication.
     * There are two versions of the Internet protocol, and thus two versions of addresses.
     * One of them is the IPv4 address.
     * Given a string, find out if it satisfies the IPv4 address naming rules.
     */

    fun isIPv4Address(inputString: String): Boolean {
        val regex = """\d{1,4}.\d{1,4}.\d{1,4}.\d{1,4}""".toRegex() // regex chuỗi hợp lệ
        if (!regex.matches(inputString)) {// chuỗi ko hợp lệ false ngay
            println(regex.matches(inputString))
            return false
        }

        var mArrayNumber = inputString.split(".") // tách chuỗi thành mảng phần tử
        mArrayNumber.forEach { st -> // quét mảng phần tử
            if (st.length > 1 && st.toInt() == 0) {   // nếu giá trị bằng ko mà độ dài chuỗi > 1 =>false
                return false
            } else if (st.toInt() < 0 || st.toInt() > 255) { // giá trị < 0 và > 255 false
                return false
            } else if (st.toInt() in 1..255 && st.substring(0, 1) == "0") { // giá trị trong khoảng 1..255 mà giá trị đầu là 0 => flase
                return false
            }
        }
        return true // còn lại thì true
    }

    /** 22 **
     *You are given an array of integers representing coordinates of obstacles situated on a straight line.
     * Assume that you are jumping from the point with coordinate 0 to the right.
     * You are allowed only to make jumps of the same length represented by some integer.
     * Find the minimal length of the jump enough to avoid all the obstacles.
     */
    fun avoidObstacles(inputArray: MutableList<Int>): Int {
        var mOutPut = 1
        inputArray.sorted()
        var mFlat = true
        while (mFlat) {
            mFlat = false
            mOutPut += 1
            inputArray.forEach {
                if (it % mOutPut == 0) {
                    mFlat = true
                }
            }
        }
        return mOutPut
    }

    /** 23 **
     * The pixels in the input image are represented as integers.
     * The algorithm distorts the input image in the following way:
     * Every pixel x in the output image has a value equal to the average value
     * of the pixel values from the 3 × 3 square that has its center at x, including x itself.
     * All the pixels on the border of x are then removed.
     * Return the blurred image as an integer, with the fractions rounded down.
     */

    fun boxBlur(image: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
        var mNewArray: MutableList<MutableList<Int>> = mutableListOf()
        for (i in 0 until image.size - 2) {
            var buf: MutableList<Int> = mutableListOf()
            for (j in 0 until image[0].size - 2) {
                buf.add((image[i][j] + image[i][j + 1] + image[i][j + 2] +
                        image[i + 1][j] + image[i + 1][j + 1] + image[i + 1][j + 2] +
                        image[i + 2][j] + image[i + 2][j + 1] + image[i + 2][j + 2]) / 9)

            }
            mNewArray.add(buf)
        }
        return mNewArray
    }

}
