package com.asiantech.intern20summer1.kotlin

class EruptionOfLight {
    fun isBeautifulString(inputString: String): Boolean {
        var result = true
        for (i in 'a' until 'z') {
            val next = i + 1
            if (count(inputString, i) < count(inputString, next)) {
                result = false
                break
            }
        }
        return result
    }

    private fun count(inputString: String, inputChar: Char): Int {
        var count = 0
        for (i in inputString.indices) {
            if (inputString[i] == inputChar)
                count++
        }
        return count
    }

    fun findEmailDomain(address: String): String {
        val pieces = address.split("@")
        return if (pieces.size == 2) {
            pieces[1]
        } else {
            pieces[pieces.size - 1]
        }
    }

    fun buildPalindrome(st: String): String {
        var flag: Boolean
        val stBuilder = StringBuilder(st)
        var i = stBuilder.length
        while (i >= 0) {
            flag = true
            var j = 0
            while (j < i - j - 1) {
                if (i - j <= stBuilder.length && stBuilder[j] != stBuilder[i - j - 1]) {
                    flag = false
                    break
                }
                j++
            }
            if (flag) {
                for (k in stBuilder.length until i) {
                    stBuilder.append(stBuilder[i - k - 1])
                }
                return stBuilder.toString()
            }
            i++
        }
        return "a"
    }

    fun electionsWinners(votes: MutableList<Int>, k: Int): Int {
        var count = 0
        votes.sort()
        val max = votes[votes.size - 1]
        if (k == 0) {
            for (i in 0 until votes.size) {
                if (votes[i] == max) count++
            }
            if (count >= 2) return 0
        } else {
            for (i in 0 until votes.size) {
                if (votes[i] + k > max) count++
            }
        }
        return count
    }

    fun isMAC48Address(inputString: String): Boolean {
        return "^([0-9A-F][0-9A-F]-){5}[0-9A-F][0-9A-F]$".toRegex().matches(inputString)
    }
}
