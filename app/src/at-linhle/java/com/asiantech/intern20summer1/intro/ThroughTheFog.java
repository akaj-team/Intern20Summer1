package com.asiantech.intern20summer1.intro;

public class ThroughTheFog {
    /**
     * Consider integer numbers from 0 to n - 1 written down along the circle in such a way that
     * the distance between any two neighboring numbers is equal (note that 0 and n - 1 are
     * neighboring, too).
     * Given n and firstNumber, find the number which is written in the radially opposite position
     * to firstNumber.
     */
    int circleOfNumbers(int n, int firstNumber) {
        if (firstNumber > n / 2) {
            return firstNumber - n / 2;
        } else if (firstNumber < n / 2) {
            return n / 2 + firstNumber;
        } else if (firstNumber == n / 2) {
            return 0;
        }
        return 0;
    }

    /**
     * You have deposited a specific amount of money into your bank account. Each year your balance
     * increases at the same growth rate. With the assumption that you don't make any additional
     * deposits, find out how long it would take for your balance to pass a specific threshold.
     */
    int depositProfit(int deposit, int rate, int threshold) {
        return (int) Math.ceil(Math.log((double) threshold / deposit) / Math.log(1 + (rate / 100.0)));
    }

    /**
     * Given a sorted array of integers a, your task is to determine which element of a is
     * closest to all other values of a. In other words, find the element x in a, which minimizes
     * the following sum:
     * abs(a[0] - x) + abs(a[1] - x) + ... + abs(a[a.length - 1] - x)
     * (where abs denotes the absolute value)
     * If there are several possible answers, output the smallest one.
     */
    int absoluteValuesSumMinimization(int[] a) {
        if (a.length % 2 == 1) return a[a.length / 2];
        else return a[a.length / 2 - 1];
    }
}
