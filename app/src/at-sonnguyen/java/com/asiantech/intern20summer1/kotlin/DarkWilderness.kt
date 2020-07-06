package com.asiantech.intern20summer1.kotlin

object DarkWilderness{
    /*
    *  find how many days it'll take for the plant to reach this height.
    */
    private fun growingPlant(upSpeed: Int, downSpeed: Int, desiredHeight: Int): Int {
        var count = 0
        var height = 0
        while (height < desiredHeight){
            height += upSpeed
            count++
            if(height >= desiredHeight ){
                return count
            }
            height-= downSpeed
        }
        return count
    }
    /*
    *   the total maximum value of the items you can take with you
    */
    private fun knapsackLight(value1: Int, weight1: Int, value2: Int, weight2: Int, maxW: Int): Int {
        var higher  = 0
         when{
            value1 > value2 -> value1
            value1 < value2 -> value2
            value1 == value2 && weight1 > weight2 -> value2
            else -> value1
        }
        when {
            maxW >= weight1 + weight2 -> return value1+ value2
            maxW in weight1..weight2 -> return value1
            maxW in weight2..weight1 -> return value2
            maxW >= weight1 && maxW >= weight2 -> return higher
            else ->  return 0

        }
    }
    /*
    *   Given a string, output its longest prefix which contains only digits.
    */
    private fun longestDigitsPrefix(inputString: String): String {
        return  inputString.takeWhile { it.isDigit() }
    }
    /*
    *   Given an integer, find its digit degree.
    */
    private fun digitDegree(n: Int): Int {
        var copyN = n
        var count : Int = 0
        while( copyN/10 > 0){
            copyN = sum(copyN)
            count++
        }
        return count
    }
    private fun sum(n : Int ) : Int{
        var copyN : Int = n
        var Sum : Int = 0
        while (copyN!= 0){
            Sum += copyN%10
            copyN/=10
        }
        return Sum
    }
    /*
    *   the example the bishop can move
    */
    private fun bishopAndPawn(bishop: String, pawn: String): Boolean = ((bishop[0].toChar().toInt()+bishop[1].toChar().toInt()) == (pawn[0].toChar().toInt() +pawn[1].toChar().toInt()) || (bishop[0].toChar().toInt() - bishop[1].toChar().toInt()) == (pawn[0].toChar().toInt() - pawn[1].toChar().toInt()) )

    @JvmStatic
    fun main(args: Array<String>) {
        val inputString : String = "123aa1"
        val number = 5698532
        println("38. spend ${growingPlant(15,2,100)} day to tree gain 100 ")
        println("39. max value can bring out is ${knapsackLight(10,5,6,4,8)}")
        println("40. Longest Digits Prefix in $inputString is ${longestDigitsPrefix(inputString)}")
        println("41. Result Digit Degree of $number = ${digitDegree(number)}")
        println("42. can move from A1 to C3  ? ${bishopAndPawn("A1","C3")} ")
    }
}
