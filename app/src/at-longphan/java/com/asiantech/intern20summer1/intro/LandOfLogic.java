package com.asiantech.intern20summer1.intro;

import java.util.ArrayList;

public class LandOfLogic {
    public static void main(String[] args) {
        //RUN main() with Coverage
        System.out.println("Ex 52: " + longestWord("Ready, steady, go!"));

        System.out.println("Ex 53: " + validTime("13:58"));

        System.out.println("Ex 54: " + sumUpNumbers("2 apples, 12 oranges"));

        System.out.println("Ex 55: " + differentSquares(new int[][]{{1, 2, 1}, {2, 2, 2}, {2, 2, 2}, {1, 2, 3}}));

        System.out.println("Ex 56: " + digitsProduct(450));
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

}
