package com.asiantech.intern20summer1.kotlinIntro

class EruptionofLight {
    fun isBeautifulString(inputString: String): Boolean {
        var tmp = mutableListOf<Int>()
        for (i in 'a'..'z') {
            var count = 0
            for (j in 0..inputString.length - 1) {
                if (inputString[j] == i) {
                    count++
                }
            }
            tmp.add(count)
        }
        for (i in 0..tmp.size - 2) {
            if (tmp[i] < tmp[i + 1])
                return false
        }
        return true
    }

    fun findEmailDomain(address: String): String {
//        An email address such as "John.Smith@example.com" is made up of a local part ("John.Smith"),
//        an "@" symbol, then a domain part ("example.com").
//
//        The domain name part of an email address may only consist of letters, digits, hyphens and dots.
//        The local part, however, also allows a lot of different special characters. Here you can
//        look at several examples of correct and incorrect email addresses.
//
//        Given a valid email address, find its domain part.
        var s = address.substring(address.lastIndexOf("@") + 1)
        return s
    }

    fun buildPalindrome(st: String): String {
//        Given a string, find the shortest possible string which can be achieved by adding characters
//        to the end of initial string to make it a palindrome.
        var s = st
        for (i in st.indices) {
            if (s != s.reversed()) {
                s = st
                s += st.take(i).reversed()
            }
        }
        return s
    }

    fun electionsWinners(votes: MutableList<Int>, k: Int): Int {
//        Elections are in progress!
//
//        Given an array of the numbers of votes given to each of the candidates so far, and an integer
//        k equal to the number of voters who haven't cast their vote yet, find the number of candidates
//        who still have a chance to win the election.
//
//        The winner of the election must secure strictly more votes than any other candidate.
//        If two or more candidates receive the same (maximum) number of votes, assume there is no
//        winner at all.
        var max = votes.max()
        if (k == 0 && votes.count { it == max } > 1) {
            return 0
        } else if (k == 0) {
            return 1
        }
        var count = 0
        for (i in votes.indices) {
            if (max!! < votes[i] + k) {
                count++
            }
        }
        return count
    }

    fun isMAC48Address(inputString: String): Boolean {
//        A media access control address (MAC address) is a unique identifier assigned to network
//        interfaces for communications on the physical network segment.
//
//        The standard (IEEE 802) format for printing MAC-48 addresses in human-friendly form is six
//        groups of two hexadecimal digits (0 to 9 or A to F), separated by hyphens (e.g. 01-23-45-67-89-AB).
//
//        Your task is to check by given string inputString whether it corresponds to MAC-48 address or not.
        return (inputString.matches("([0-9A-F][0-9A-F]-){5}[0-9A-F][0-9A-F]".toRegex()))
    }

}

fun main(args: Array<String>) {
    var nah = EruptionofLight()
    println(nah.isBeautifulString("dfgasdxber"))
    println(nah.findEmailDomain("dfgdhgh@sfxxbef.com"))
    println(nah.buildPalindrome("dfgca"))
    println(nah.electionsWinners(mutableListOf(2, 5, 6, 7, 9, 4), 5))
    println(nah.isMAC48Address("23-df-45-gh-23-12"))
}
