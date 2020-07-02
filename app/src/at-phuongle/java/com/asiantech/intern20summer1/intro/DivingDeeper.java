package com.asiantech.intern20summer1.intro;

import java.util.ArrayList;
import java.util.List;

public class DivingDeeper {
    /**
     * Write a funtion that removes each k th element from the given array.
     */
    Integer[] extractEachKth(int[] inputArray, int k) {
        int count = 1;
        List<Integer> invalidCases = new ArrayList<>();
        ArrayList<Integer> newArray = new ArrayList<>();

        for (int i = 0; i < inputArray.length; i++) {
            newArray.add(inputArray[i]);
        }

        for (int i = 0; i < inputArray.length; i++) {
            if (count == k) {
                invalidCases.add(inputArray[i]);
                count = 0;
            }
            count++;
        }

        for (int i = 0; i < invalidCases.size(); i++) {
            if (newArray.contains(invalidCases.get(i))) {
                newArray.remove(invalidCases.get(i));
            }
        }

        return newArray.toArray(new Integer[newArray.size()]);
    }

    /**
     * Write a funtion that find the leftmost digit that occurs in a given string.
     */
    char firstDigit(String inputString) {
        for (int i = 0; i < inputString.length(); i++) {
            if (Character.isDigit(inputString.charAt(i))) {
                return inputString.charAt(i);
            }
        }

        return '0';
    }

    /**
     * Write a funtion that finds the number of different characters in the given string.
     */
    int differentSymbolsNaive(String s) {
        List<Character> listDifferentChar = new ArrayList<Character>();

        for (int i = 0; i < s.length(); i++) {
            if (!listDifferentChar.contains(s.charAt(i))) {
                listDifferentChar.add(s.charAt(i));
            }
        }

        return listDifferentChar.size();
    }

    /**
     * Write a funtion that returns the maximal possible sum of some of its k consecutive elements.
     */
    int arrayMaxConsecutiveSum(int[] inputArray, int k) {
        int sum;
        int count;
        int maxSum = 0;
        for (int i = 0; i < inputArray.length; i++) {
            sum = 0;
            count = k;
            try {
                while (count > 0) {
                    sum += inputArray[i + count - 1];
                    count--;
                }

            } catch (Exception ex4) {

            }
            if (sum > maxSum) {
                maxSum = sum;
            }
        }
        return maxSum;
    }
}
