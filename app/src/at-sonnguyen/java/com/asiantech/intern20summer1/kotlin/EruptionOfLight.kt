package com.asiantech.intern20summer1.kotlin

object EruptionOfLight {
    /*
    *   Given a string, check whether it is beautiful.
    */
    private fun isBeautifulString(inputString: String): Boolean {
        var initialChar: Char = 'a'
        for (i in 0 until 26) {
            if (inputString.toCharArray().filter { it == initialChar }.count() < inputString.toCharArray()
                    .filter { it == (initialChar + 1).toChar() }.count()
            ) {
                return false
            }
            initialChar = (initialChar + 1).toChar()
        }
        return true
    }
    /*
    *   Given a valid email address, find its domain part.
    */
    private fun findEmailDomain(address: String): String = address.substring(address.lastIndexOf('@')+1)
    /*
    *   Given a string, find the shortest possible string which can be achieved
    *   by adding characters to the end of initial string to make it a palindrome.
    */
    private fun buildPalindrome(st: String): String {
        if (st == (StringBuilder(st).reverse().toString())){
            return st
        }
        var string1 : String =  ""
        for (i in st.indices){
            string1 += st.get(i)
            var string2 : String =st+ (StringBuilder(string1).reverse().toString())
            if (string2.equals(StringBuilder(string2).reverse().toString())){
                return string2
            }
        }
        return ""
    }
    /*
    *   find the number of candidates who still have a chance to win the election.
    */
    private fun electionsWinners(votes: MutableList<Int>, k: Int): Int {
        var maxOfVotes : Int = 0
        var maxIsOnly : Boolean = false
        var count : Int = 0
        for ( i in votes.indices){
            if (votes[i] > maxOfVotes ){
                maxOfVotes = votes[i]
                maxIsOnly = false
            }else if (votes[i] == maxOfVotes){
                maxIsOnly = true
            }
        }
        for (i in votes.indices){
            if(k!=0 && k+votes[i] > maxOfVotes ){
                count++
            }else if(k==0 && maxIsOnly == false){
                return 1
            }else if (k==0 && maxIsOnly == true ){
                return 0
            }
        }
        return count
    }
    /*
    *   check by given string inputString whether it corresponds to MAC-48 address or not.
    */
    private fun isMAC48Address(inputString: String) = inputString.matches("""^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$""".toRegex())
    @JvmStatic
    fun main(args: Array<String>) {
        val inputString : String = "bbbaacdafe"
        val ipAddress : String = "00-1B-63-84-45-E6"
        val email : String = "prettyandsimple@example.com"
        println("43. $inputString is a beautiful String ? ${isBeautifulString(inputString)}")
        println("44. domain in $email is ${findEmailDomain(email)}")
        println("45. Palinedrome of $inputString is ${buildPalindrome(inputString)}")
        val inputArray : MutableList<Int> = mutableListOf(2, 3, 5, 2)
        println("46. There are ${electionsWinners(inputArray,3)} can win the elections")
        println("47. $ipAddress is a Ip Mac Address ? ${isMAC48Address(ipAddress)}")
    }
}
