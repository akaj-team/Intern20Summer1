package com.asiantech.intern20summer1.kotlin

import kotlin.math.abs

object IslandOfKnowledge {
    private const val LENGTH_SPLIT_IP_V4 = 4
    private const val MAX_IP_VALUE = 255

    /*
    * Given your and your friend's arms' lifting capabilities find out if you two are equally strong.
    */
    private fun areEquallyStrong(
        yourLeft: Int,
        yourRight: Int,
        friendsLeft: Int,
        friendsRight: Int
    ): Boolean = setOf(yourLeft, yourRight) == setOf(friendsLeft, friendsRight)

    /*
    *   Given an array of integers, find the maximal absolute difference between any two of its adjacent elements
    */
    private fun arrayMaximalAdjacentDifference(inputArray: MutableList<Int>) : Int? {
        return inputArray.zip(inputArray.drop(1)) { a, b -> abs(a - b) }.max()
    }

    /*
    *   Given a string, find out if it satisfies the IPv4 address naming rules.
    */
    private fun isIPv4Address(inputString: String): Boolean {
        var result : Boolean = true
        var splitIp = inputString.split('.')
        if (splitIp.size != LENGTH_SPLIT_IP_V4) {
            result = false
        }
        for (v in splitIp) {
            var num = v.toIntOrNull()
            if (num == null || (v.toInt() < 0 || v.toInt() > MAX_IP_VALUE)) {
                result = false
            } else if ((v.toInt() > 0 && v.getOrNull(0) == '0') || (v.toInt() == 0 && v.length > 1)) {
                result = false
            }
        }
        return result
    }


    /*
    *   Find the minimal length of the jump enough to avoid all the obstacles.
    */

    private fun avoidObstacles(inputArray: MutableList<Int>): Int {
        var max: Int = 0
        var result: Int = 1
        for (i in 0 until inputArray.size) {
            if (inputArray[i] > max) {
                max = inputArray[i]
            }
        }
        for (i in 0..max) {
            for (j in 0 until inputArray.size) {
                if (inputArray[j] % result == 0) {
                    result++
                }
            }
        }
        return result
    }

    /*
    *   Return the blurred image as an integer, with the fractions rounded down.
    */
    private fun boxBlur(image: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
        var xMax = image[0].size - 3
        var yMax = image.size - 3
        var sum = 0
        var result : MutableList<MutableList<Int>> = mutableListOf()
        for(i in 0..yMax){
            var x : MutableList<Int> = mutableListOf()
            for(j in 0..xMax){
                sum = image[i].subList(j,j+3).sum()+
                        image[i+1].subList(j,j+3).sum()+
                        image[i+2].subList(j,j+3).sum()
                x.add(sum/9)
            }
            result.add(x)
        }
        return result
    }


    /*
    *In the popular Minesweeper game you have a board with some mines and
    *  those cells that don't contain a mine have a number in it that indicates
    * the total number of mines in the neighboring cells. Starting off with some arrangement of
    *  mines we want to create a Minesweeper game setup.
    */
    private fun minesweeper(matrix: MutableList<MutableList<Boolean>>): MutableList<MutableList<Int>> {
        var result : MutableList<MutableList<Int>> = mutableListOf()
        print(matrix.size)
        print(matrix[0].size)
        for (i in matrix.indices){
            var array : MutableList<Int> = mutableListOf()
            for (j in matrix[0].indices){
                var count : Int = 0
                if(i>0 && matrix[i-1][j]){ count++ }
                if (i < matrix.size-1 && matrix[i+1][j]) { count++}
                if (j > 0){
                    if (matrix[i][j-1]){ count++ }
                    if (i > 0 && matrix[i-1][j-1]){ count++ }
                    if(i < matrix.size -1 && matrix [i+1][j-1]) { count++ }
                }
                if (j < matrix[0].size-1){
                    if (matrix[i][j+1]){ count++ }
                    if (i > 0 && matrix [i-1][j+1]){ count++ }
                    if(i < matrix.size -1 && matrix [i+1][j+1]) { count++ }
                }
                array.add(count)
            }
            result.add(array)
        }
        return result
    }
    @JvmStatic
    fun main(args: Array<String>) {
        val inputArray1 : MutableList<Int> = mutableListOf(-1, 4, 10, 3, -2)
        val inputArray2 : MutableList<Int> = mutableListOf(5, 3, 6, 7, 9)
        val string : String = "172.316.254.1"
        val boxBlur : MutableList<MutableList<Int>> = mutableListOf(mutableListOf(7,4,0,1), mutableListOf(5,6,2,2),
            mutableListOf(6,10,7,8), mutableListOf(1,4,2,0))
        val matrixBoolean : MutableList<MutableList<Boolean>> = mutableListOf(mutableListOf(true,false,false), mutableListOf(false,true,false),
            mutableListOf(false,false,false))
        println("19. You and yourfriend is equally strong ? ${areEquallyStrong(15,10,10,15)}")
        println("20. Maximal Adjacent Difference of $inputArray1 is ${arrayMaximalAdjacentDifference(inputArray1)} ")
        println("21. $string is a ip address ? ${isIPv4Address(string)}")
        println("22. min step to avoid elements in $inputArray2 is ${avoidObstacles(inputArray2)} ")
        println("23. Blur Image of $boxBlur is ${boxBlur(boxBlur)} ")
        println("24. MineSweeper of $matrixBoolean is ${minesweeper(matrixBoolean)}")
    }
}


