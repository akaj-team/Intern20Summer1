package com.asiantech.intern20summer1.kotlin

object IslandOfKnowledge {
    private const val FOUR = 4
    private const val NINE = 9
    private const val TEN = 10
    private const val MAX_IPv4 = 255

    fun areEquallyStrong(
        yourLeft: Int,
        yourRight: Int,
        friendsLeft: Int,
        friendsRight: Int
    ): Boolean {
        var myHeaviestWeights: Int
        var friendsHeaviestWeights: Int
        var mySum: Int = yourLeft + yourRight
        var friendsSum: Int = friendsLeft + friendsRight

        if (yourLeft > yourRight) {
            myHeaviestWeights = yourLeft
        } else {
            myHeaviestWeights = yourRight
        }

        if (friendsLeft > friendsRight) {
            friendsHeaviestWeights = friendsLeft
        } else {
            friendsHeaviestWeights = friendsRight
        }

        if (mySum == friendsSum) {
            if (myHeaviestWeights == friendsHeaviestWeights) {
                return true;
            }
        }
        return false;
    }

    fun arrayMaximalAdjacentDifference(inputArray: Array<Int>): Int {
        var maxDiff: Int = 0;

        for (i in 0 until inputArray.size - 1) {
            var diff: Int = Math.abs(inputArray[i] - inputArray[i + 1]);
            if (maxDiff < diff) {
                maxDiff = diff;
            }
        }

        return maxDiff;
    }

    fun isIPv4Address(inputString: String): Boolean {
        var check: Boolean = true
        var regex: String = "[.]"
        var numbers: Array<String> = inputString.split(regex.toRegex()).toTypedArray()

        if (numbers.size != FOUR) {
            check = false
        } else {
            for (i in numbers.indices) {
                try {
                    if ((numbers[i].length == 2) && Integer.parseInt(numbers[i]) < TEN) {
                        check = false
                    }
                    if (numbers[i].isNullOrEmpty()) {
                        check = false
                    }
                    if (Integer.parseInt(numbers[i]) < 0 || Integer.parseInt(numbers[i]) > MAX_IPv4) {
                        check = false
                    }
                } catch (ex1: IndexOutOfBoundsException) {
                    check = false
                }
            }
        }

        return check
    }

    fun avoidObstacles(inputArray: Array<Int>): Int {
        var check: Boolean = false
        var count: Int = 1
        while (true) {
            for (i in 0 until inputArray.size) {
                if (inputArray[i] % count != 0) {
                    check = true
                } else {
                    check = false
                    break
                }
            }

            if (check == true) {
                break
            }

            count++;
        }

        return count
    }

    fun boxBlur(image: Array<Array<Int>>): Array<IntArray> {
        var sum: Int = 0
        var count: Int = 0
        var list: MutableList<Int> = mutableListOf()

        for (i in image.indices) {
            for (j in image[0].indices) {
                count += FOUR

                if (i < (image.size - 1)) {
                    count += 2
                }

                if (j < (image[0].size - 1)) {
                    count += 2
                }

                if (i < (image.size - 1) && j < (image[0].size - 1)) {
                    count += 1
                }

                try {
                    if (count == NINE) {
                        sum += image[i][j]
                        sum += image[i][j - 1]
                        sum += image[i - 1][j]
                        sum += image[i - 1][j - 1]
                        sum += image[i + 1][j]
                        sum += image[i + 1][j - 1]
                        sum += image[i][j + 1]
                        sum += image[i - 1][j + 1]
                        sum += image[i + 1][j + 1]

                        list.add(sum / count)
                    }
                } catch (ex2: Exception) {
                    println("Exception 2")
                }

                sum = 0
                count = 0
            }
        }

        var blurImage: Array<IntArray> = Array(image.size - 2) { IntArray(image[0].size - 2) }
        for (element in blurImage) {
            for (j in blurImage[0].indices) {
                element[j] = list[0]
                list.remove(0)
            }
        }

        return blurImage
    }

    fun minesweeper(matrix: Array<Array<Boolean>>): Array<IntArray> {
        var result: Array<IntArray> = Array(matrix.size) { IntArray(matrix[0].size) }

        for (element in result) {
            for (j in result[0].indices) {
                element[j] = 0
            }
        }

        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                if (matrix[i][j]) {
                    try {
                        if (j > 0) result[i][j - 1]++
                        if (j < matrix[0].size - 1) result[i][j + 1]++
                        if (i > 0) result[i - 1][j]++
                        if (i < matrix.size - 1) result[i + 1][j]++
                        if (i > 0 && j > 0) result[i - 1][j - 1]++
                        if (i < matrix.size - 1 && j > 0) result[i + 1][j - 1]++
                        if (i > 0 && j < matrix[0].size - 1) result[i - 1][j + 1]++
                        if (i < matrix.size - 1 && j < matrix[0].size - 1) result[i + 1][j + 1]++
                    } catch (ex3: Exception) {
                        println("Exception 3")
                    }
                }
            }
        }

        return result
    }
}
