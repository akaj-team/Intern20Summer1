package com.asiantech.intern20summer1.kotlinIntro

class IslandOfKnowledge {
    fun areEquallyStrong(
        yourLeft: Int,
        yourRight: Int,
        friendsLeft: Int,
        friendsRight: Int
    ): Boolean {
//        Call two arms equally strong if the heaviest weights they each are able to lift are equal.
//
//        Call two people equally strong if their strongest arms are equally strong (the strongest
//                arm can be both the right and the left), and so are their weakest arms.
//
//        Given your and your friend's arms' lifting capabilities find out if you two are equally strong.
        return (yourLeft == friendsLeft || yourLeft == friendsRight)
                && (yourRight == friendsRight || yourRight == friendsLeft)
    }

    fun arrayMaximalAdjacentDifference(inputArray: MutableList<Int>): Int {
//        Given an array of integers, find the maximal absolute difference between any two of
//        its adjacent elements.
        var max = 0
        for (i in 0..inputArray.size - 2) {
            if (Math.abs(inputArray[i] - inputArray[i + 1]) > max) {
                max = Math.abs(inputArray[i] - inputArray[i + 1])
            }
        }
        return max
    }

    fun isIPv4Address(inputString: String): Boolean {
//        An IP address is a numerical label assigned to each device (e.g., computer, printer)
//        participating in a computer network that uses the Internet Protocol for communication.
//        There are two versions of the Internet protocol, and thus two versions of addresses.
//        One of them is the IPv4 address.
//        Given a string, find out if it satisfies the IPv4 address naming rules.
        if (inputString[0] == '.') {
            return false
        }
        var sArray: List<String> = inputString.split(".")
        if (sArray.size != 4) {
            return false
        }
        try {
            for (i in 0..3) {
                if (sArray[i].toInt() > 255 || sArray[i].toInt() < 0) {
                    return false
                }
                if (sArray[i][0] == '0' && sArray[i].length >= 2) {
                    return false
                }
            }
        } catch (e: NumberFormatException) {
            return false
        }

        return true
    }

    fun avoidObstacles(inputArray: MutableList<Int>): Int {
//        You are given an array of integers representing coordinates of obstacles situated on a straight line.
//
//        Assume that you are jumping from the point with coordinate 0 to the right.
//        You are allowed only to make jumps of the same length represented by some integer.
//
//        Find the minimal length of the jump enough to avoid all the obstacles.
        var max = inputArray.max()
        var a = 0
        for (i in 2..max!! + 1) {
            var count = 0
            for (j in 0..inputArray.size - 1) {
                if (inputArray[j] % i != 0) {
                    count++
                }
            }
            if (count == inputArray.size) {
                a = i
                break
            }
        }
        return a
    }

    fun boxBlur(image: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
//        Last night you partied a little too hard. Now there's a black and white photo of you
//        that's about to go viral! You can't let this ruin your reputation, so you want to apply
//        the box blur algorithm to the photo to hide its content.
//
//        The pixels in the input image are represented as integers. The algorithm distorts the
//        input image in the following way: Every pixel x in the output image has a value equal to
//        the average value of the pixel values from the 3 × 3 square that has its center at x,
//        including x itself. All the pixels on the border of x are then removed.
//
//        Return the blurred image as an integer, with the fractions rounded down.
        var blur = mutableListOf<MutableList<Int>>()
        val row = image.size
        val col = image[0].size
        for (i in 0 until row - 2) {
            var temp = mutableListOf<Int>()
            for (j in 0 until col - 2) {
                // print(image[i+2][j+1])
                temp.add(
                    (image[i][j] + image[i][j + 1] + image[i][j + 2]
                            + image[i + 1][j] + image[i + 1][j + 1] + image[i + 1][j + 2]
                            + image[i + 2][j] + image[i + 2][j + 1] + image[i + 2][j + 2]) / 9
                )
            }
            blur.add(temp)
        }
        return blur
    }

    fun minesweeper(matrix: MutableList<MutableList<Boolean>>): MutableList<MutableList<Int>> {
//        In the popular Minesweeper game you have a board with some mines and those cells that
//                don't contain a mine have a number in it that indicates the total number of mines
//        in the neighboring cells. Starting off with some arrangement of mines we want to create a
//        Minesweeper game setup.
        var mine = mutableListOf<MutableList<Int>>()
        var row = matrix.size
        var col = matrix[0].size
        if (row == 0) {
            return mine
        }
        for (i in 0..row - 1) {
            var tmp = mutableListOf<Int>()
            for (j in 0..col - 1) {
                var count = 0
                if (i != 0 && j != 0 && matrix[i - 1][j - 1] == true) {
                    count++
                }
                if (i != 0 && matrix[i - 1][j] == true) {
                    count++
                }
                if (i != 0 && j != col - 1 && matrix[i - 1][j + 1]) {
                    count++
                }
                if (j != 0 && matrix[i][j - 1] == true) {
                    count++
                }
                if (j != col - 1 && matrix[i][j + 1] == true) {
                    count++
                }
                if (i != row - 1 && j != 0 && matrix[i + 1][j - 1] == true) {
                    count++
                }
                if (i != row - 1 && matrix[i + 1][j]) {
                    count++
                }
                if (i != row - 1 && j != col - 1 && matrix[i + 1][j + 1]) {
                    count++
                }
                tmp.add(count)
            }
            mine.add(tmp)
        }
        return mine
    }

}

fun main(args: Array<String>) {
    var nah = IslandOfKnowledge()
    val yourLeft = 10
    val yourRight = 15
    val friendsLeft = 15
    val friendsRight = 10
    val intArray = mutableListOf<Int>(2, 4, 1, 0)
    val image =
        mutableListOf(mutableListOf(1, 1, 1), mutableListOf(1, 7, 1), mutableListOf(1, 1, 1))
    val mine = mutableListOf(
        mutableListOf(true, false, false), mutableListOf(false, true, false),
        mutableListOf(false, false, false)
    )
    val ip = "172.16.254.1"
    println(nah.areEquallyStrong(yourLeft, yourRight, friendsLeft, friendsRight))
    println(nah.arrayMaximalAdjacentDifference(intArray))
    println(nah.isIPv4Address(ip))
    println(nah.avoidObstacles(intArray))
    println(nah.boxBlur(image))
    println(nah.minesweeper(mine))
}
