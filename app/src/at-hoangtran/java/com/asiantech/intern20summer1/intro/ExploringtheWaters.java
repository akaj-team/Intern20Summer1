package com.asiantech.intern20summer1.intro;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Collections;
import java.util.stream.Stream;
import static java.util.stream.Collectors.joining;

public class ExploringtheWaters {
    public static void main(String[] args) {
        int[] intArrayA = new int[]{1, 7, 6, 4, 6, 7, 8, 9, 5};
        int[] intArrayB = new int[]{1, 4, 6, 7, 9, 6, 4, 2, 1};
        String[] string = new String[]{"asdasd", "zcvxcb", "azvbas"};
        String palin = "aaabbcc";
        System.out.println(alternatingSums(intArrayA));
        System.out.println(addBorder(string));
        System.out.println(areSimilar(intArrayA, intArrayB));
        System.out.println(arrayChange(intArrayA));
        System.out.println(palindromeRearranging(palin));
    }

    static int[] alternatingSums(int[] a) {
//        Several people are standing in a row and need to be divided into two teams.The first
//        person goes into team 1, the second goes into team 2, the third goes into team 1 again, the
//        fourth into team 2, and so on.
//
//        You are given an array of positive integers -the weights of the people.Return an array of
//        two integers, where the first element is the total weight of team 1, and the second
//        element is the total weight of team 2 after the division is complete.
        int l = a.length;
        int sum1 = 0;
        int sum2 = 0;
        int[] sum = new int[2];
        for (int i = 0; i < l; i++) {
            if (i % 2 == 0) {
                sum1 += a[i];
            } else {
                sum2 += a[i];
            }
        }
        sum[0] = sum1;
        sum[1] = sum2;
        return sum;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    static String[] addBorder(String[] picture) {
//        Given a rectangular matrix of characters, add a border of asterisks(*) to it.
        int l = picture.length;
        String[] border = new String[l + 2];
        int stringl = picture[0].length();
        for (int i = 0; i < l + 2; i++) {
            if (i == 0 || i == l + 1) {
                border[i] = border[l + 1] = Stream.generate(() ->"*").limit(stringl + 2).collect(joining());

            } else {
                border[i] = "*" + picture[i - 1] + "*";
            }
        }
        return border;
    }

    static boolean areSimilar(int[] a, int[] b) {
//        Two arrays are called similar if one can be obtained from another by swapping at most
//        one pair of elements in one of the arrays.
//
//        Given two arrays a and b, check whether they are similar.
        int l = a.length;
        int count = 0;
        int swapcount = 0;
        for (int i = 0; i < l; i++) {
            if (a[i] == b[i]) {
                count++;
            } else {
                for (int j = i + 1; j < l; j++) {
                    if (b[j] == a[i] && b[j] != a[j]) {
                        int tmp = b[j];
                        b[j] = b[i];
                        b[i] = tmp;
                        swapcount++;
                    }
                }
                if (a[i] == b[i]) {
                    count++;
                }
            }

        }
        if (count == l && swapcount < 2) {
            return true;
        }
        return false;
    }

    static int arrayChange(int[] inputArray) {
//        You are given an array of integers.On each move you are allowed to increase exactly one
//        of its element by one.Find the minimal number of moves required to obtain a
//        strictly increasing sequence from the input.
        int l = inputArray.length;
        int count = 0;
        for (int i = 0; i < l - 1; i++) {
            if (inputArray[i] >= inputArray[i + 1]) {
                count += inputArray[i] - inputArray[i + 1] + 1;
                inputArray[i + 1] = inputArray[i] + 1;
            }
        }
        return count;
    }

    static boolean palindromeRearranging(String inputString) {
//        Given a string, find out if its characters can be rearranged to form a palindrome.
        int l = inputString.length();
        int noDup = 0;
        char[] charArray = new char[l];
        for (int i = 0; i < l; i++) {
            charArray[i] = inputString.charAt(i);
        }
        for (int i = 0; i < l; i++) {
            int dup = 0;
            for (int j = 0; j < l; j++) {
                if (charArray[j] == charArray[i]) {
                    dup++;
                }
            }
            if (dup == 1) {
                noDup++;
            } else if (dup % 2 == 1 && l % 2 == 0) {
                return false;
            } else if (dup % 2 == 1 && l % 2 == 1) {
                return true;
            }
        }
        if (noDup > 1) {
            return false;
        }
        return true;
    }

}
