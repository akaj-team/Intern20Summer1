package com.asiantech.intern20summer1.intro;

import java.util.Arrays;

public class LandOfLogic {
    /**
     * Write a function that returns the longest word from the given string.
     */
    String longestWord(String text) {
        String[] strings = text.split("[^a-zA-Z]+");
        String longestWord = "";

        for (int i = 0; i < strings.length; i++) {
            if (strings[i].length() > longestWord.length()) {
                longestWord = strings[i];
            }
        }

        return longestWord;
    }

    /**
     * Write a function that checks if the given string is a correct time representation of the 24-hour clock.
     */
    boolean validTime(String time) {
        String[] strings = time.split("[:]");
        if (Integer.parseInt(strings[0]) < 0 || Integer.parseInt(strings[0]) > 23) {
            return false;
        }
        if (Integer.parseInt(strings[1]) < 0 || Integer.parseInt(strings[1]) > 59) {
            return false;
        }

        return true;
    }

    /**
     * Write a function that returns the sum of numbers that appear in the given inputString.
     */
    int sumUpNumbers(String inputString) {
        int sum = 0;
        String[] strings = inputString.split("[^0-9]+");

        for (int i = 0; i < strings.length; i++) {
            if (!strings[i].isEmpty()) {
                sum += Integer.parseInt(strings[i]);
            }
        }

        return sum;
    }

    /**
     * Write a function that returns the decrypted message.
     */
    String messageFromBinaryCode(String code) {
        String result = "";
        String[] strings = code.split("(?<=\\G.{8})");

        for (int i = 0; i < strings.length; i++) {
            result += (char) Integer.parseInt(strings[i], 2);
        }

        return result;
    }

    /**
     * Write a function that checks if the given array is validate.
     */
    boolean validate(int[] check) {
        int i = 0;
        Arrays.sort(check);
        for (int number : check) {
            if (number != ++i)
                return false;
        }
        return true;
    }

    /**
     * Write a function that checks if the given grid of numbers represents a correct solution to Sudoku.
     */

    boolean sudoku(int[][] grid) {
        for (int i = 0; i < 9; i++) {

            int[] row = new int[9];
            int[] square = new int[9];
            int[] column = grid[i].clone();

            for (int j = 0; j < 9; j ++) {
                row[j] = grid[j][i];
                square[j] = grid[(i / 3) * 3 + j / 3][i * 3 % 9 + j % 3];
            }
            if (!(validate(column) && validate(row) && validate(square)))
                return false;
        }
        return true;
    }
}
