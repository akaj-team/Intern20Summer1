package com.asiantech.intern20summer1.intro;


import java.lang.reflect.Array;

public class TheJourneyBegins {
    public static void main(String[] args) {

        System.out.println(add(1, 2));
        System.out.println(centuryFromYear(2020));
        System.out.println(checkPalindrome("asd"));

    }

    static int add(int param1, int param2) {
/**
 * Write a function that returns the sum of two numbers.
 **/
        return param1 + param2;
    }

    static int centuryFromYear(int year) {
        /**Given a year,return the century it is in.The first century spans from the year 1 up to
         and including the year 100, the second - from the year 101 up to and including the year
         200, etc. **/
        int cen;
        if (year % 100 == 0) {
            cen = year / 100;
        } else {
            cen = year / 100 + 1;
        }
        return cen;
    }

    static boolean checkPalindrome(String inputString) {
        /**
         * Given the string, check if it is a palindrome.
         */
        String a = " ";
        int l = inputString.length();
        for (int i = l - 1; i >= 0; i--) {
            a = a + inputString.charAt(i);
        }
        if (a.equalsIgnoreCase(inputString)) {
            return true;
        } else {
            return false;
        }
    }


}

