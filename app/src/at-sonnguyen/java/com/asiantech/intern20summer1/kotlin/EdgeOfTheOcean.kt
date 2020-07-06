package com.asiantech.intern20summer1.kotlin

object EdgeOfTheOcean {
    const val SHAPE = 5

    /*
    *   Given an array of integers,
    *   find the pair of adjacent elements that has the largest product and return that product
    */
    private fun adjacentElementsProduct(inputArray: MutableList<Int>): Int {
        var lengthOfArray = inputArray.size
        var max : Int = inputArray[0]*inputArray[1]
        for (index in 0 until lengthOfArray-1){
            if(inputArray[index]*inputArray[index+1] > max){
                max = inputArray[index]*inputArray[index+1]
            }
        }
        return max
    }
    /*
    *   return the area of a polygon for a given n
    */
    private fun shapeArea(n: Int): Int = n*n + (n-1)*(n-1)
    /*
    *   the minimum number of additional statues needed.
    */
    private fun makeArrayConsecutive2(statues: MutableList<Int>): Int {
        statues.sort()
        return statues[statues.size-1] - statues[0] - statues.size +1
    }
    /*
    *   Given a sequence of integers as an array,
    * determine whether it is possible to obtain a strictly increasing sequence
    * by removing no more than one element from the array
    */
    private fun almostIncreasingSequence(sequence: MutableList<Int>): Boolean {
        var errorCountOne = 0
        var errorCountTwo = 0

        for(i in 0..sequence.size - 2)
        {
            if(sequence[i] >= sequence[i + 1])
                errorCountOne++

            if(i != 0)
            {
                if(sequence[i - 1] >= sequence[i + 1])
                    errorCountTwo++
            }
        }

        return errorCountOne == 1 && errorCountTwo <= 1
    }
    /*
    * return the total sum of all rooms that are suitable for the CodeBots
    */
    private fun matrixElementsSum(matrix: MutableList<MutableList<Int>>): Int {
        var sum : Int = 0
        for(i in 0 until matrix.size){
            for (j in 0 until matrix[0].size){
                if (matrix[i][j]==0){
                    for (k in i until matrix.size){
                        matrix[k][j] =0
                    }
                    continue
                }else sum += matrix[i][j]

            }
        }
        return sum
    }

    @JvmStatic
    fun main(args: Array<String>) {
        var inputMatrix : MutableList<MutableList<Int>> = mutableListOf(mutableListOf(0,1,1,2), mutableListOf(0,5,0,0),
            mutableListOf(2,0,3,3) )
        var inputArray : MutableList<Int> = mutableListOf(3, 6, -2, -5, 7, 3)
        var n = SHAPE
        println("4. ${adjacentElementsProduct(inputArray)}")
        println("5. Shape Area of $n = ${shapeArea(n)}")
        println("6. minimum add number = ${makeArrayConsecutive2(inputArray)} ")
        println("7. $inputArray is almost increasing Array ? ${almostIncreasingSequence(inputArray)}")
        println("8. max of Element Sum in $inputMatrix = ${matrixElementsSum(inputMatrix)}")
    }
}
