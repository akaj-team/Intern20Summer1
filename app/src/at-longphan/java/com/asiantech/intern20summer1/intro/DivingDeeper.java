package com.asiantech.intern20summer1.intro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DivingDeeper {
    public static void main(String[] args) {
        //RUN main() with Coverage
        System.out.println("Ex 34: " + Arrays.toString(extractEachKth(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},3)));

        System.out.println("Ex 35: " + firstDigit("var_123__Int"));

        System.out.println("Ex 36: " + differentSymbolsNaive("cabca"));

        System.out.println("Ex 37: " + arrayMaxConsecutiveSum(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 2));
    }

    static int[] extractEachKth(int[] inputArray, int k) {
        /**
         * Given array of integers, remove each kth element from it.
         */
        ArrayList<Integer> x = new ArrayList<>();
        for(int i = 0, j = 1; i < inputArray.length; i++, j++){
            if(j == k) {
                j = 0;
            } else {
                x.add(inputArray[i]);
            }
        }
        int[] result = new int[x.size()];
        for (int i = 0; i < x.size(); i++) {
            result[i]= (x.get(i));
        }
        return result;
    }

    static char firstDigit(String inputString) {
        /**
         * Find the leftmost digit that occurs in a given string.
         */
        for(int i = 0; i < inputString.length(); i++){
            if(String.valueOf(inputString.charAt(i)).matches("[0-9]"))
                return inputString.charAt(i);
        }
        return 0;
    }

    static int differentSymbolsNaive(String s) {
        /**
         * Given a string, find the number of different characters in it.
         */
        HashMap<Character, Integer> map = new HashMap<>();
        for(int i = 0; i < s.length(); i++){
            if(!map.containsKey(s.charAt(i))) map.put(s.charAt(i), 0);
        }
        return map.size();
    }

    static int arrayMaxConsecutiveSum(int[] a, int k) {
        /**
         * Given array of integers, find the maximal possible sum of
         * some of its k consecutive elements.
         */
        int max = 0;
        int previousSum = 0;
        for(int i = 0; i <= a.length - k; i++) {
            if(i == 0) {
                for(int j = i; j < i + k; j++){
                    previousSum += a[j];
                }
            } else {
                previousSum = previousSum - a[i-1] + a[i+k-1];
            }
            if(previousSum > max) max = previousSum;
        }
        return max;
    }
}
