package com.asiantech.intern20summer1.intro;

public class ThroughTheFog {
    public static void main(String[] args) {

    }

    static int circleOfNumbers(int n, int firstNumber) {
//        Consider integer numbers from 0 to n -1 written down along the circle in such a way that
//        the distance between any two neighboring numbers is
//        equal(note that 0and n - 1are neighboring, too).
//
//                Given n and firstNumber, find the number which is written in the radially
//        opposite position to firstNumber.
        return (n / 2 + firstNumber) < n ? (n / 2 + firstNumber) : (n / 2 + firstNumber) - n;
    }

    static int depositProfit(int deposit, int rate, int threshold) {
//        You have deposited a specific amount of money into your bank account.Each year your balance
//        increases at the same growth rate.With the assumption that you don
//        't make any additional deposits, find out how long it would take for your balance to pass a specific threshold.
        int count = 0;
        double x = (double) deposit;
        while (x < threshold) {
            x = (double) (x * (rate + 100) / 100);
            count++;
        }
        return count;
    }

    static int absoluteValuesSumMinimization(int[] a) {
//        Given a sorted array of integers a, your task is to determine which element of a is closest
//        to all other values of a.In other words, find the element x in a, which minimizes the
//        following sum:
        int l = a.length;
        if (l == 2) {
            return a[0];
        }
        int x = a[0];
        int sumtmp = 0;
        for (int i = 0; i < l; i++) {
            sumtmp += Math.abs(a[i] - a[0]);
        }
        for (int i = 0; i < l; i++) {
            int sum = 0;
            for (int j = 0; j < l; j++) {
                sum += Math.abs(a[j] - a[i]);
            }
            if (sum < sumtmp) {
                sumtmp = sum;
                x = a[i];
            }
        }
        return x;
    }


}
