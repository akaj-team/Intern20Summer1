package com.asiantech.intern20summer1.intro.kotlin

object EruptionOfLight {
    @JvmStatic
    fun main(args: Array<String>) {
        //RUN main() with Coverage
        println("Ex 43: " + isBeautifulString("bbbaacdafe"))

        println("Ex 44: " + findEmailDomain("prettyandsimple@example.com"))

        println("Ex 45: " + buildPalindrome("abcdc"))

        println("Ex 46: " + electionsWinners(mutableListOf(2, 3, 5, 2), 3))

        println("Ex 47: " + isMAC48Address("00-1B-63-84-45-E6"))
    }

    private const val SIZE_ALPHABET = 26
    private const val TRANSFER_RANGE = 97

    private fun isBeautifulString(inputString: String): Boolean {
        /**
         *  string is said to be beautiful if each letter in the string appears at most as
         *  many times as the previous letter in the alphabet within the string; ie:
         *  b occurs no more times than a; c occurs no more times than b; etc.

        Given a string, check whether it is beautiful.
         */
        val numLetters = IntArray(SIZE_ALPHABET)
        for (letter in inputString) {
            val i = letter.toInt() - TRANSFER_RANGE
            numLetters[i]++
        }

        var isBeautiful = true
        for (i in 1 until numLetters.size) {
            isBeautiful = isBeautiful && numLetters[i] <= numLetters[i - 1]
        }

        return isBeautiful
    }

    private fun findEmailDomain(address: String): String? {
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

    private fun buildPalindrome(a: String): String {
        /**
         * Given a string, find the shortest possible string which can be achieved by
         * adding characters to the end of initial string to make it a palindrome.
         */
        var aReverse = a
        aReverse = aReverse.reversed()
        if (aReverse == a) return a
        var insertStr = ""
        var reverse = ""
        for (i in a) {
            insertStr = i.toString() + insertStr
            reverse = ""
            reverse = (a + insertStr).reversed()
            if (reverse == a + insertStr) break
        }
        return reverse
    }

    private fun electionsWinners(votes: MutableList<Int>, k: Int): Int {
        /**
         * Elections are in progress!
        Given an array of the numbers of votes given to each of the candidates so far,
        and an integer k equal to the number of voters who haven't cast their vote yet,
        find the number of candidates who still have a chance to win the election.

        The winner of the election must secure strictly more votes than any other candidate.
        If two or more candidates receive the same (maximum) number of votes, assume there is no winner at all.
         */
        val max = votes.max()
        val e = votes.filter { it + k > max ?: 0 || it == max }

        return when (k) {
            0 -> e.filter { cur -> e.count { it == cur } == 1 }.size
            else -> e.size
        }
    }

    private fun isMAC48Address(a: String): Boolean {
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
