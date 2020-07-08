package com.asiantech.intern20summer1.kotlin

object LandOfLogic {
    private const val NINE = 9
    private const val VALID_HOUR = 23
    private const val VALID_MINUTE = 59

    fun longestWord(text: String): String {
        var regex: String = "[^a-zA-Z]+"
        var strings: Array<String> = text.split(regex.toRegex()).toTypedArray()
        var longestWord: String = ""

        for (i in strings.indices) {
            if (strings[i].length > longestWord.length) {
                longestWord = strings[i]
            }
        }

        return longestWord
    }

    fun validTime(time: String): Boolean {
        var check: Boolean = true
        var regex: String = "[:]"
        var strings: Array<String> = time.split(regex.toRegex()).toTypedArray()
        if (strings[0].toInt() < 0 || strings[0].toInt() > VALID_HOUR) {
            check = false
        }
        if (strings[1].toInt() < 0 || strings[1].toInt() > VALID_MINUTE) {
            check = false
        }

        return check
    }

    fun sumUpNumbers(inputString: String): Int {
        var regex: String = "[^0-9]+"
        var sum: Int = 0
        var strings: Array<String> = inputString.split(regex.toRegex()).toTypedArray()

        for (i in strings.indices) {
            if (strings[i].isNotEmpty()) {
                sum += strings[i].toInt()
            }
        }

        return sum
    }

    fun digitsProduct(product: Int): Int {
        var p: Int = product
        var digits: String = ""

        if (p == 0) {
            digits = "10"
        }

        if (p == 1) {
            digits = "1"
        }


        for (divisor in NINE downTo 2) {
            while (p % divisor == 0) {
                p /= divisor
                digits = divisor.toString() + digits
            }
        }

        if (p > 1) {
            digits = "-1"
        }

        return digits.toInt()
    }

    fun fileNaming(names: Array<String>): Array<String> {
        var result: MutableList<String> = mutableListOf()
        for (i in names.indices) {
            var count: Int = 0
            var s: String = names[i]
            while (result.contains(s)) {
                count++
                s = names[i] + "(" + count + ")"
            }
            result.add(s)
        }

        return result.toTypedArray()
    }

    fun messageFromBinaryCode(code: String): String {
        var regex: String = "(?<=\\G.{8})"
        var result: String = ""
        var strings: Array<String> = code.split(regex.toRegex()).toTypedArray()

        for (i in 0 until strings.size - 1) {
            result += strings[i].toInt(2).toChar()
        }

        return result
    }
}
