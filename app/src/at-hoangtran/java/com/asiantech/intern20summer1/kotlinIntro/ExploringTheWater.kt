package com.asiantech.intern20summer1.kotlinIntro

class ExploringTheWater {
    fun alternatingSums(a: MutableList<Int>): MutableList<Int> {
//        Several people are standing in a row and need to be divided into two teams.
//        The first person goes into team 1, the second goes into team 2, the third goes into team 1 again,
//        the fourth into team 2, and so on.
//
//        You are given an array of positive integers - the weights of the people.
//        Return an array of two integers, where the first element is the total weight of team 1,
//        and the second element is the total weight of team 2 after the division is complete.
        var sum: MutableList<Int> = mutableListOf()
        var odd = 0
        var even = 0
        for (i in 0..a.size - 1) {
            if (i % 2 == 0) {
                even += a[i]
            } else {
                odd += a[i]
            }
        }
        sum.add(even)
        sum.add(odd)
        return sum
    }

    fun addBorder(picture: MutableList<String>): MutableList<String> {
//        Given a rectangular matrix of characters, add a border of asterisks(*) to it.
        var s: MutableList<String> = mutableListOf()
        val l = picture[0].length
        s.add(0, "*".repeat(l + 2))
        for (i in 0..picture.size - 1) {
            s.add(i + 1, "*" + picture[i] + "*")
        }
        s.add("*".repeat(l + 2))
        return s
    }

    fun areSimilar(a: MutableList<Int>, b: MutableList<Int>): Boolean {
//        Two arrays are called similar if one can be obtained from another by swapping at most
//                one pair of elements in one of the arrays.
//
//        Given two arrays a and b, check whether they are similar.
        var count = 0
        var swapcount = 0
        for (i in 0..a.size - 1) {
            if (a[i] == b[i]) {
                count++
            } else {
                for (j in i + 1..a.size - 1) {
                    if (a[j] == b[i] && a[i] == b[j] && a[j] != b[j]) {
                        swapcount++
                        var tmp = a[i]
                        a[i] = a[j]
                        a[j] = tmp
                        println(i)
                        println(j)
                    }
                }
            }
        }
        return count + swapcount == a.size && swapcount < 2
    }

    fun arrayChange(inputArray: MutableList<Int>): Int {
//        You are given an array of integers. On each move you are allowed to increase exactly one
//                of its element by one. Find the minimal number of moves required to obtain a strictly
//        increasing sequence from the input.
        var count = 0
        for (i in 1..inputArray.size - 1) {
            if (inputArray[i] <= inputArray[i - 1]) {
                count += inputArray[i - 1] - inputArray[i] + 1
                inputArray[i] = inputArray[i - 1] + 1
            }
        }
        return count
    }

    fun palindromeRearranging(inputString: String): Boolean {
//        Given a string, find out if its characters can be rearranged to form a palindrome.
        var alone = 0
        for (i in 0..inputString.length - 1) {
            var count = 0
            for (j in 0..inputString.length - 1) {
                if (inputString[j] == inputString[i]) {
                    count++
                }
            }
            if (count % 2 == 1 && inputString.length % 2 == 0) {
                return false
            }
            if (count == 1) {
                alone++
            }
        }
        return (alone <= 1)
    }

}

fun main(args: Array<String>) {
    val nah = ExploringTheWater()
    val s = mutableListOf<String>("abc", "ded")
    val sum = mutableListOf<Int>(50, 60, 60, 45, 70)
    val sum2 = mutableListOf<Int>(80, 60, 60, 45, 70)
    println(nah.alternatingSums(sum))
    println(nah.addBorder(s))
    println(nah.areSimilar(sum, sum2))
    println(nah.arrayChange(sum))
    println(nah.palindromeRearranging("aabb"))
}
