package com.asiantech.intern20summer1.kotlin


import java.util.*


object DivingDeeper {
    /*
    *   Given array of integers, remove each kth element from it.
    */
    private fun extractEachKth(inputArray: MutableList<Int>, k: Int): MutableList<Int> {
        var result : MutableList<Int> = mutableListOf()
        for (i in 0 until inputArray.size){
            if ( (i+1)%k !=0) {
                result.add(inputArray[i])
            }
        }
        return result
    }
    /*
    *   Find the leftmost digit that occurs in a given string.
    */
    private fun firstDigit(inputString: String): Char {
        for(i in inputString.indices){
            if (inputString[i].toChar() in '0'..'9') {
                return inputString[i].toChar()
            }
        }
        return '0'
    }
    /*
    *   Given a string, find the number of different characters in it.
    */
    private fun differentSymbolsNaive(s: String): Int {
        var listOfChar : MutableList<Char> = mutableListOf()
        for(i in s.indices){
            if(listOfChar.contains(s[i].toChar())){
                continue
            }
            listOfChar.add(s[i].toChar())
        }
        return listOfChar.size
    }
    /*
    *   Given array of integers, find the maximal possible sum of some of its k consecutive elements.
    */
    private fun arrayMaxConsecutiveSum(inputArray: MutableList<Int>, k: Int): Int {
        var temp : Int = 0
        var result : Int = 0
        for (i in 0..k-2){
            temp += inputArray[i]
        }
        for(i in (k-1) until inputArray.size) {
            temp += inputArray[i]
            if(temp > result){
                result = temp
            }
            temp -= inputArray[i-k+1]
        }
        return result
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val inputArray : MutableList<Int> = mutableListOf(1, 2, 5, 2, 6, 7, 1, 2)
        val string : String ="var_1__Int"
        println("34. after remove element Kth of $inputArray is ${extractEachKth(inputArray,2)}")
        println("35. first Digits of $string is ${firstDigit(string)} ")
        println("36. Defference Symbol in $string is ${differentSymbolsNaive(string)}")
        println("37. Max Sum of k value in $inputArray is ${arrayMaxConsecutiveSum(inputArray,3)}")
    }
}
