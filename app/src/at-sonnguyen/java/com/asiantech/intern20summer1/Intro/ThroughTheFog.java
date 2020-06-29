package com.asiantech.intern20summer1.Intro;

public class ThroughTheFog {
    /*
    *Given n and firstNumber,
    * find the number which is written in the radially opposite position to firstNumber
    */
    static int circleOfNumbers(int n, int firstNumber) {
        return (firstNumber + n/2 ) % n ;
    }
    /*
    * find out how long it would take for your balance to pass a specific threshold
    */
    static int depositProfit(int deposit, int rate, int threshold) {
        int Result = 0;
        double money = deposit ;
        while (money < threshold ){
            money += money*rate/100;
            Result++;
        }
        return Result ;
    }
    /*
    *  determine which element of a is closest to all other values of a
    */
    static int absoluteValuesSumMinimization(int[] a) {
        return a[(a.length-1)/2];
    }
    /*
    *Given an array of equal-length strings,
    * you'd like to know if it's possible to rearrange the order of the elements in such a way
    * that each consecutive pair of strings differ by exactly one character.
    * Return true if it's possible, and false if not.
    */
    boolean stringsRearrangement(String[] inputArray) {
        return p(inputArray.length, inputArray.length, inputArray, false);}

    boolean p(int n, int s, String[] a, boolean b) {
        if (s == 1) {
            int c = 0;
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < a[i].length(); j++) {
                    if (!a[i].substring(j,j+1).equals(a[i+1].substring(j,j+1))) {c++;}}
                if (a[i].equals(a[i+1])) {c += 10;}}
            if (c == a.length - 1) {return true;}}
        for (int i = 0; i < s; i++) {
            b = p(n, s-1, a, b);
            if (s % 2 == 0) {
                String t = a[i];
                a[i] = a[s-1];
                a[s-1] = t;}
            else {
                String t = a[0];
                a[0] = a[s-1];
                a[s-1] = t;}}
        return b;}
    public static void main(){

    int FaceWithFirstNamber = circleOfNumbers(15,5);
    int TimeToGainPtofit = depositProfit(100,10,200 );
    int[] inputArray = { 1,2,6,8,9,15};
    int AbsoluteValuesSumMinOfArray = absoluteValuesSumMinimization(inputArray);

    }

}
