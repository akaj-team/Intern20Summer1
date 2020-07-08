package com.asiantech.intern20summer1.kotlin

object EruptionOfLight {
    fun findEmailDomain(address: String): String {
        var indexOfAt: Int = 0
        for (element in address.indices) {
            if (address[element] == '@') {
                indexOfAt = element
            }
        }

        return address.substring(indexOfAt + 1)
    }

    fun isMAC48Address(inputString: String): Boolean {
        var regex = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$"
        return inputString.matches(regex.toRegex())
    }

    fun buildPalindrome(st: String): String {
        var string: String = st
        var canConvert: Boolean
        var i: Int = string.length
        var j: Int = 0

        while (true) {
            canConvert = true

            for (k in 0 until (i - j - 1)) {
                if (i - k <= string.length && (string[k] != string[i - k - 1])) {
                    canConvert = false
                    break
                }
            }

            if (canConvert) {
                for (j in string.length until i) {
                    string += string[i - j - 1]
                }
                return string
            }

            i++
            j++
        }
    }
}
