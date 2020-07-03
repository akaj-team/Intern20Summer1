package com.asiantech.intern20summer1.intro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LandOfLogic {
    /**
     * Define a word as a sequence of consecutive English letters. Find the longest word from the
     * given string.
     */
    String longestWord(String text) {
        String longest = "";
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if ((text.charAt(i) >= 'A' && text.charAt(i) <= 'Z')
                    || (text.charAt(i) >= 'a' && text.charAt(i) <= 'z')) {
                temp.append(text.charAt(i));
            } else {
                if (temp.length() > longest.length()) {
                    longest = temp.toString();
                }
                temp = new StringBuilder();
            }
        }
        return temp.length() > longest.length() ? temp.toString() : longest;
    }

    /**
     * Check if the given string is a correct time representation of the 24-hour clock.
     */
    boolean validTime(String time) {
        String[] temp = time.split(":");
        return (Integer.parseInt(temp[0]) >= 0 && Integer.parseInt(temp[0]) <= 23)
                && (Integer.parseInt(temp[1]) >= 0 && Integer.parseInt(temp[1]) <= 59);
    }

    /**
     * CodeMaster has just returned from shopping. He scanned the check of the items he bought and
     * gave the resulting string to Ratiorg to figure out the total number of purchased items.
     * Since Ratiorg is a bot he is definitely going to automate it, so he needs a program that
     * sums up all the numbers which appear in the given input.
     * Help Ratiorg by writing a function that returns the sum of numbers that appear in the given
     * inputString.
     */
    int sumUpNumbers(String inputString) {
        int sum = 0;
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < inputString.length(); i++) {
            if (Character.isDigit(inputString.charAt(i))) {
                temp.append(inputString.charAt(i));
            } else {
                if (temp.length() > 0) {
                    sum += Integer.parseInt(temp.toString());
                    temp = new StringBuilder();
                }
            }
        }
        return (temp.length() > 0) ? sum + Integer.parseInt(temp.toString()) : sum;
    }

    /**
     * Given a rectangular matrix containing only digits, calculate the number of different 2 × 2
     * squares in it.
     */
    int differentSquares(int[][] matrix) {
        List<String> lStrings = new ArrayList<>();
        for (int i = 0; i < matrix.length - 1; i++) {
            for (int j = 0; j < matrix[0].length - 1; j++) {
                String str = String.valueOf(matrix[i][j]);
                str += "" + matrix[i][j + 1];
                str += "" + matrix[i + 1][j];
                str += "" + matrix[i + 1][j + 1];
                if (!lStrings.contains(str)) {
                    lStrings.add(str);
                }
            }
        }
        return lStrings.size();
    }

    /**
     * Given an integer product, find the smallest positive (i.e. greater than 0) integer the
     * product of whose digits is equal to product. If there is no such integer, return -1 instead.
     */
    int digitsProduct(int product) {
        if (product == 0) {
            return 10;
        }
        if (product == 1) {
            return 1;
        }
        StringBuilder str = new StringBuilder();
        for (int i = 9; i > 1; i--) {
            while (product % i == 0) {
                product /= i;
                str.insert(0, i + "");
            }
        }

        if (product > 1) {
            return -1;
        }
        return Integer.parseInt(str.toString());
    }

    /**
     * You are given an array of desired filenames in the order of their creation. Since two files
     * cannot have equal names, the one which comes later will have an addition to its name in a
     * form of (k), where k is the smallest positive integer such that the obtained name is not
     * used yet.
     * <p>
     * Return an array of names that will be given to the files.
     */
    String[] fileNaming(String[] names) {
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < names.length; i++) {
            String temp = names[i];
            if (!set.contains(names[i])) {
                set.add(names[i]);
            } else {
                int count = 1;
                String check = temp;
                while (set.contains(check)) {
                    check = temp + "(" + count + ")";
                    count++;
                }
                set.add(check);
                names[i] = check;
            }
        }
        return names;
    }

    /**
     * You are taking part in an Escape Room challenge designed specifically for programmers.
     * In your efforts to find a clue, you've found a binary code written on the wall behind a vase,
     * and realized that it must be an encrypted message. After some thought, your first guess is
     * that each consecutive 8 bits of the code stand for the character with the corresponding
     * extended ASCII code.
     * Assuming that your hunch is correct, decode the message.
     */
    String messageFromBinaryCode(String code) {
        StringBuilder temp = new StringBuilder();
        while (code.length() != 0) {
            temp.append((char) Integer.parseInt(code.substring(0, 8), 2));
            code = code.substring(8);
        }
        return temp.toString();
    }

    /**
     * Construct a square matrix with a size N × N containing integers from 1 to N * N in a spiral
     * order, starting from top-left and in clockwise direction.
     */
    int[][] spiralNumbers(int n) {
        if (n == 0) {
            return new int[0][];
        }
        int[][] matrix = new int[n][n];
        int start = 0;
        int end = n - 1;
        int num = 1;
        while (start < end) {
            for (int col = start; col < end; col++) {
                matrix[start][col] = num++;
            }
            for (int row = start; row < end; row++) {
                matrix[row][end] = num++;
            }
            for (int col = end; col > start; col--) {
                matrix[end][col] = num++;
            }
            for (int row = end; row > start; row--) {
                matrix[row][start] = num++;
            }
            start++;
            end--;
        }
        if (start == end) {
            matrix[start][start] = num++;
        }
        return matrix;
    }

    /**
     * Sudoku is a number-placement puzzle. The objective is to fill a 9 × 9 grid with digits so
     * that each column, each row, and each of the nine 3 × 3 sub-grids that compose the grid
     * contains all of the digits from 1 to 9.
     * This algorithm should check if the given grid of numbers represents a correct solution to
     * Sudoku.
     */
    boolean sudoku(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length - 1; j++) {
                for (int k = j + 1; k < grid[0].length; k++) {
                    if (grid[i][j] == grid[i][k]) {
                        return false;
                    } else if (grid[j][i] == grid[k][i]) {
                        return false;
                    }
                }
            }
        }
        for (int lb = 0; lb < 9; lb += 3) {
            for (int cb = 0; cb < 9; cb += 3) {
                System.out.println(lb + " " + cb);
                HashSet<Integer> number = new HashSet<>();
                for (int i = lb; i < lb + 3; i++) {
                    for (int j = cb; j < cb + 3; j++) {
                        if (!number.add(grid[i][j])) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
