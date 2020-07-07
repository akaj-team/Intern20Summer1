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


}