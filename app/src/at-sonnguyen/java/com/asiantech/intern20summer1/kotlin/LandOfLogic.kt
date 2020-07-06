package com.asiantech.intern20summer1.kotlin

object LandOfLogic {
    /*
    *   Define a word as a sequence of consecutive English letters. Find the longest word from the given string.
    */
    private fun longestWord(text: String) =text.split(Regex("[^a-zA-Z]")).maxBy { it.length }
    /*
    *   Check if the given string is a correct time representation of the 24-hour clock.
    */
    private fun validTime(time: String): Boolean {
        val split = time.split(":")
        val hr = split[0].toInt()
        val min = split[1].toInt()
        return hr in 0..23 && min in 0..59
    }
    /*
    *   returns the sum of numbers that appear in the given inputString
    */
    private fun sumUpNumbers(inputString: String): Int {
        val list = mutableListOf<Int>()
        var temp = "0"
        inputString.forEach {
            if (it.isDigit()) {
                temp += it
            } else {
                list.add(temp.toInt())
                temp = "0"
            }
        }

        list.add(temp.toInt())
        return list.sum()
    }
    /*
    *   Given a rectangular matrix containing only digits, calculate the number of different 2 × 2 squares in it.
    */
    private fun differentSquares(matrix: MutableList<MutableList<Int>>): Int {
        val set = mutableSetOf<String>()
        for (i in 0..matrix.size - 2) {
            for (j in 0..matrix.first().size - 2) {
                set.add(
                    "${matrix[i][j]}" +
                            "${matrix[i + 1][j]}" +
                            "${matrix[i][j + 1]}" +
                            "${matrix[i + 1][j + 1]}"
                )
            }
        }
        return set.size
    }
    /*
    *   Given an integer product, find the smallest positive integer the product
    * of whose digits is equal to product. If there is no such integer, return -1 instead
    */
    private fun digitsProduct(product: Int): Int {
        if ( product == 0){
            return 10
        }
        if (product == 1){
            return 1
        }
        var result : String = ""
        var copyProduct : Int = product
        for ( number in 9 downTo 2 ){
            while ( copyProduct % number == 0){
                copyProduct /= number
                result = number.toString() + result
            }
        }
        if (copyProduct > 1){
            return -1
        }
        return Integer.parseInt(result)
    }
    /*
    *   Return an array of names that will be given to the files.
    */
    private fun fileNaming(names: MutableList<String>): MutableList<String> {
        val namesList = mutableListOf<String>()
        names.forEach{
            var count : Int = 0
            var name = it
            while (name in namesList){
                count++;
                name = "$it($count)"
            }
            namesList.add(name)
        }
        return namesList
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val text : String = "Ready[[[, steady, go!"
        println("52. longest word from $text is ${longestWord(text)}")
        val time : String = "13:58"
        println("53. $time is a valid time ? ${validTime(time)}")
        val  string2 = "2 apples, 12 oranges"
        println("54. Result of $string2 is ${sumUpNumbers(string2)}")
        val maxtrix :MutableList<MutableList<Int>> = mutableListOf(mutableListOf(1,2,1), mutableListOf(2,2,2), mutableListOf(2,2,2),
            mutableListOf(1,2,3),
            mutableListOf(2,2,1))
        println("55. number of difference 2*2 squares of $maxtrix is ${differentSquares(maxtrix)}  ")
        val product = 12
        println("56. smallest product number have whose digits is equal to $product is ${digitsProduct(product)} ")
        val names : MutableList<String> = mutableListOf("doc", "doc", "image", "doc(1)", "doc")
        println("57. result filenames of $names is ${fileNaming(names)}")

    }
}