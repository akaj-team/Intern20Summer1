package com.asiantech.intern20summer1.Intro;

public class TheJourneyBegins {
    /*
    * returns the sum of two numbers.
    */
    int add(int param1, int param2) {
        return param1 + param2 ;
    }

    /*
     * return the century the year is in
     */
    int centuryFromYear(int year) {
        if (year % 100 == 0){
            return year/100;
        }
        return year/100 + 1 ;
    }
    /*
     * check palindrome.
     */
    boolean checkPalindrome(String inputString) {
        char [] arrayInputString = inputString.toCharArray();
        for (int i =0 ; i <= inputString.length()/2 ; i++){
            if (arrayInputString[i] != arrayInputString[inputString.length()-1 - i]){
                return false;
            }
        }
        return true;
    }
}
