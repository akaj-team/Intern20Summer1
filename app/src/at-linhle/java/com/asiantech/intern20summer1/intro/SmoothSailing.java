package com.asiantech.intern20summer1.intro;

import java.util.ArrayList;
import java.util.Stack;

public class SmoothSailing {
    /**
     * Given an array of strings, return another array containing all of its longest strings
     */
    String[] allLongestStrings(String[] inputArray) {
        int size = 0;
        int max = 0;
        int temp = 0;
        for (int i = 0; i < inputArray.length; i++) {
            if (max < inputArray[i].length()) {
                max = inputArray[i].length();
            }
        }
        for (int j = 0; j < inputArray.length; j++) {
            if (inputArray[j].length() == max)
                size++;
        }
        String[] outputArray = new String[size];
        for (int k = 0; k < inputArray.length; k++) {
            if (inputArray[k].length() == max) {
                outputArray[temp] = inputArray[k];
                temp++;
            }
        }
        return outputArray;
    }

    /**
     * Given two strings, find the number of common characters between them.
     */
    int commonCharacterCount(String s1, String s2) {
        int count = 0;
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                if (!arr.contains(j) && s1.charAt(i) == s2.charAt(j)) {
                    count++;
                    arr.add(j);
                    break;
                }
            }
        }
        System.out.print(arr);
        return count;
    }

    /**
     * Ticket numbers usually consist of an even number of digits. A ticket number is considered
     * lucky if the sum of the first half of the digits is equal to the sum of the second half.
     * Given a ticket number n, determine if it's lucky or not.
     */
    boolean isLucky(int n) {
        String string = n + "";
        int sum = 0;
        for (int i = 0; i < string.length() / 2; i++) {
            sum = sum + (string.charAt(i) - string.charAt(string.length() - 1 - i));
        }
        return sum == 0;
    }

    /**
     * Some people are standing in a row in a park. There are trees between them which cannot
     * be moved. Your task is to rearrange the people by their heights in a non-descending order
     * without moving the trees. People can be very tall!
     */
    int[] sortByHeight(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j] && a[i] != -1 && a[j] != -1) {
                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
        return a;
    }

    /**
     * Write a function that reverses characters in (possibly nested) parentheses in the input
     * string. Input strings will always be well-formed with matching ()s.
     */
    String reverseInParentheses(String inputString) {

        Stack<Character> stack = new Stack<Character>();
        StringBuilder string = new StringBuilder("");
        StringBuilder temp = new StringBuilder("");

        for (int i = 0; i < inputString.length(); i++) {
            if (inputString.charAt(i) == '(') {
                stack.push(inputString.charAt(i));
            } else {
                if (!stack.isEmpty()) {
                    if (inputString.charAt(i) == ')') {
                        while (stack.peek() != '(') {
                            temp.append(stack.peek());
                            stack.pop();
                        }
                        stack.pop();
                        if (!stack.isEmpty()) {
                            for (int j = 0; j < temp.length(); j++) {
                                stack.push(temp.charAt(j));
                            }

                        } else {
                            string.append(temp);
                        }
                        temp = new StringBuilder();
                    } else {
                        stack.push(inputString.charAt(i));
                    }
                } else {
                    string.append(String.valueOf(inputString.charAt(i)));
                }
            }
        }
        return string.toString();
    }
}
