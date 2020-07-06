package com.asiantech.intern20summer1.introKotlin

import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
fun main() {
    //RUN main() with Coverage
    var obj: EruptionOfLight = EruptionOfLight()
    println("Ex 43: " + obj.isBeautifulString("bbbaacdafe"))

    println("Ex 44: " + obj.findEmailDomain("prettyandsimple@example.com"))

    println("Ex 45: " + obj.buildPalindrome("abcdc"))

    println("Ex 46: " + obj.electionsWinners(mutableListOf(2, 3, 5, 2), 3))

    println("Ex 47: " + obj.isMAC48Address("00-1B-63-84-45-E6"))
}

class EruptionOfLight {
    @RequiresApi(Build.VERSION_CODES.N)
    fun isBeautifulString(inputString: String): Boolean {
        /**
         *  string is said to be beautiful if each letter in the string appears at
         *  most as many times as the previous letter in the alphabet within the string;
         *  ie: b occurs no more times than a; c occurs no more times than b; etc.

        Given a string, check whether it is beautiful.
         */
        val sortedMap = inputString.groupingBy { it }.eachCount().toSortedMap()
        ('a'..'z').forEach { sortedMap.putIfAbsent(it, 0) }
        val sortedValues = sortedMap.values.toList()
        return (0 until sortedValues.size - 1).all { sortedValues[it] >= sortedValues[it + 1] }
    }

    fun findEmailDomain(address: String): String? {
        /**
         * An email address such as "John.Smith@example.com" is made up of a local part ("John.Smith"),
         * an "@" symbol, then a domain part ("example.com").

        The domain name part of an email address may only consist of letters, digits, hyphens and dots.
        The local part, however, also allows a lot of different special characters.
        Here you can look at several examples of correct and incorrect email addresses.

        Given a valid email address, find its domain part.
         */
        val lastIndex = address.lastIndexOf("@")
        return address.substring(lastIndex + 1)
    }

    fun buildPalindrome(a: String): String? {
        /**
         * Given a string, find the shortest possible string which can be achieved by
         * adding characters to the end of initial string to make it a palindrome.
         */
        var aReverse = a
        aReverse = aReverse.reversed()
        if (aReverse.equals(a)) {
            return a
        } else {
            var insertStr = ""
            for (i in 0 until a.length) {
                val newCh = a[i]
                insertStr = newCh.toString() + insertStr
                var reverse = a + insertStr
                reverse = reverse.reversed()
                if (reverse.equals(a + insertStr)) return reverse
            }
        }
        return null
    }

    fun electionsWinners(votes: MutableList<Int>, k: Int): Int {
        /**
         * Elections are in progress!
        Given an array of the numbers of votes given to each of the candidates so far,
        and an integer k equal to the number of voters who haven't cast their vote yet,
        find the number of candidates who still have a chance to win the election.

        The winner of the election must secure strictly more votes than any other candidate.
        If two or more candidates receive the same (maximum) number of votes, assume there is no winner at all.
         */
        if (k == 0) {
            var moreOne = 1
            var max = 0
            for (item in votes) {
                if (item > max) {
                    max = item
                    moreOne = 0
                }
                if (item == max) moreOne++
            }
            return if (moreOne > 1) 0 else 1
        }
        var count = 0
        var voteCanWin = 0
        for (item in votes) {
            if (item > voteCanWin) voteCanWin = item
        }
        voteCanWin++
        for (item in votes) {
            if (item + k >= voteCanWin) count++
        }
        return count
    }

    fun isMAC48Address(a: String): Boolean {
        /**
         * A media access control address (MAC address) is a unique identifier assigned to
         * network interfaces for communications on the physical network segment.

        The standard (IEEE 802) format for printing MAC-48 addresses in human-friendly form
        is six groups of two hexadecimal digits (0 to 9 or A to F), separated by hyphens (e.g. 01-23-45-67-89-AB).

        Your task is to check by given string inputString whether it corresponds to MAC-48 address or not.
         */
        return a.matches("[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}".toRegex())
    }
}