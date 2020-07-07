package com.asiantech.intern20summer1.kotlinIntro

class EgdeOfTheOcean {
    fun adjacentElementsProduct(inputArray: MutableList<Int>): Int {
//        Given an array of integers, find the pair of adjacent elements that has the
//        largest product and return that product.
        var max = inputArray.get(0) * inputArray.get(1)
        for (i in 1..inputArray.size - 2) {
            if (inputArray.get(i) * inputArray.get(i + 1) > max) {
                max = inputArray.get(i) * inputArray.get(i + 1);
            }
        }
        return max
    }

    //    Below we will define an n-interesting polygon. Your task is to find the area of a polygon for a given n.
//
//    A 1-interesting polygon is just a square with a side of length 1. An n-interesting polygon
//    is obtained by taking the n - 1-interesting polygon and appending 1-interesting polygons to its
//    rim, side by side. You can see the 1-, 2-, 3- and 4-interesting polygons in the picture below.
    fun shapeArea(n: Int): Int = n * n + (n - 1) * (n - 1)

    fun makeArrayConsecutive2(statues: MutableList<Int>): Int {
//        Ratiorg got statues of different sizes as a present from CodeMaster for his birthday,
//        each statue having an non-negative integer size. Since he likes to make things perfect,
//        he wants to arrange them from smallest to largest so that each statue will be bigger than
//        the previous one exactly by 1. He may need some additional statues to be able to accomplish
//        that. Help him figure out the minimum number of additional statues needed.
        var min = statues.get(0)
        var max = statues.get(0)
        for (i in 1..statues.size - 1) {
            if (min > statues.get(i)) {
                min = statues.get(i)
            }
            if (max < statues.get(i)) {
                max = statues.get(i)
            }
        }
        return max - min - statues.size + 1
        // return statues.max()!! - statues.min()!!- statues.size +1
    }

    fun almostIncreasingSequence(sequence: MutableList<Int>): Boolean {
//        Given a sequence of integers as an array, determine whether it is possible to obtain a
//        strictly increasing sequence by removing no more than one element from the array.
//
//        Note: sequence a0, a1, ..., an is considered to be a strictly increasing if a0 < a1 < ... < an.
//        Sequence containing only one element is also considered to be strictly increasing.
        var count = 0
        for (i in 1..sequence.size - 1) {
            if (sequence.get(i - 1) >= sequence.get(i)) {
                count++
                if (count > 1) {
                    return false
                }
                if (i != 1 && i + 1 != sequence.size && sequence.get(i - 1) >= sequence.get(i + 1) &&
                sequence.get(i - 2) >= sequence.get(i)
                ) {
                    return false
                }
            }
        }
        return true
    }
}

fun main(args: Array<String>) {
    val intL = mutableListOf(3, 6, -2, -5, 7, 3)
    var nah = EgdeOfTheOcean()
    println(nah.adjacentElementsProduct(intL))
    println(nah.shapeArea(3))
    println(nah.makeArrayConsecutive2(intL))
    println(nah.almostIncreasingSequence(intL))
}
