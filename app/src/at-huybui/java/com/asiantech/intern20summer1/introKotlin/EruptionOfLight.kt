package com.asiantech.intern20summer1.introKotlin

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
        return  address.substring((address.lastIndexOf("@") + 1))
    }


}