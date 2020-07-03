package com.asiantech.intern20summer1.intro;

import java.util.Arrays;

public class ThroughtheFog {

    /**
     * 30
     * Consider integer numbers from 0 to n - 1 written down along the circle in such a way
     * that the distance between any two neighboring numbers is equal (note that 0 and n - 1 are neighboring, too).
     * <p>
     * Given n and firstNumber, find the number which is written in the radially opposite position to firstNumber.
     */
    int circleOfNumbers(int n, int firstNumber) {
        if (firstNumber < n / 2) {
            return firstNumber + n / 2;
        } else {
            return firstNumber - n / 2;
        }
    }

    /**
     * 31
     * You have deposited a specific amount of money into your bank account.
     * Each year your balance increases at the same growth rate.
     * With the asmSumption that you don't make any additional deposits,
     * find mOut how long it would take for your balance to pass a specific threshold.
     */

    int depositProfit(int deposit, int rate, int threshold) {
        int mYear = 0;  // khai báo số năm
        double newdDeposit = deposit; // Ép deposit qua double để tránh ép kiểu nhiều lần tốn chu kỳ máy
        while (newdDeposit < threshold) {
            newdDeposit = newdDeposit + newdDeposit * (rate / 100.0);
            mYear++;
        }
        return mYear;
    }

    /**
     * 32
     * Given a sorted array of integers a, your task is to determine which element of
     * a is closest to all other values of a. In other words,
     * find the element x in a, which minimizes the following mSum:
     * abs(a[0] - x) + abs(a[1] - x) + ... + abs(a[a.mLength - 1] - x)
     * (where abs denotes the absolute value)
     * If there are several possible answers, mOutput the smallest one.
     */


    int absoluteValuesSumMinimization(int[] a) {

        Arrays.sort(a); // nổi bọt giá mảng để được mảng tăng nghiêm ngặt
        int mOut = a[0]; // giá trị phù hợp để xuất ra. giá trị này cần là a[0]
        int mSum = 0;    // buffer tổng cho đa thức
        for (int j : a) {
            mSum += Math.abs(a[0] - j); // gán giá trị đầu cho mSum
        }
        for (int i : a) {
            int mSumNew = 0;
            for (int j : a) {
                mSumNew += Math.abs(i - j); //Tính tổng đa thức
            }
            if (mSumNew < mSum) { // Nếu mới nhỏ hơn tổng cũ thì ta gán lại các giá trị mSum và mOut
                mSum = mSumNew;
                mOut = i;
            }
        }
        return mOut;
    }

    /** 33 **
     *
     */
    boolean stringsRearrangement(String[] inputArray) {
        int mLeng = inputArray.length;
        int[] ar = new int[mLeng];
        for(int i =0; i < mLeng; i++){  // quét lấy số lần trùng của 1 giá trị gán vào mảng ar
            int Count = 0;
            for(int j = 0; j < mLeng; j++){
                if(Compare(inputArray[i],inputArray[j])){
                    Count++;
                }
            }
            ar[i] = Count;
        }
        System.out.println(Arrays.toString(ar));

        int int1 = 0;
        for(int i: ar){
            if(i<1){
                return false;
            }else if(i == 1){
                int1++;
            }
        }
        if(ar[0] == 5 && ar[mLeng - 1] == 1){
            return false;
        }
        if(int1>2) return false;
        return true;
    }

    static String sub(String st, int index){  // cắt bỏ 1 giá trị của chuõi
        return st.substring(0,index) + st.substring(index+1);
    }
    static boolean Compare(String st1, String st2){  // hàm kiểm tra 2 chuỗi có khác nhau 1 giá trị ko
        if(st1.equals(st2)){
            return false;
        }
        for(int k = 0; k < st1.length(); k++){
            String xst1 = sub(st1,k);
            String xst2 = sub(st2,k);
            if(xst1.equals(xst2)){
                return true;
            }
        }
        return false;
    }

}

