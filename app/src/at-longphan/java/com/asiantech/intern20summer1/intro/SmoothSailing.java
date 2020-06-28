package com.asiantech.intern20summer1.intro;

import java.util.ArrayList;
import java.util.Collections;

public class SmoothSailing {
    public static void main(String[] args) {
        //RUN main() with Coverage
        String[] arrayString = allLongestStrings(new String[]{"aba", "aa", "ad", "vcd", "aba"});
        System.out.print("Ex 9: [ ");
        for (String str : arrayString) {
            System.out.print(str + " ");
        }
        System.out.println("]");

        System.out.println("Ex 10: " + commonCharacterCount("aabcc", "adcaa"));

        System.out.println("Ex 11: " + isLucky(1203));

        System.out.print("Ex 12: [ ");
        for (Integer item : sortByHeight(new int[]{-1, 160, 190, -1, 170, 180})) {
            System.out.print(item + " ");
        }
        System.out.println("]");

        System.out.println("Ex 13: " + reverseInParentheses("foo(bar(baz))blim"));

    }

    static String[] allLongestStrings(String[] inputArray) {
        /**
         * Given an array of strings,
         * return another array containing all of its longest strings.
         */
        String array = "";
        int maxLength = 0;
        for (String str : inputArray) {
            if (str.length() > maxLength) {
                maxLength = str.length();
                array = str + ",";
            } else if (str.length() == maxLength) {
                array += str + ",";
            }
        }
        return array.split(",");
    }

    static int commonCharacterCount(String s1, String s2) {
        /**
         * Given two strings, find the number of common characters between them.
         */
        int count = 0;
        int s1Length = s1.length();
        for (int i = 0; i < s1Length; i++) {
            char ch = s1.charAt(i);
            if (s2.indexOf(ch) != -1) {
                count++;
                s2 = charRemoveAt(s2, s2.indexOf(ch));
            }
        }
        return count;
    }

    static String charRemoveAt(String str, int charIndex) {
        /**
         * Remove a char from string
         */
        return str.substring(0, charIndex) + str.substring(charIndex + 1);
    }

    static boolean isLucky(int n) {
        /**
         * Ticket numbers usually consist of an even number of digits.
         * A ticket number is considered lucky if the sum of
         * the first half of the digits is equal to the sum of the second half.
         *
         * Given a ticket number n, determine if it's lucky or not.
         */
        String str = Integer.toString(n);
        int i = 0, result = 0;
        for (; i < str.length() / 2; i++) {
            result += str.charAt(i);
        }
        for (; i < str.length(); i++) {
            result -= str.charAt(i);
        }
        return result == 0;
    }

    static int[] sortByHeight(int[] a) {
        /**
         * Some people are standing in a row in a park.
         * There are trees between them which cannot be moved.
         * Your task is to rearrange the people by their heights
         * in a non-descending order without moving the trees.
         * People can be very tall!
         */
        ArrayList<Integer> b = new ArrayList<>();
        for (Integer x : a) {
            if (x != -1) b.add(x);
        }
        Collections.sort(b);
        int listIndex = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != -1) {
                a[i] = b.get(listIndex);
                listIndex++;
            }
        }
        return a;
    }

    static String reverseInParentheses(String a) {
        /**
         * Write a function that reverses characters
         * in (possibly nested) parentheses
         * in the input string.
         *
         * Input strings will always be well-formed with matching ()s.
         */
        int firstIndex, lastIndex = 0;
        while (a.contains("(")) {
            firstIndex = a.lastIndexOf("(");
            lastIndex = a.indexOf(")", firstIndex);

            StringBuilder reverseStr = new StringBuilder(a.substring(firstIndex + 1, lastIndex));
            reverseStr = reverseStr.reverse();

            a = a.substring(0, firstIndex) + reverseStr + a.substring(lastIndex + 1);
        };
        return a;
    }

}
