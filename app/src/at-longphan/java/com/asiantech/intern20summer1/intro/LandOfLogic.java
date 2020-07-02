package com.asiantech.intern20summer1.intro;

import java.util.ArrayList;
import java.util.List;

public class LandOfLogic {
    public static void main(String[] args) {
        //RUN main() with Coverage
        System.out.println("Ex 52: " + longestWord("Ready, steady, go!"));

        System.out.println("Ex 53: " + validTime("13:58"));

        System.out.println("Ex 54: " + sumUpNumbers("2 apples, 12 oranges"));

        System.out.println("Ex 55: " + differentSquares(new int[][]{{1, 2, 1}, {2, 2, 2}, {2, 2, 2}, {1, 2, 3}}));

        System.out.println("Ex 56: " + digitsProduct(450));

        System.out.println("Ex 57: " + fileNaming(new String[]{"doc", "doc", "image", "doc(1)", "doc"}));

        System.out.println("Ex 58: " + messageFromBinaryCode("010010000110010101101100011011000110111100100001"));

        System.out.println("Ex 59: ");
        int[][] output = spiralNumbers(3);
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[0].length; j++) {
                System.out.print(output[i][j] + "\t");
            }
            System.out.println();
        }

        int[][] sudokuMap = new int[][]{{1, 3, 2, 5, 4, 6, 9, 8, 7}, {4, 6, 5, 8, 7, 9, 3, 2, 1}, {7, 9, 8, 2, 1, 3, 6, 5, 4},
                {9, 2, 1, 4, 3, 5, 8, 7, 6}, {3, 5, 4, 7, 6, 8, 2, 1, 9}, {6, 8, 7, 1, 9, 2, 5, 4, 3},
                {5, 7, 6, 9, 8, 1, 4, 3, 2}, {2, 4, 3, 6, 5, 7, 1, 9, 8}, {8, 1, 9, 3, 2, 4, 7, 6, 5}};
        System.out.println("Ex 60: " + sudoku(sudokuMap));


    }

    static String longestWord(String text) {
        /**
         * Define a word as a sequence of consecutive English letters.
         * Find the longest word from the given string.
         */
        String[] x = text.split("[^a-zA-Z]");
        String longestWord = "";
        int max = 0;
        for (int i = 0; i < x.length; i++) {
            if (x[i].length() > max) {
                max = x[i].length();
                longestWord = x[i];
            }
        }
        return longestWord;
    }

    static boolean validTime(String time) {
        /**
         * Check if the given string is a correct time representation
         * of the 24-hour clock.
         */
        int h = Integer.valueOf(time.substring(0, 2));
        int m = Integer.valueOf(time.substring(3));
        if (h < 0 || h > 23) return false;
        if (m < 0 || m > 59) return false;
        if (h == 24 && m == 0) return false;
        return true;
    }

    static int sumUpNumbers(String inputString) {
        /**
         * CodeMaster has just returned from shopping.
         * He scanned the check of the items he bought
         * and gave the resulting string to Ratiorg to figure out
         * the total number of purchased items.
         * Since Ratiorg is a bot he is definitely going to automate it,
         * so he needs a program that sums up all the numbers which appear in the given input.
         *
         * Help Ratiorg by writing a function
         * that returns the sum of numbers that appear in the given inputString.
         */
        String[] str = inputString.split("[^0-9]");
        int sum = 0;
        for (int i = 0; i < str.length; i++) {
            if (!str[i].equals("")) {
                sum += Integer.valueOf(str[i]);
            }
        }
        return sum;
    }

    static int differentSquares(int[][] matrix) {
        /**
         * Given a rectangular matrix containing only digits,
         * calculate the number of different 2 × 2 squares in it.
         */
        int row = matrix.length;
        int col = matrix[0].length;
        //list contain value of each different square
        ArrayList<String> list = new ArrayList<>();
        //loop to get value of all squares
        for (int i = 0; i < row - 1; i++) {
            for (int j = 0; j < col - 1; j++) {
                // take each 4 digits to a square
                String square = "";
                int toFour = 0;
                for (int i2 = i; i2 < i + 2; i2++) {
                    for (int j2 = j; j2 < j + 2; j2++) {
                        toFour++;
                        square += matrix[i2][j2];
                        if (toFour == 4) {
                            boolean isDuplicate = true;
                            //check if duplicate
                            for (int k = 0; k < list.size(); k++) {
                                if (square.equals(list.get(k))) {
                                    isDuplicate = false;
                                    break;
                                }
                            }
                            if (isDuplicate) {
                                list.add(square);
                            }
                        }
                    }
                }
            }
        }
        return list.size();
    }

    static int digitsProduct(int product) {
        /**
         * Given an integer product, find the smallest positive
         * (i.e. greater than 0) integer the product of whose digits is equal to product.
         * If there is no such integer, return -1 instead.
         */
        if (product == 0) return 10;
        if (product < 10) return product;

        String str = "";
        for (int i = 9; i > 1; i--) {
            for (; product % i == 0; product /= i) {
                str = i + str;
            }
        }
        return product == 1 ? new Integer(str) : -1;
    }

    static Object fileNaming(String[] names) {
        /**
         * You are given an array of desired filenames in the order of their creation.
         * Since two files cannot have equal names, the one which comes later
         * will have an addition to its name in a form of (k),
         * where k is the smallest positive integer such that the obtained name is not used yet.
         *
         * Return an array of names that will be given to the files.
         */
        List<String> result = new ArrayList<>();
        for (String item : names) {
            if (result.contains(item)) {
                int i = 1;
                while (result.contains(item + "(" + i + ")")) {
                    i++;
                }
                item += "(" + i + ")";
            }
            result.add(item);
        }
        return result;
    }

    static String messageFromBinaryCode(String code) {
        /**
         * You are taking part in an Escape Room challenge designed specifically for programmers.
         * In your efforts to find a clue, you've found a binary code written on the wall behind a vase,
         * and realized that it must be an encrypted message.
         * After some thought, your first guess is that each consecutive 8 bits
         * of the code stand for the character with the corresponding extended ASCII code.
         *
         * Assuming that your hunch is correct, decode the message.
         */
        String result = "";
        for (int i = 0; i < code.length() / 8; i++) {
            int a = Integer.parseInt(code.substring(i * 8, (i + 1) * 8), 2);
            result += (char) (a);
        }
        return result;
    }

    static int[][] spiralNumbers(int n) {
        /**
         * Construct a square matrix with a size N × N
         * containing integers from 1 to N * N in a spiral order,
         * starting from top-left and in clockwise direction.
         */
        int[][] a = new int[n][n];
        int top = 0;
        int right = n - 1;
        int left = 0;
        int bot = n - 1;
        for (int count = 1; count <= n * n; ) {
            for (int x = left; x <= right; x++) {
                a[top][x] = count++;
            }
            top++;
            for (int y = top; y <= bot; y++) {
                a[y][right] = count++;
            }
            right--;
            for (int x = right; x >= left; x--) {
                a[bot][x] = count++;
            }
            bot--;
            for (int y = bot; y >= top; y--) {
                a[y][left] = count++;
            }
            left++;
        }
        return a;
    }

    static boolean sudoku(int[][] grid) {
        /**
         * Sudoku is a number-placement puzzle. The objective is to fill a 9 × 9 grid
         * with digits so that each column, each row, and each of the nine 3 × 3 sub-grids
         * that compose the grid contains all of the digits from 1 to 9.
         *
         * This algorithm should check if the given grid of numbers represents a correct solution to Sudoku.
         */
        int n = grid.length;
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> oneToN = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (oneToN.contains(grid[i][j])) {
                    return false;
                } else {
                    oneToN.add(grid[i][j]);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> oneToN = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (oneToN.contains(grid[j][i])) {
                    return false;
                } else {
                    oneToN.add(grid[j][i]);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i % 3 == 0 && j % 3 == 0) {
                    ArrayList<Integer> oneToN = new ArrayList<>();
                    for (int i2 = i; i2 < i + 3; i2++) {
                        for (int j2 = j; j2 < j + 3; j2++) {
                            if (oneToN.contains(grid[i2][j2])) {
                                return false;
                            } else {
                                oneToN.add(grid[i2][j2]);
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

}
