package com.asiantech.intern20summer1.intro;
import java.util.*;

public class SmoothSailing {
    /**
     * Write a function that sort array from min to max.
     */
    void sortASC(Integer[] arr) {
        int temp = arr[0];
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }
    }

    /**
     * Write a function that returns an array store the longest strings.
     */
    String[] allLongestStrings(String[] inputArray) {
        int longestLength = 0;
        List<String> longestStrings = new ArrayList<>();
        for (int i = 0; i < inputArray.length; i++) {
            if (inputArray[i].length() > longestLength) {
                longestLength = inputArray[i].length();
            }
        }

        for (int i = 0; i < inputArray.length; i++) {
            if (inputArray[i].length() == longestLength) {
                longestStrings.add(inputArray[i]);
            }
        }
        return longestStrings.toArray(new String[longestStrings.size()]);
    }

    /**
     * Write a function that returns the number of common characters between them.
     */
    int commonCharacterCount(String s1, String s2) {
        int count = 0;
        char[] charsArray1 = s1.toCharArray();
        char[] charsArray2 = s2.toCharArray();
        List<Character> listChar2 = new ArrayList<>();
        for (int i = 0; i < charsArray2.length; i++) {
            listChar2.add(charsArray2[i]);
        }

        for (int i = 0; i < charsArray1.length; i++) {
            for (int j = 0; j < listChar2.size(); j++) {
                if (charsArray1[i] == listChar2.get(j)) {
                    count++;
                    listChar2.remove(j);
                    break;
                }
            }
        }
        return count;
    }

    /**
     * Write a function that checks if the sum of the first half of the digits is equal to the sum of the second half.
     */
    boolean isLucky(int n) {
        List<Integer> numbers = new ArrayList<Integer>();
        int sum1 = 0, sum2 = 0;
        while (n > 0) {
            numbers.add(n % 10);
            n /= 10;
        }

        if (numbers.size() % 2 != 0) {
            return false;
        }

        for (int i = 0; i < numbers.size() / 2; i++) {
            sum1 += numbers.get(i);
            sum2 += numbers.get(numbers.size() - i - 1);
        }
        if (sum1 != sum2) {
            return false;
        }
        return true;
    }

    /**
     * Write a function that sorted array a with all the -1 untouched.
     */
    int[] sortByHeight(int[] a) {
        List<Integer> numbers = new ArrayList<Integer>();

        for (int i = 0; i < a.length; i++) {
            if (a[i] != -1) {
                numbers.add(a[i]);
            }
        }

        Collections.sort(numbers);

        for (int i = 0; i < a.length; i++) {
            if (a[i] != -1) {
                a[i] = numbers.get(0);
                numbers.remove(0);
            }
        }
        return a;
    }

    /**
     * Write a function that returns all the characters that were in parentheses reversed.
     */
    String reverseInParentheses(String inputString) {
        String tmpCh = new String("");
        String tmpChRe = new String("");
        String tmp = new String("");
        int n = 0;
        int j = 0;

        for (int i = 0; i < inputString.length(); i++) {
            if (inputString.charAt(i) == '(')
                n++;
        }

        int T[] = new int[n];

        for (int i = 0; i < inputString.length(); i++) {
            if (inputString.charAt(i) == '(') {
                T[j] = i;
                j++;
            }
        }

        j = 0;

        while (n > 0) {
            j = T[n - 1] + 1;
            while (inputString.charAt(j) != ')') {
                tmpCh = tmpCh + inputString.charAt(j);
                j++;
            }
            for (int q = tmpCh.length() - 1; q >= 0; q--) {
                tmpChRe = tmpChRe + tmpCh.charAt(q);
            }
            tmp = inputString.substring(0, T[n - 1]) + tmpChRe + inputString.substring(T[n - 1] + tmpChRe.length() + 2);
            inputString = tmp;
            n--;
            tmp = "";
            tmpCh = "";
            tmpChRe = "";
        }

        return inputString;
    }
}
