package intro;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ExploringtheWaters {
    public static void main(String[] args) {
        /**
         * Several people are standing in a row and need to be divided into two teams. The first person goes into team 1,
         * the second goes into team 2, the third goes into team 1 again, the fourth into team 2, and so on.
         */
        int[] inputNumber = {50, 60, 60, 45, 70};
        int[] resultNumber = alternatingSums(inputNumber);
        System.out.print(resultNumber[0] + "\t" + resultNumber[1]);
        /**
         * Given a rectangular matrix of characters, add a border of asterisks(*) to it.
         */
        String[] inputArray = {"sa", "**", "da"};
        String[] resultArray = addBorder(inputArray);
        System.out.println();
        for (String item : resultArray) {
            System.out.println(item);
        }
        /**
         * Two arrays are called similar if
         * one can be obtained from another by swapping at most one pair of elements in one of the arrays.
         */
        int[] arrayA = {1, 2, 3};
        int[] arrayB = {3, 2, 1};
        System.out.println(areSimilar(arrayA, arrayB));
        /**
         * You are given an array of integers. On each move you are allowed to increase exactly one of
         *its element by one. Find the minimal number of moves required to obtain a strictly increasing sequence from the input.
         */
        int[] inputNumber2 = {1, 1, 1};
        System.out.println("17. ArrayChane:" + arrayChange(inputNumber2));
    }

    static int[] alternatingSums(int[] a) {
        int[] result = new int[2];
        result[0] = a[0];
        for (int i = 1; i < a.length; i++) {
            if (i % 2 == 0) {
                result[0] = result[0] + a[i];
            } else {
                result[1] = result[1] + a[i];
            }
        }
        return result;
    }

    static String[] addBorder(String[] picture) {
        String[] result = new String[picture.length + 2];
        String start = "";

        for (int i = 0; i < picture[0].length() + 2; i++) {
            start = start + "*";
        }
        result[0] = start;
        result[picture.length + 1] = start;
        for (int j = 0; j < picture.length; j++) {
            result[j + 1] = ("*" + picture[j] + "*");
        }

        return result;
    }

    static boolean areSimilar(int[] a, int[] b) {
        int count = 0;
        int[] arrayA = Arrays.copyOf(a, a.length);
        int[] arrayB = Arrays.copyOf(b, b.length);
        if (a.length != b.length) {
            return false;
        }
        Arrays.sort(a);
        Arrays.sort(b);
        if (Arrays.equals(a, b) != true) {
            return false;
        } else {
            for (int i = 0; i < arrayA.length; i++) {
                if (arrayA[i] != arrayB[i]) {
                    count++;
                }
            }
        }
        if (count == 2 || count == 0) {
            return true;
        }
        return false;
    }

    static int arrayChange(int[] inputArray) {
        int legth = inputArray.length;
        int count = 0;
        for (int i = 0; i < inputArray.length - 1; i++) {
            if (inputArray[i] >= inputArray[i + 1]) {
                do {
                    inputArray[i + 1] += 1;
                    count++;
                } while (inputArray[i] >= inputArray[i + 1]);
            }
        }
        return count;
    }
    static boolean palindromeRearranging(String inputString) {
        Set<Character> oddLetters = new HashSet<>();
        for (char item : inputString.toCharArray()) {
            if ( ! oddLetters.remove(item) ) {
                oddLetters.add(item);
            }
        }
        if (oddLetters.size() <2){
            return true;
        }
        return false;
    }
}

