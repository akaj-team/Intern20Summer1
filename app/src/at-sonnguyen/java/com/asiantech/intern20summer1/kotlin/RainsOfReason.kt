package com.asiantech.intern20summer1.kotlin

object RainsOfReason{
    private const val TEN = 10
    /*
    *   Given an array of integers, replace all the occurrences of elemToReplace with substitutionElem.
    */
    private fun arrayReplace(inputArray: MutableList<Int>, elemToReplace: Int, substitutionElem: Int): MutableList<Int> {
        for(i in inputArray.indices){
            if (inputArray[i] == elemToReplace ){
                inputArray[i] = substitutionElem
            }
        }

        return inputArray
    }
    /*
    * Check if all digits of the given integer are even.
    */
    private fun evenDigitsOnly(n: Int): Boolean {
        var copyN : Int = n
        while(copyN > 0){
            if(copyN % 2 != 0 ){
                return false
            }
            copyN/= TEN
        }
        return true
    }
    /*
    *   Check if the given string is a correct variable name.
    */
    private fun variableName(name: String): Boolean {
        return """([a-zA-Z]|_)+(\w|_)*""".toRegex().matches(name)
    }
    /*
    *   Given a string, your task is to replace each of its characters
    *   by the next one in the English alphabet
    */
    private fun alphabeticShift(inputString: String): String {
        var resultString : String = ""
        for (i in inputString.indices){

            if (inputString.get(i)+1 > 'z'){
                resultString += "a"
            }else resultString += (inputString.get(i)+1)
        }
        return resultString
    }
    /*
    *   Given two cells on the standard chess board, determine whether they have the same color or not.
    */
    private fun chessBoardCellColor(cell1: String, cell2: String): Boolean =
        (cell1[0].toInt()+cell1[1].toInt())%2 == (cell2[0].toInt()+cell2[1].toInt())%2
    @JvmStatic
    fun main(args: Array<String>) {
        val inputArray : MutableList<Int> = mutableListOf(1, 2, 1)
        //val n : Int = 268462
        val string = "variable0"
        val cell1 = "A1"
        val cell2 = "H2"
        println("25. Replace all element = 1 to element = 3 in $inputArray is ${arrayReplace(inputArray,1,3)} ")
        println("26.  is only even Digits ? ${evenDigitsOnly(268462)} ")
        println("27. $string is a variable name ? ${variableName(string)} ")
        println("28. $string after shift a value is ${alphabeticShift(string)}")
        println("29. $cell1 and $cell2 are same color ? ${chessBoardCellColor(cell1,cell2)} ")
    }
}
