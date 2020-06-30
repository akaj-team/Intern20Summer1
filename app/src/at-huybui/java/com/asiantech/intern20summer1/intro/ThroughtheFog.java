package com.asiantech.intern20summer1.intro;

public class ThroughtheFog {

    /** 30
     * Consider integer numbers from 0 to n - 1 written down along the circle in such a way
     * that the distance between any two neighboring numbers is equal (note that 0 and n - 1 are neighboring, too).
     *
     * Given n and firstNumber, find the number which is written in the radially opposite position to firstNumber.
     */
    int circleOfNumbers(int n, int firstNumber) {
        if(firstNumber < n/2){
            return firstNumber+n/2;
        }else{
            return firstNumber - n/2;
        }
    }

    /** 31
     * You have deposited a specific amount of money into your bank account.
     * Each year your balance increases at the same growth rate.
     * With the assumption that you don't make any additional deposits,
     * find out how long it would take for your balance to pass a specific threshold.
     */

        int depositProfit(int deposit, int rate, int threshold) {
            int mYear = 0;  // khai báo số năm
            double newdDeposit      = deposit; // Ép deposit qua double để tránh ép kiểu nhiều lần tốn chu kỳ máy
            while(newdDeposit < threshold){
                newdDeposit = newdDeposit + newdDeposit*(rate/100.0);
                mYear++;
            }
            return mYear;
        }

}
