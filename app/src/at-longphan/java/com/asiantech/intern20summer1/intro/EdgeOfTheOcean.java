package com.asiantech.intern20summer1.intro;

import java.util.Arrays;

public class EdgeOfTheOcean {
    public static void main(String[] args) {
        //RUN main() with Coverage
        System.out.println("Ex 4: " + adjacentElementsProduct(new int[]{3, 6, -2, -5, 7, 3}));
        System.out.println("Ex 5: " + shapeArea(3));
        System.out.println("Ex 6: " + makeArrayConsecutive2(new int[]{6, 2, 3, 8}));
        System.out.println("Ex 7: " + almostIncreasingSequence(new int[]{1, 2, 1, 2}));
        System.out.println("Ex 8: " + matrixElementsSum(new int[][]{{0, 1, 1, 2}, {0, 5, 0, 0}, {2, 0, 3, 3}}));
    }

    static int adjacentElementsProduct(int[] inputArray) {
        /**
         * Given an array of integers, find the pair of adjacent elements
         * that has the largest product and return that product.
         */
        int max = inputArray[0] * inputArray[1];
        for (int i = 1; i < inputArray.length - 1; i++) {
            if (inputArray[i] * inputArray[i + 1] > max) {
                max = inputArray[i] * inputArray[i + 1];
            }
        }
        return max;
    }

    static int shapeArea(int n) {
        /**
         * Below we will define an n-interesting polygon.
         * Your task is to find the area of a polygon for a given n.
         *
         * A 1-interesting polygon is just a square with a side of length 1.
         * An n-interesting polygon is obtained by taking the n - 1-interesting polygon
         * and appending 1-interesting polygons to its rim, side by side.
         * You can see the 1-, 2-, 3- and 4-interesting polygons in the picture below.
         */
        return n * n + (n - 1) * (n - 1);
    }

    static int makeArrayConsecutive2(int[] statues) {
        /**
         * Ratiorg got statues of different sizes as a present from CodeMaster for his birthday,
         * each statue having an non-negative integer size.
         * Since he likes to make things perfect,
         * he wants to arrange them from smallest to largest
         * so that each statue will be bigger than the previous one exactly by 1.
         * He may need some additional statues to be able to accomplish that.
         * Help him figure out the minimum number of additional statues needed.
         */
        Arrays.sort(statues);
        return statues[statues.length - 1] - statues[0] - 1 - (statues.length - 2);
    }

    static boolean almostIncreasingSequence(int[] a) {
        /**
         * Given a sequence of integers as an array,
         * determine whether it is possible to obtain a strictly increasing sequence
         * by removing no more than one element from the array.
         *
         * Note: sequence a0, a1, ..., an is considered to be a strictly increasing
         * if a0 < a1 < ... < an. Sequence containing only one element
         * is also considered to be strictly increasing.
         */
        if (a.length == 2) {
            return true;
        }
        int count = 0;
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] >= a[i + 1]) {
                count++;
                if (count == 2) {
                    return false;
                } else if (i > 0 && i < a.length - 2 && a[i + 2] <= a[i] && a[i + 1] <= a[i - 1]) {
                    return false;
                }
            }
        }
        return true;
    }

    static int matrixElementsSum(int[][] matrix) {
        /**
         * After becoming famous, the CodeBots decided to move into a new building together.
         * Each of the rooms has a different cost, and some of them are free,
         * but there's a rumour that all the free rooms are haunted! Since the CodeBots are quite superstitious,
         * they refuse to stay in any of the free rooms, or any of the rooms below any of the free rooms.
         *
         * Given matrix, a rectangular matrix of integers,
         * where each value represents the cost of the room,
         * your task is to return the total sum of all rooms that are suitable for the CodeBots
         * (ie: add up all the values that don't appear below a 0).
         */
        int row = matrix.length;
        int col = matrix[0].length;
        int sum = 0;
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                if (matrix[j][i] == 0) break;
                sum += matrix[j][i];
            }
        }
        return sum;
    }

}
