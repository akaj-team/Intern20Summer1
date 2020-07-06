package com.asiantech.intern20summer1.kotlin

object smoothSailing {
    /*
    *Given an array of strings, return another array containing all of its longest strings.
    */
    private fun allLongestStrings(inputArray: MutableList<String>): MutableList<String> {
        var maxLength : Int = inputArray[0].length
        var listResult = mutableListOf<String>()
        for(i in 0..inputArray.size-1){
            if (inputArray[i].length > maxLength ){
                maxLength = inputArray[i].length
            }
        }
        for ( i in 0 until inputArray.size){
            if (inputArray[i].length==maxLength){
                listResult.add(inputArray[i])
            }
        }
        return listResult
    }
    /*
    *Given two strings, find the number of common characters between them.
    */
    private fun commonCharacterCount(s1: String, s2: String): Int {
        var arrayList = ArrayList<String>()
        var count : Int = 0
        for (i in 0..s1.length-1){
            for (j in 0..s2.length-1){
                if(!arrayList.contains("$j")&& s1.get(i) ==s2.get(j)){
                    print(j)
                    arrayList.add("$j")
                    count++
                    break
                }
            }
        }
        return count
    }
    /*
    * Given a ticket number n, determine if it's lucky or not.
    */
    fun isLucky(n: Int): Boolean {
        var string : String = n.toString()
        var sum1 : Int = 0;
        var sum2 : Int = 0;
        for ( i in string.indices){
            if ( i < string.length/2 ){
                sum1 += string[i].toInt()
            }else {
                sum2 += string[i].toInt()
            }

        }
        if (sum1==sum2){
            return true
        }
        return false
    }
    /*
    * rearrange the people by their heights in a non-descending order without moving the trees
    */
    fun sortByHeight(a: MutableList<Int>): MutableList<Int> {
        var temp : Int = 0
        for (i in 0..a.size-1){
            for (j in i+1..a.size-1){
                if (a[i] > a[j] &&a[i]!= -1 && a[j]!= -1 ){
                    temp = a[i]
                    a[i] = a[j]
                    a[j] = temp
                }
            }
        }
        return a
    }
    /*
    *   reverses characters in (possibly nested) parentheses in the input string
    */
    private fun reverseInParentheses(inputString: String): String {
        var begin = 0
        val end: Int
        for (i in inputString.indices) {
            if (inputString[i] == '(')
                begin = i
            if (inputString[i] == ')') {
                end = i
                val temp = inputString.substring(begin + 1, end)
                val beginStr = inputString.substring(0, begin)
                val endStr = inputString.substring(end + 1)
                val toContinue = beginStr + temp.reversed() + endStr
                return reverseInParentheses(toContinue)
            }
        }
        return inputString
    }
    @JvmStatic
    fun main(args: Array<String>) {
        var inputArray : MutableList<String> = mutableListOf("aba", "aa", "ad","vcd","aba")
        val s1 : String = "aabcc"
        val  s2 : String = "adcaa"
        val string : String = "foo(bar)baz"
        val n = 134008
        var a : MutableList<Int> = mutableListOf(-1, 150, 190, 170, -1, -1, 160, 180)
        println("9. longest String in $inputArray is : ${allLongestStrings(inputArray)}")
        println(" 10. common Character of $s1 and $s2 = ${commonCharacterCount(s1,s2)}")
        println("11. $n is a lucky number? ${isLucky(n)} ")
        println("12. sort by height of $a is ${sortByHeight(a)}")
        println("13. reserve In Parenthese of $string is ${reverseInParentheses(string)}")
    }

}