package com.asiantech.intern20summer1.intro;

public class ThroughTheFog {
    public static void main(String[] args) {
        //RUN main() with Coverage
        System.out.println("Ex 30: " + circleOfNumbers(10, 2));

        System.out.println("Ex 31: " + depositProfit(100, 20, 70));

        System.out.println("Ex 32: " + absoluteValuesSumMinimization(new int[]{1, 1, 3, 4}));

        System.out.println("Ex 33: " + stringsRearrangement(new String[]{"zzzzab", "zzzzbb", "zzzzaa"}));
    }

    static int circleOfNumbers(int n, int firstNumber) {
        /**
         * Consider integer numbers from 0 to n - 1 written down
         * along the circle in such a way that
         * the distance between any two neighboring numbers is equal
         * (note that 0 and n - 1 are neighboring, too).
         *
         * Given n and firstNumber,
         * find the number which is written
         * in the radially opposite position to firstNumber.
         */
        if (firstNumber < n / 2) {
            return firstNumber + n / 2;
        } else if (firstNumber < n / 2) {
            return 0;
        } else {
            return firstNumber - n / 2;
        }
    }

    static int depositProfit(int deposit, int rate, int threshold) {
        /**
         *You have deposited a specific amount of money into your bank account.
         * Each year your balance increases at the same growth rate.
         * With the assumption that you don't make any additional deposits,
         * find out how long it would take for your balance to pass a specific threshold.
         */
        double money = (double)deposit;

        for (int year = 1; ; year++) {
            money =  money*(rate+100)/100;
            if (money >= threshold) {
                return year;
            };
        }
    }

    static int absoluteValuesSumMinimization(int[] a) {
        /**
         * Given a sorted array of integers a,
         * your task is to determine which element of a is closest to all other values of a. In other words,
         * find the element x in a, which minimizes the following sum:
         *
         * abs(a[0] - x) + abs(a[1] - x) + ... + abs(a[a.length - 1] - x)
         * (where abs denotes the absolute value)
         *
         * If there are several possible answers, output the smallest one.
         */
        if(a.length%2==1) {
            return a[a.length/2];
        } else {
            return a[a.length/2-1];
        }
    }

    static boolean stringsRearrangement(String[] inputArray) {

        boolean[] used = new boolean[inputArray.length];
        findSequence(inputArray, null, used, 0);
        return success;
    }

    static boolean success = false; // modified by findSequence
    /**
     *      recursive backtracking procedure to find admissible
     *      sequence of strings in the array. String prev is the
     *      previous string in the sequence, used[] keeps track
     *      of which strings have been used so far, and n is the
     *      current length of the sequence.
     */

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
