package com.asiantech.intern20summer1.introJava;


public class EdgeOfTheOcean {

    public static void main(String[] args) {
        System.out.println("\nOUT: ");
    }

    /**
     * 4  **
     * Given an array of integers, find the pair of adjacent elements
     * that has the largest product and return that product.
     */


    static int adjacentElementsProduct(int[] inputArray) {

        int mProduct = inputArray[0] * inputArray[1];
        ;
        for (int i = 0; i < inputArray.length - 1; i++) {
            int productApter = inputArray[i] * inputArray[i + 1];
            if (productApter > mProduct) {
                mProduct = productApter;
            }
        }
        return mProduct;
    }

    /**
     * 5 **
     * Below we will define an n-interesting polygon.
     * Your task is to find the area of a polygon for a given n.
     * A 1-interesting polygon is just a square with a side of length 1.
     * An n-interesting polygon is obtained by taking the n - 1-interesting polygon
     * and appending 1-interesting polygons to its rim, side by side.
     * You can see the 1-, 2-, 3- and 4-interesting polygons in the picture below.
     */

    int shapeArea(int n) {
        int mSum = 0;
        for (int i = 1; i < n; i++) {
            mSum += 2 * (i + (i - 1));
        }
        mSum += n + (n - 1);
        return mSum;
    }


    /**
     * 6 **
     * Ratiorg got statues of different sizes as a present from CodeMaster for his birthday,
     * each statue having an non-negative integer size. Since he likes to make things perfect,
     * he wants to arrange them from smallest to largest so that each
     * statue will be bigger than the previous one exactly by 1.He may need some additional
     * statues to be able to accomplish that. Help him figure out the minimum number of additional statues needed.
     */

    int makeArrayConsecutive2(int[] statues) {
        int mMaxNumber = statues[0];
        int mMinNumber = statues[0];
        for (int i = 0; i < statues.length; i++) {
            if (statues[i] > mMaxNumber) {
                mMaxNumber = statues[i];
            }

            if (statues[i] < mMinNumber) {
                mMinNumber = statues[i];
            }
        }
        return (mMaxNumber - mMinNumber) - (statues.length - 1);
    }

    /**
     * 7 **
     * Given a sequence of integers as an array,
     * determine whether it is possible to obtain a strictly increasing sequence
     * by removing no more than one element from the array.
     *
     * @param sequence
     * @return
     */

    boolean almostIncreasingSequence(int[] sequence) {

        int mflat = 0;
        for (int i = 0; i < sequence.length - 1; i++) {
            if (sequence[i] >= sequence[i + 1]) {
                mflat++;
                if (i >= 1
                        && i + 2 <= sequence.length - 1
                        && sequence[i] >= sequence[i + 2]
                        && sequence[i - 1] >= sequence[i + 1]) {
                    return false;
                }
            }
        }
        return mflat <= 1;
    }


    /**
     * 8 **
     * After becoming famous, the CodeBots decided to move into a new building together.
     * Each of the rooms has a different cost, and some of them are free,
     * but there's a rumour that all the free rooms are haunted! Since the CodeBots are quite superstitious,
     * they refuse to stay in any of the free rooms, or any of the rooms below any of the free rooms.
     * Given matrix, a rectangular matrix of integers, where each value represents the cost of the room,
     * your task is to return the total sum of all rooms that are suitable
     * for the CodeBots (ie: add up all the values that don't appear below a 0).
     *
     * @param matrix
     * @return
     */

    int matrixElementsSum(int[][] matrix) {
        int mSum = 0;
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j][i] != 0) {
                    mSum += matrix[j][i];
                } else {
                    break;
                }
            }
        }
        return mSum;
    }
}
