package com.asiantech.intern20summer1.introkotlin

class EruptionOfLight {

    /** 43 **
     * A string is said to be beautiful if each letter in the string appears at most as many
     * times as the previous letter in the alphabet within the string;
     * ie: b occurs no more times than a; c occurs no more times than b;
     * etc.Given a string, check whether it is beautiful.
     */
    fun isBeautifulString(inputString: String): Boolean {
        val mArray = ('a'..'z').map { c -> inputString.count { it == c } } // tạo mảng đếm số ký tự
        return mArray.equals(mArray.sortedDescending()) // kiếm tra xem sô ký tự có giảm ko
    }

    /** 44 **
     *Given a valid email address, find its domain part.
     */
    fun findEmailDomain(address: String): String {
        return address.substring((address.lastIndexOf("@") + 1))
    }

    /** 45 **
     *Given a string, find the shortest possible string which can be achieved
     * by adding characters to the end of initial string to make it a palindrome.
     */
    fun buildPalindrome(st: String): String {
        for (index in 0 until st.length) {   // quét chuỗi
            // thêm ky tự vào chuỗi lần lượt là 1, 2, 3 ... ký tự, với ký tự lấy ở đầu chuỗi và đã đảo ngược
            var mOutput = st + st.take(index).reversed()
            if (mOutput == mOutput.reversed()) { // nếu chuỗi và đảo ngược của chuỗi bằng nhau thì return Output
                return mOutput
            }
        }
        return "0"
    }

    /** 46 **'
     *Elections are in progress!
     * Given an array of the numbers of votes given to each of the candidates so far, and an integer k equal
     * to the number of voters who haven't cast their vote yet, find the number of candidates who still have
     * a chance to win the election.
     * The winner of the election must secure strictly more votes than any other candidate.
     * If two or more candidates receive the same (maximum) number of votes, assume there is no winner at all.
     */
    fun electionsWinners(votes: MutableList<Int>, k: Int): Int {
        val maxVote = votes.max()!!  // lấy giá trị vote lơn nhất hiện tại
        var mCount = 0   // số người có cơ hội chiến thắng
        votes.forEach { // quét votes
            when {
                it + k > maxVote -> mCount++ // cộng mCount nếu số phiếu sau cầu cử lớn hơn maxVote
            }
        }
        if(votes[votes.size - 2] != maxVote && mCount == 0){
            mCount++
        }
        return mCount
    }

    /** 47 **
     *A media access control address (MAC address) is a unique identifier assigned
     * to network interfaces for communications on the physical network segment.
     * The standard (IEEE 802) format for printing MAC-48 addresses in human-friendly
     * form is six groups of two hexadecimal digits (0 to 9 or A to F), separated by hyphens (e.g. 01-23-45-67-89-AB).
     * Your task is to check by given string inputString whether it corresponds to MAC-48 address or not
     */
    fun isMAC48Address(inputString: String): Boolean {
        return inputString.matches("^([A-F0-9]{2}-){5}[A-F0-9]{2}$".toRegex())
    }

}
