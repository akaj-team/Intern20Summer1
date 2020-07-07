package com.asiantech.intern20summer1.kotlinIntro

class ThroughTheFog {
    fun circleOfNumbers(n: Int, firstNumber: Int): Int {
//        Consider integer numbers from 0 to n - 1 written down along the circle in such a way
//       that the distance between any two neighboring numbers is equal (note that 0 and n - 1
//        are neighboring, too).
//        Given n and firstNumber, find the number which is written in the radially opposite position
//        to firstNumber.
        if(n/2>firstNumber){
            return n/2 + firstNumber
        }
        return firstNumber -n/2
    }
    fun depositProfit(deposit: Int, rate: Int, threshold: Int): Int {
//        You have deposited a specific amount of money into your bank account. Each year your
//                balance increases at the same growth rate. With the assumption that you
//        don't make any additional deposits, find out how long it would take for your balance to
//        pass a specific threshold.
        var count = 0
        var n:Double = deposit.toDouble()
        while(n<threshold){
            n += n*rate/100
            count++
        }
        return count
    }
    fun absoluteValuesSumMinimization(a: MutableList<Int>): Int {
//        Given a sorted array of integers a, your task is to determine which element of a is closest
//        to all other values of a. In other words, find the element x in a, which minimizes the following sum:
        return a[(a.size-1)/2]
    }

}

fun main(args: Array<String>) {
    val nah = ThroughTheFog()
    val intArray = mutableListOf(2,5,7,8,9)
    println(nah.circleOfNumbers(2,6))
    println(nah.depositProfit(100, 20, 1000))
    println(nah.absoluteValuesSumMinimization(intArray))
}