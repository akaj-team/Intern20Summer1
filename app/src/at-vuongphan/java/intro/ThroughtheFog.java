package intro;

public class ThroughtheFog {

    public static void main(String[] args) {
        /**
         *     Consider integer numbers from 0 to n - 1 written down along the circle in such a way that the distance
         *     between any two neighboring numbers is equal (note that 0 and n - 1 are neighboring, too).
         */
        System.out.println("30.Result :"+circleOfNumbers(10,3));
        /**
         * You have deposited a specific amount of money into your bank account. Each year your balance increases at the same growth rate. With the assumption that you
         * don't make any additional deposits, find out how long it would take for your balance to pass a specific threshold.
         */
        System.out.println("31.Result:"+depositProfit(50,25,100));
        /**
         *Given a sorted array of integers a, your task is to determine which element of a is closest to all other values of a.
         *  In other words, find the element x in a, which minimizes the following sum:
         */
        int[] array = {2,3};
        System.out.println("32.Result:"+absoluteValuesSumMinimization(array));
        /**
         * Given an array of equal-length strings, you'd like to know if it's possible to rearrange the order of the elements in such a way that each consecutive
         * pair of strings differ by exactly one character. Return true if it's possible, and false if not.
         */
        String[] inputArray = {"aba", "bbb", "bab"};
        System.out.println("33.Result:"+stringsRearrangement(inputArray));

    }
    static int circleOfNumbers(int n, int firstNumber) {
        return (firstNumber+n/2)%n;
    }
    static int depositProfit(int deposit, int rate, int threshold) {
        int year = 0;
        double sum = deposit;
        while (sum < threshold) {
            sum = sum + (sum * rate / 100);
            year++;

        }
        return year;
    }
    static int absoluteValuesSumMinimization(int[] arr) {
        if (arr.length % 2 != 0) {
            return arr[arr.length / 2];
        } else
            return arr[arr.length / 2 - 1];
    }
    static boolean stringsRearrangement(String[] inputArray) {

        boolean[] used = new boolean[inputArray.length];
        findSequence(inputArray, null, used, 0);
        return success;
    }

    static boolean success = false; // modified by findSequence
    // recursive backtracking procedure to find admissible
    // sequence of strings in the array. String prev is the
    // previous string in the sequence, used[] keeps track
    // of which strings have been used so far, and n is the
    // current length of the sequence.
    static void findSequence(String[] a, String prev, boolean[] used, int n) {
        if (n == a.length) {
            success = true;
            return;
        }
        for (int i = 0; i < a.length; i++) {
            if (!used[i] && (prev == null || differByOne(prev, a[i]))) {
                used[i] = true;
                findSequence(a, a[i], used, n+1);
                used[i] = false;
            }
        }
    }

    static boolean differByOne(String a, String b) {
        int count = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                count++;
            }
        }
        return count == 1;
    }
}
