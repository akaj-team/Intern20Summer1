package com.asiantech.intern20summer1.intro;

import java.util.HashMap;

public class ExploringTheWaters {
    public static void main(String[] args) {
        //RUN main() with Coverage
        System.out.print("Ex 14: [ ");
        for (Integer item : alternatingSums(new int[]{50, 60, 60, 45, 70})) {
            System.out.print(item + " ");
        }
        System.out.println("]");

        System.out.println("Ex 15: [ ");
        for (String item : addBorder(new String[]{"abc", "ded"})) {
            System.out.println(item);
        }
        System.out.println("]");

        System.out.println("Ex 16: " + areSimilar(new int[]{1, 2, 3}, new int[]{2, 1, 3}));

        System.out.println("Ex 17: " + arrayChange(new int[]{2, 1, 10, 1}));

        System.out.println("Ex 18: " + palindromeRearranging("abbcabb"));
    }

    static int[] alternatingSums(int[] a) {
        /**
         * Several people are standing in a row and need to be divided into two teams.
         * The first person goes into team 1,
         * the second goes into team 2,
         * the third goes into team 1 again,
         * the fourth into team 2, and so on.
         *
         * You are given an array of positive integers - the weights of the people.
         * Return an array of two integers, where the first element is the total weight of team 1,
         * and the second element is the total weight of team 2 after the division is complete.
         */
        int[] result = new int[2];
        for (int i = 0; i < a.length; i++) {
            if (i % 2 == 0) {
                result[0] += a[i];
            } else {
                result[1] += a[i];
            }
        }
        return result;
    }

    static String[] addBorder(String[] picture) {
        /**
         * Given a rectangular matrix of characters,
         * add a border of asterisks(*) to it.
         */
        String[] result = new String[picture.length + 2];
        String asterisks = "*";
        result[0] = result[result.length - 1] = "";
        for (int i = 0; i < picture[0].length() + 2; i++) {
            result[0] = result[result.length - 1] += asterisks;
        }
        for (int i = 1; i < result.length - 1; i++) {
            result[i] = asterisks + picture[i - 1] + asterisks;
        }
        return result;
    }

    static boolean areSimilar(int[] a, int[] b) {
        /**
         * Two arrays are called similar if one can be obtained from another
         * by swapping at most one pair of elements in one of the arrays.
         *
         * Given two arrays a and b, check whether they are similar.
         */
        if (a.length != b.length) return false;
        int count = 0;
        int aDiff = 0, bDiff = 0, iDiff = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i] && count == 0) {
                aDiff = a[i];
                bDiff = b[i];
                iDiff = i;
                count++;
                continue;
            }
            if (a[i] != b[i] && count == 1) {
                if ((a[i] != bDiff || b[i] != aDiff)) {
                    return false;
                } else {
                    a[iDiff] = a[i];
                    a[i] = aDiff;
                    break;
                }
            }
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    static int arrayChange(int[] a) {
        /**
         * You are given an array of integers.
         * On each move you are allowed to increase exactly one of its element by one.
         * Find the minimal number of moves required to obtain a strictly increasing sequence
         * from the input.
         */
        int times = 0;
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] >= a[i]) {
                times += (a[i - 1] - a[i] + 1);
                a[i] = a[i - 1] + 1;
            }
        }
        return times;
    }

    static boolean palindromeRearranging(String a) {
        /**
         * Given a string, find out if its characters can be rearranged to form a palindrome.
         */
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < a.length(); i++) {
            char ch = a.charAt(i);
            if (map.containsKey(ch)) {
                map.replace(ch, map.get(ch) + 1);
            } else {
                map.put(ch, 1);
            }
            ;
        }
        int count = 0;
        for (Character key : map.keySet()) {
            if ((map.get(key) % 2) != 0) {
                count++;
                if (count == 2) return false;
            }
            ;
        }
        return true;
    }

}
