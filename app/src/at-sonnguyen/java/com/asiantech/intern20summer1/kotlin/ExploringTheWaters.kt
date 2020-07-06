package com.asiantech.intern20summer1.kotlin

object exploringTheWaters {
    /*
    *   Return an array of two integers,
    *   where the first element is the total weight of team 1,
    *   and the second element is the total weight of team 2
    */
    private fun alternatingSums(a: MutableList<Int>): MutableList<Int> {
        var sum0 = 0
        var sum1 = 0
        for (i in 0..a.size-1){
            if (i%2==0){
                sum0 += a[i]
            }else sum1 += a[i]
        }
        return mutableListOf(sum0,sum1)
    }
    /*
    * Given a rectangular matrix of characters, add a border of asterisks(*) to it.
    */
    private fun addBorder(picture: MutableList<String>): MutableList<String> {
        val returnMatrix = mutableListOf<String>()
        val length = picture[0].length

        var borderRow = ""
        for (i in 1..length + 2) {
            borderRow+="*"
        }

        returnMatrix.add(borderRow)

        for (row in picture) {
            returnMatrix.add("*$row*")
        }

        returnMatrix.add(borderRow)

        return returnMatrix
    }
    /*
    *   Given two arrays a and b, check whether they are similar.
    */
    private fun areSimilar(a: MutableList<Int>, b: MutableList<Int>): Boolean {
        var diff = mutableListOf(-1,-1)
        var times = 0
        var similar = false
        for(i in a.indices){
            if(a[i] != b[i]){
                if(diff[0] != -1){
                    if( (diff[0] == b[i] && diff[1] == a[i])) {
                        similar = true
                        diff[0] = -1
                        continue
                    }
                }
                diff[0] = a[i]
                diff[1] = b[i]
                times++
                if(times > 1){
                    return false
                }
            }
            else
                similar = true

        }
        return similar
    }
    /*
    *   Find the minimal number of moves required to obtain a strictly increasing sequence from the input.
    */
    private fun arrayChange(inputArray: MutableList<Int>): Int {
        var count : Int = 0
        for(i in 0..inputArray.size-2){
            while ( inputArray[i] >= inputArray[i+1] ){
                inputArray[i+1]++
                count++
            }
        }
        return count
    }
    /*
    *   Given a string, find out if its characters can be rearranged to form a palindrome.
    */
    private fun palindromeRearranging(inputString: String): Boolean {
        var z = 0
        for(v in inputString.toList().distinct()){
            var t = inputString.count{ it == v }
            if(t.rem(2) != 0){
                z++
            }
        }
        return z<2
    }
    @JvmStatic
    fun main(args: Array<String>) {
        val picture : MutableList<String> = mutableListOf("abc","ded")
        val a : MutableList<Int> = mutableListOf(50, 60, 60, 45, 70)
        val inputArray1 : MutableList<Int> = mutableListOf(1, 2, 3)
        val inputArray2 : MutableList<Int> = mutableListOf(2, 1, 3)
        val string : String = "zyyzzzzz"
        println("14. sum of 2 teams in $a is ${alternatingSums(a)} ")
        println("15. add border of $picture is ${addBorder(picture)} ")
        println("16. $inputArray1 and $inputArray2 are similars ? ${areSimilar(inputArray1,inputArray2)}")
        println("17. $inputArray2 need ${arrayChange(inputArray2)} changes to become a increasing Array")
        println("18. $string can be rearranged to be come a palinedrome ? ${palindromeRearranging(string)} ")
    }

}
