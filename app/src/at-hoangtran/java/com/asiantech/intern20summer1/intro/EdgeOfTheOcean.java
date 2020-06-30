package com.asiantech.intern20summer1.intro;

import java.lang.reflect.Array;

public class EdgeOfTheOcean {
    public static void main(String[] args) {
        int[] intArray = new int[]{1, 2, 4, 5, 6, 8, 9, 12};
        int[] intArray2 = new int[]{1, 5, 2, 2, 6, 8, 8, 6, 4};
        int[][] matrix = {{0, 1, 1, 2}, {0, 5, 0, 0}, {2, 0, 3, 3}};
        System.out.println(adjacentElementsProduct(intArray));
        System.out.println(shapeArea(6));
        System.out.println(makeArrayConsecutive2(intArray));
        System.out.println(almostIncreasingSequence(intArray2));
        System.out.println(matrixElementsSum(matrix));
    }

    static int adjacentElementsProduct(int[] inputArray) {
//        Given an array of integers, find the pair of adjacent elements that has the largest product
//        and return that product.

        int max = inputArray[0] * inputArray[1];
        int size = inputArray.length;
        for (int i = 0; i < size - 1; i++) {
            if (inputArray[i] * inputArray[i + 1] > max) {
                max = inputArray[i] * inputArray[i + 1];
            }
        }
        return max;
    }

    static int shapeArea(int n) {
//        Below we will define an n -interesting polygon.Your task is to find the area of a polygon
//        for a given n.
//
//                A 1 - interesting polygon is just a square with a side of length 1. An n
//        -interesting polygon is obtained by taking the n - 1 - interesting polygon and appending
//        1 - interesting polygons to its rim, side by side.You can see the 1 -, 2 -, 3 - and
//        4 - interesting polygons in the picture below.
        int area = (n - 1) * (n - 1) + n * n;
        return area;

    }

    static int makeArrayConsecutive2(int[] statues) {
//        Ratiorg got statues of different sizes as a present from CodeMaster for his birthday, each
//        statue having an non -negative integer size.Since he likes to make things perfect, he
//        wants to arrange them from smallest to largest so that each statue will be bigger than
//        the previous one exactly by 1. He may need some additional statues to be able to
//        accomplish that.Help him figure out the minimum number of additional statues needed.

        int count = 0;
        int min = statues[0];
        int max = statues[0];
        int n = 0;
        for (int i = 0; i < statues.length; i++) {
            if (statues[i] > max) {
                max = statues[i];
            }
            if (statues[i] < min) {
                min = statues[i];
            }
        }
        for (int i = min; i < max; i++) {
            for (int j = 0; j < statues.length; j++) {
                if (i == statues[j]) {
                    count++;
                    break;
                }
            }
        }
        return max - min - count;
    }

    static boolean almostIncreasingSequence(int[] sequence) {
//        Given a sequence of integers as an array, determine whether it is possible to obtain
//        a strictly increasing sequence by removing no more than one element from the array.
//
//        Note:
//        sequence a0, a1, ...,an is considered to be a strictly increasing if a0<a1< ... <
//        an.Sequence containing only one element is also considered to be strictly increasing.

            int count = 0;
            int dupcount = 0;
            int l = sequence.length;
            for (int i = 0; i < l - 1; i++) {
                if (sequence[i + 1] <= sequence[i]) {
                    count++;
                    if (i > 1) {
                        if (sequence[i + 1] < sequence[i - 1] && i + 1 != l - 1) {
                            return false;
                        }
                    }
                }
            }
            for (int i = 0; i < l; i++) {
                int counti = 0;
                for (int j = 0; j < l; j++) {
                    if (sequence[j] == sequence[i]) {
                        counti++;
                    }
                }
                if (counti > 1) {
                    dupcount++;
                }
            }
            if (dupcount > 2 || count > 1) {
                return false;
            }
            return true;
        }



    static int matrixElementsSum(int[][] matrix) {
//        After becoming famous, the CodeBots decided to move into a new building together.Each of
//        the rooms has a different cost, and some of them are free, but there
//        's a rumour that all the free rooms are haunted! Since the CodeBots are quite superstitious,
//        they refuse to stay in any of the free rooms, or any of the rooms below any of the free rooms.
//
//        Given matrix, a rectangular matrix of integers, where each value represents the cost of
//        the room, your task is to return the total sum of all rooms that are suitable for
//        the CodeBots (ie:add up all the values that don 't appear below a 0).
        int col = matrix.length;
        int row = matrix[0].length;
        int sum = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[j][i] == 0) {
                    break;
                } else {
                    sum += matrix[j][i];
                }
            }
        }
        return sum;
    }


}

