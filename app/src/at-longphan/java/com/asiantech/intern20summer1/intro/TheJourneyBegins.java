package com.asiantech.intern20summer1.intro;

public class TheJourneyBegins {
    public static void main(String[] args) {
        //RUN main() with Coverage
        System.out.print("Ex1: ");
        System.out.println(add(2, 8));

        System.out.print("Ex2: ");
        System.out.println(centuryFromYear(2001));

        System.out.print("Ex3: ");
        System.out.println(checkPalindrome("acbca"));
    }

    static int add(int param1, int param2) {
        /**
         * Write a function that returns the sum of two numbers.
         */
        return param1 + param2;
    }

    static int centuryFromYear(int year) {
        /**
         * Given a year, return the century it is in. T
         * he first century spans from the year 1 up to and including the year 100,
         * the second - from the year 101 up to and including the year 200, etc.
         */
        return (year/100.0) == (year/100) ? year/100 : year/100 +1;
    }

    static boolean checkPalindrome(String inputString) {
        /**
         * Given the string, check if it is a palindrome.
         */
        boolean result = true;
        int length = inputString.length();
        for(int i = 0; i < length/2; i++){
            if(inputString.charAt(i) != inputString.charAt(length -1 -i)) {
                result = false;
                break;
            }
        }
        return result;
    }
}
