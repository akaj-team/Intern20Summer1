package com.asiantech.intern20summer1.introKotlin

class ExploringtheWaters {

    /** 14 **
     *     You are given an array of positive integers - the weights of the people. Return an array of two integers,
     *     where the first element is the total weight of team 1, and the second element is the total
     *     weight of team 2 after the division is complete.
     */
    fun alternatingSums(a: MutableList<Int>): MutableList<Int> {
        var mOutPut = mutableListOf<Int>()
        mOutPut.add(a.filterIndexed { index, i -> index % 2 == 0 }.sum())
        mOutPut.add(a.filterIndexed { index, i -> index % 2 == 1 }.sum())
        return mOutPut
    }

    /** 15 **
     *G iven a rectangular matrix of characters, add a border of asterisks(*) to it.
     */
    fun addBorder(picture: MutableList<String>): MutableList<String> {
        var lengElement = picture[0].length
        var st = ""
        for (i in 1..lengElement + 2) { // tạo cạnh ********
            st += "*"
        }
        picture.add(picture.size, st)  // thêm cạnh trên và dưới
        picture.add(0, st)
        picture.forEach {
            when {
                it.length < lengElement + 2 -> { // thêm cạnh 2 bên
                    picture[picture.indexOf(it)] = "*" + picture[picture.indexOf(it)] + "*"
                }
            }
        }
        return picture
    }

    /** 16
     * Two arrays are called similar if one can be obtained from another by swapping at most
     * one pair of elements in one of the arrays.
     * Given two arrays a and b, check whether they are similar.
     */
    fun areSimilar(a: MutableList<Int>, b: MutableList<Int>): Boolean {
        a.forEachIndexed { indexA1, iA1 ->  // quet a bằng  2 vòng foreach lồng để hoán giá trị của A cho nhau
            if (indexA1 < a.size) {
                a.forEachIndexed { indexA2, iA2 ->
                    if (indexA2 < a.size) {
                        var bufArray = a.toMutableList()  // tạo mảng buf để thay đổi giá trị
                        bufArray[indexA1] = iA2          // hoán đổi giá trị
                        bufArray[indexA2] = iA1
                        if (bufArray == b) {  // trả về true nếu như buf bằng b
                            return true
                        }
                    }
                }
            }
        }
        return false
    }


}