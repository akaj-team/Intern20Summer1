package intro;

public class TheJourneyBegins {
    /**
     * Write a function that returns the sum of two numbers.
     */
    int add(int param1, int param2) {
        return param1 + param2;
    }

    /**
     * Given a year, return the century it is in. The first century spans from the year 1 up to
     * and including the year 100, the second - from the year 101 up to and including the year
     * 200, etc.
     */
    int centuryFromYear(int year) {
        int temp = year / 100;
        if (year % 100 == 0) {
            return temp;
        } else return temp + 1;
    }

    /**
     * Given the string, check if it is a palindrome.
     */
    boolean checkPalindrome(String inputString) {
        for (int i = 0; i < ((inputString.length() - 1) / 2F); i++) {
            if (inputString.charAt(i) != inputString.charAt((inputString.length() - 1) - i)) {
                return false;
            }
        }
        return true;
    }
}
