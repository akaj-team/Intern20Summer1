object EruptionofLight {
    private const val SIZE = 26
    private const val ACI = 97
    @JvmStatic
    fun main(args: Array<String>) {
        /**
         * A string is said to be beautiful if each letter in the string appears at most as many times as the previous
         * letter in the alphabet within the string; ie: b occurs no more times than a; c occurs no more times than b; etc.
         */
          println("43.Result:${isBeautifulString("bbbaacdafe")}")
        /**
         *An email address such as "John.Smith@example.com" is made up of a local part ("John.Smith"), an "@" symbol, then a domain part ("example.com")
         */
        println("44.Result:${findEmailDomain("102160078@sv.dut.edu.vn")}")
        /**
         * A media access control address (MAC address) is a unique identifier assigned to network interfaces
         * for communications on the physical network segment.
         */
        println("47.Result:${isMAC48Address( "00-1B-63-84-45-E6")}")
    }
    private fun isMAC48Address(inputString: String): Boolean {
        return (inputString.matches("""^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})${'$'}""".toRegex()))
    }
    private fun findEmailDomain(address: String): String {
        var domain = ""
        var index = address.lastIndexOf('@')
        for(i in index + 1 until address.length){
            domain = domain + address[i]
        }
        return domain
    }
    private fun isBeautifulString(inputString: String): Boolean {
        val numLetters = IntArray(SIZE)
        for (letter in inputString) {
            val i = letter.toInt() - ACI
            numLetters[i]++
        }

        var isBeautiful = true
        for (i in 1 until numLetters.size) {
            isBeautiful = isBeautiful && numLetters[i] <= numLetters[i - 1]
        }

        return isBeautiful
    }
}
