package com.asiantech.intern20summer1.kotlin

class ExploringTheWaters {
    fun alternatingSums(a: MutableList<Int>): MutableList<Int> {
        val array = IntArray(2)
        for (i in 0 until a.size) {
            if ((i + 1) % 2 != 0) {
                array[0] += a[i]
            } else {
                array[1] += a[i]
            }
        }
        return array.toMutableList()
    }

    fun addBorder(picture: MutableList<String>): MutableList<String> {
        val string = Array(picture.size + 2) { "" }
        var max = 0
        for (i in 0 until picture.size) {
            string[i + 1] = "*" + picture[i] + "*"
            max = maxOf(string[i + 1].length, max)
        }
        string[0] = ""
        string[picture.size + 1] = ""
        for (i in 0 until max) {
            string[0] += "*"
            string[picture.size + 1] += "*"
        }
        return string.toMutableList()
    }

    fun areSimilar(a: MutableList<Int>, b: MutableList<Int>): Boolean {
        var count = 0
        var s1 = 1
        var s2 = 1
        if (a.size != b.size) {
            return false
        } else {
            for (i in 0 until a.size) {
                if (a[i] != b[i]) {
                    count++
                }
                s1 *= a[i]
                s2 *= b[i]
            }
        }
        return count <= 2 && s1 == s2
    }

    fun arrayChange(inputArray: MutableList<Int>): Int {
        var sum = 0
        for (i in 0 until inputArray.size - 1) {
            if (inputArray[i] >= inputArray[i + 1]) {
                sum = sum + inputArray[i] + 1 - inputArray[i + 1]
                inputArray[i + 1] = inputArray[i] + 1
            }
        }
        return sum
    }

    fun palindromeRearranging(inputString: String): Boolean {
        var count: Int
        var check = '.'
        var temp: Char
        var flag = false
        for (i in inputString.indices) {
            temp = inputString[i]
            count = 0
            for (j in inputString.indices) {
                if (temp == inputString[j]) {
                    count++
                }
            }
            if (count % 2 == 1) {
                if (flag && check != temp) {
                    return false
                } else {
                    check = temp
                    flag = true
                }
            }
        }
        return true
    }
}
