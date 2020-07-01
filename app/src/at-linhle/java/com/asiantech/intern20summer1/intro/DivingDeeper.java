package com.asiantech.intern20summer1.intro;

import java.util.HashSet;

public class DivingDeeper {
    /**
     * Given array of integers, remove each kth element from it.
     */
    int[] extractEachKth(int[] inputArray, int k) {
        int j = 0;
        int[] outputArray = new int[inputArray.length - inputArray.length / k];
        for (int i = 0; i < inputArray.length; i++) {
            if ((i + 1) % k != 0) {
                outputArray[j] = inputArray[i];
                j++;
            }
        }
        return outputArray;
    }

    /**
     * Find the leftmost digit that occurs in a given string.
     */
    char firstDigit(String inputString) {
        for (int i = 0; i < inputString.length(); i++) {
            if ('0' <= inputString.charAt(i) && inputString.charAt(i) <= '9') {
                return inputString.charAt(i);
            }
        }
        return 'a';
    }

    /**
     * Given a string, find the number of different characters in it.
     */
    int differentSymbolsNaive(String s) {
        HashSet<Character> temp = new HashSet<Character>();
        for (int i = 0; i < s.length(); i++) {
            temp.add(s.charAt(i));
        }
        return temp.size();
    }

    /**
     * Given array of integers, find the maximal possible sum of some of its k consecutive elements.
     */
    int arrayMaxConsecutiveSum(int[] inputArray, int k) {
        int max = 0;
        for (int i = 0; i <= inputArray.length - k; i++) {
            int sum = 0;
            for (int j = 0; j < k; j++) {
                sum = sum + inputArray[i + j];
                if (max < sum) {
                    max = sum;
                }
            }
        }
        return max;
    }
}
