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
}
