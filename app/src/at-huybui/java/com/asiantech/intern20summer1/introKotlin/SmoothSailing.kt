package com.asiantech.intern20summer1.introKotlin

class SmoothSailing {

    /** 9 **
     * Given an array of strings, return another array containing all of its longest strings.
     */
    fun allLongestStrings(inputArray: MutableList<String>): MutableList<String> {
        var lengMax = 0;
        inputArray.forEach {
            when {
                it.length > lengMax -> lengMax = it.length
            }
        }
        return inputArray.filter { it.length == lengMax } as MutableList<String>
    }
}