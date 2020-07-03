package com.asiantech.intern20summer1.theCoreKotlin

class IntroGates {

    /** 1 **
     *You are given a two-digit integer n. Return the sum of its digits.
     */
    fun addTwoDigits(n: Int): Int {
        return n/10 + n%10  //trả về tổng số đầu và số cuỗi
    }

    /** 2 **
     *Given an integer n, return the largest number that contains exactly n digits.
     */
    fun largestNumber(n: Int): Int {
        var mOut = 0
        for(i in 0 until n){
            mOut = (mOut*10 + 9)
        }
        return mOut
    }

    /** 3 **
     * n children have got m pieces of candy. They want to eat as much candy as they can,
     * but each child must eat exactly the same amount of candy as any other child.
     * Determine how many pieces of candy will be eaten by all the children together.
     * Individual pieces of candy cannot be split.
     */
    fun candies(n: Int, m: Int): Int {
        return (m/n) * n   // chia lấy nguyên rồi nhân lại sẽ được số kẹo lớn nhất
    }

    /** 4 **
     * Given the total number of rows and columns in the theater (nRows and nCols, respectively),
     * and the row and column you're sitting in, return the number
     * of people who sit strictly behind you and in your column or to the left,
     * assuming all seats are occupied.
     */
    fun seatsInTheater(nCols: Int, nRows: Int, col: Int, row: Int): Int {
        return (nCols - col+1)*(nRows - row)

    }

    /** 5 **
     *Given a divisor and a bound, find the largest integer N such that:
        -N is divisible by divisor.
        -N is less than or equal to bound.
        -N is greater than 0.
     *It is guaranteed that such a number exists.
     */
    fun maxMultiple(divisor: Int, bound: Int): Int {
        var mOut = bound;
        while(mOut%divisor != 0){
            mOut--;
        }
        return mOut

    }

    /** 6 **
     *Consider integer numbers from 0 to n - 1 written down along the circle in such a way that the distance
     * between any two neighboring numbers is equal (note that 0 and n - 1 are neighboring, too).
     * Given n and firstNumber, find the number which is written in the radially opposite position to firstNumber.
     */
    fun circleOfNumbers(n: Int, firstNumber: Int): Int {
        if(firstNumber < n/2){
            return n/2 + firstNumber
        }else{
            return firstNumber - n/2
        }
    }

    /** 7 **
     *https://app.codesignal.com/arcade/code-arcade/intro-gates/aiKck9MwwAKyF8D4L
     */
    fun lateRide(n: Int): Int {
        val mHour   = n/60
        val mMinute = n%60
        return mHour/10 + mHour%10 + mMinute/10 + mMinute%10
    }

    /** 8 **
     *Some phone usage rate may be described as follows:
     * first minute of a call costs min1 cents,
     * each minute from the 2nd up to 10th (inclusive) costs min2_10 cents
     * each minute after 10th costs min11 cents.
     * You have s cents on your account before the call. What is the duration of the longest call
     * (in minutes rounded down to the nearest integer) you can have?
     */
    fun phoneCall(min1: Int, min2_10: Int, min11: Int, s: Int): Int {
        if(s <= min1){
            return s/min1
        }else if(s > min1 && s< (min2_10*9 + min1)){
            return 1 + (s - min1)/min2_10
        }else{
            return 10 + (s- min1 - min2_10*9)/min11
        }

    }

}
