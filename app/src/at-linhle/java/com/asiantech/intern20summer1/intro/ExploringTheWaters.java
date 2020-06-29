package com.asiantech.intern20summer1.intro;

public class ExploringTheWaters {
    /**
     * Several people are standing in a row and need to be divided into two teams. The first person
     * goes into team 1, the second goes into team 2, the third goes into team 1 again, the fourth
     * into team 2, and so on.
     * You are given an array of positive integers - the weights of the people. Return an array of
     * two integers, where the first element is the total weight of team 1, and the second element
     * is the total weight of team 2 after the division is complete.
     */
    int[] alternatingSums(int[] a) {
        int[] array = new int[2];
        for (int i = 0; i < a.length; i++) {
            if ((i + 1) % 2 != 0) {
                array[0] += +a[i];
            } else {
                array[1] += a[i];
            }
        }
        return array;
    }

    /**
     * Given a rectangular matrix of characters, add a border of asterisks(*) to it.
     */
    String[] addBorder(String[] picture) {
        String[] string = new String[picture.length + 2];
        int max = 0;
        for (int i = 0; i < picture.length; i++) {
            string[i + 1] = "*" + picture[i] + "*";
            if (string[i + 1].length() > max) {
                max = string[i + 1].length();
            }
        }
        string[0] = "";
        string[picture.length + 1] = "";
        for (int i = 0; i < max; i++) {
            string[0] += "*";
            string[picture.length + 1] += "*";
        }
        return string;
    }

    /**
     * Two arrays are called similar if one can be obtained from another by swapping at most one
     * pair of elements in one of the arrays.
     * Given two arrays a and b, check whether they are similar.
     */
    boolean areSimilar(int[] a, int[] b) {
        int count = 0;
        int s1 = 1;
        int s2 = 1;
        if (a.length != b.length) {
            return false;
        } else {
            for (int i = 0; i < a.length; i++) {
                if (a[i] != b[i]) {
                    count++;
                }
                s1 *= a[i];
                s2 *= b[i];
            }
        }
        return count <= 2 && s1 == s2;
    }

    /**
     * You are given an array of integers. On each move you are allowed to increase exactly one of
     * its element by one. Find the minimal number of moves required to obtain a strictly
     * increasing sequence from the input.
     */
    int arrayChange(int[] inputArray) {
        int sum = 0;
        for (int i = 0; i < inputArray.length - 1; i++) {
            if (inputArray[i] >= inputArray[i + 1]) {
                sum = sum + inputArray[i] + 1 - inputArray[i + 1];
                inputArray[i + 1] = inputArray[i] + 1;
            }
        }
        return sum;
    }

    /**
     * Given a string, find out if its characters can be rearranged to form a palindrome.
     */
    boolean palindromeRearranging(String inputString) {
        int count = 0;
        char check = '.', temp;
        boolean flag = false;
        for (int i = 0; i < inputString.length(); i++) {
            temp = inputString.charAt(i);
            count = 0;
            for (int j = 0; j < inputString.length(); j++) {
                if (temp == inputString.charAt(j)) {
                    count++;
                }
            }
            if (count % 2 == 1) {
                if (flag == true && check != temp) {
                    return false;
                } else {
                    check = temp;
                    flag = true;
                }
            }
        }
        return true;
    }
}
