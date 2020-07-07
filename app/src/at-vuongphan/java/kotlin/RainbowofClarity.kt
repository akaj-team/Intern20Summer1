object RainbowofClarity {
    @JvmStatic
    fun main(args: Array<String>) {
        /**
         * Determine if the given character is a digit or not.
         */
        println("48.Result:${isDigit('0')}")
        /**
         * Given a string, return its encoding defined as follows:
         * First, the string is divided into the least possible number of disjoint substrings consisting of identical characters
         * for example, "aabbbc" is divided into ["aa", "bbb", "c"]
         * Next, each substring with length greater than one is replaced with a concatenation of its length and the repeating character
         * for example, substring "bbb" is replaced by "3b"
         * Finally, all the new strings are concatenated together in the same order and a new string is returned.
         */
        println("49.Result:${lineEncoding("aabbbc")}")
        /**
         *  Given some integer, find the maximal number you can obtain by deleting exactly one digit of the given number.
         */
        println("51.Result:${deleteDigit(152)}")
    }
    private fun deleteDigit(n: Int): Int {
        val stringNumber = n.toString()
        var max = 0
        for (i in 0 until stringNumber.length) {
            val stringBuilder = StringBuilder(stringNumber)
            stringBuilder.deleteCharAt(i)
            if (stringBuilder.toString().toInt() > max) {
                max = stringBuilder.toString().toInt()
            }
        }
        return max
    }
    private fun lineEncoding(s: String): String? {
        var s1 = ""
        var count = 1
        var n = 0
        for (i in 1 until s.length) {
            n = i
            if (s[i] == s[i - 1]) count++ else {
                if (count == 1) s1 += s[i - 1] else {
                    s1 += count.toString() + s[i - 1]
                    count = 1
                }
            }
        }
        if (count == 1) s1 += s[n] else {
            s1 += count.toString() + s[n]
            count = 1
        }
        return s1
    }
    private fun isDigit(symbol: Char): Boolean {
        return if ('0' > symbol || symbol > '9') {
            false
        } else true
    }
}
