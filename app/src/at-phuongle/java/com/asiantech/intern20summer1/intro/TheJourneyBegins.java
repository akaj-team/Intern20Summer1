package com.asiantech.intern20summer1.intro;

public class TheJourneyBegins {
    /**
     * Write a function that returns the sum of two numbers.
     */
    int add(int param1, int param2) {
        return param1 + param2;
    }

    /**
     * Write a function that returns the century when input year
     */
    int centuryFromYear(int year) {
        if (year <= 100) {
            return 1;
        } else {
            return ((year) % 100 == 0) ? (year / 100) : ((year / 100) + 1);
        }
    }

    /**
     * Write a function that check the palindrome string
     */
    boolean checkPalindrome(String inputString) {
        int n = inputString.length();
        for (int i = 0; i < (n / 2); i++) {
            char a1 = inputString.charAt(i);
            char a2 = inputString.charAt(n - i - 1);

            if (a1 != a2) {
                return false;
            }
        }
        return true;
    }
}
