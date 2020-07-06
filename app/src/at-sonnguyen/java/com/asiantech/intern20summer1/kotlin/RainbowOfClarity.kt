package com.asiantech.intern20summer1.kotlin

import kotlin.math.abs

object RainbowOfClarity{
    /*
    *   Determine if the given character is a digit or not.
    */
    private fun isDigit(symbol: Char) = symbol.toString().matches("""[0-9]""".toRegex())
    /*
    *   Given a string, return its encoding
    */
    private fun lineEncoding(s: String): String {
        val sb = StringBuilder(s.length)
        var count = 0
        var cur = s.first()
        for (c in s) {
            if (c != cur) {
                if (count > 1) {
                    sb.append("$count")
                }
                sb.append("$cur")
                count = 0
                cur = c
            }
            count++
        }
        if (count > 1) {
            sb.append("$count")
        }
        sb.append(s.last())
        return sb.toString()
    }
    /*
    *   Given a position of a knight on the standard chessboard,
    *   find the number of different moves the knight can perform.
    */
    private fun chessKnight(cell: String): Int {
        var count : Int = 0
        var x : Int = cell.get(0).toInt() % 97
        println(x)
        var y : Int = cell.get(1) - '0' -1
        println(y)
        for (i in x-2..x+2){
            for ( j in y-2..y+2){
                if(i < 0 || j <0 ||i>7||j>7){
                    continue
                } else if ( (abs(x-i) ==1 && abs(j-y) ==2) || (abs(x-i) ==2 && abs(j-y) ==1) ){
                    count++
                }
            }
        }
        return count
    }
    /*
    *   Given some integer, find the maximal number you can obtain by deleting exactly one digit of the given number.
    */
    private fun deleteDigit(n: Int): Int {
        var stringOfN = n.toString()
        println(stringOfN)
        var max : Int = 0
        for(i in stringOfN.indices ){
            var builder = StringBuilder(stringOfN)
            builder.deleteCharAt(i)
            if(builder.toString().toInt()>max){
                max = builder.toString().toInt()
            }

        }
        return max
    }
    @JvmStatic
    fun main(args: Array<String>) {
        var symbol : Char = '0'
        val string = "aabbbc"
        val cell = "d5"
        val number = 1658542
        println("48. $symbol is a digit ? ${isDigit(symbol)}")
        println("49. encoding of $string is ${lineEncoding(string)}")
        println("50. cell number knight can perform from $cell is ${chessKnight(cell)}")
        println("51. max number you can gain is ${deleteDigit(number)}")
    }
}
