package com.asiantech.intern20summer1.intro;

import java.util.ArrayList;
import java.util.List;

public class RainbowOfClarity {
    /**
     * Write a function that checks if the given character is a digit or not.
     */
    boolean isDigit(char symbol) {
        return Character.isDigit(symbol);
    }

    /**
     * Write a function that returns its encoding.
     */
    String lineEncoding(String s) {
        s += "#";
        int count = 1;
        String result = "";

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                count++;
            } else {
                if (count > 1) {
                    result += count;
                }
                result += s.charAt(i - 1);
                count = 1;
            }
        }

        return result;
    }

    /**
     * Write a function that returns number of valid move of the knight.
     */
    int chessKnight(String cell) {
        int[][] matrix = new int[8][8];
        int count = 0;
        int i1Cell = 7 - (cell.charAt(0) - 97);
        int i2Cell = cell.charAt(1) - 49;
        int X[] = {2, 1, -1, -2, -2, -1, 1, 2};
        int Y[] = {1, 2, 2, 1, -1, -2, -2, -1};

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = 0;
            }
        }

        for (int i = 0; i < 8; i++) {

            int x = i1Cell + X[i];
            int y = i2Cell + Y[i];

            if (x >= 0 && y >= 0 && x < matrix.length && y < matrix[0].length
                    && matrix[x][y] == 0)
                count++;
        }

        return count;
    }

    /**
     * Write a function that returns the maximal number you can obtain
     * by deleting exactly one digit of the given number.
     */
    int deleteDigit(int n) {
        String temp = "";
        int newNumber = 0;
        List<Integer> listNumbers = new ArrayList<Integer>();

        while (n > 0) {
            listNumbers.add(n % 10);
            n /= 10;
        }

        for (int i = 0; i < listNumbers.size(); i++) {
            for (int j = listNumbers.size() - 1; j >= 0; j--) {
                if (j != i) {
                    temp += listNumbers.get(j);
                }
            }

            if (Integer.parseInt(temp) > newNumber) {
                newNumber = Integer.parseInt(temp);
            }
            temp = "";
        }
        return newNumber;
    }
}
