package com.asiantech.intern20summer1.intro;

public class TheJourneyBegins {

/** 1 **
*    Write a function that returns the sum of two numbers.
*/
    int add(int param1, int param2) {
        return param1+param2;
    }

/** 2 **
 * Given a year, return the century it is in. The first century spans
 * from the year 1 up to and including the year 100, the second -
 * from the year 101 up to and including the year 200.
 */
    int centuryFromYear(int year) {
        return (year-1)/100 + 1;
    }

/** 3 **
 * Given the string, check if it is a string that doesn't changed when reversed
 * (it reads the same backward and forward).
 * "eye" is a palindrome
 * "taco cat" is not a palindrome (backwards it spells "tac ocat")
 */
    boolean checkPalindrome(String inputString) {
        boolean mReturn = true;
        for(int i = 0; i < inputString.length()/2; i++)
        {
            if(inputString.charAt(i) != inputString.charAt(inputString.length() - 1 - i))
            {
                mReturn = false;
            }
        }
        return mReturn;
    }

    /**
     * The end.
     */
}
