package intro;

public class EdgeOfTheOcean {
    /**
     * Given an array of integers, find the pair of adjacent elements that has the largest product
     * and return that product.
     */
    int adjacentElementsProduct(int[] inputArray) {
        int max = inputArray[0] * inputArray[1];
        for (int i = 1; i < inputArray.length - 1; i++) {
            if (max < inputArray[i] * inputArray[i + 1]) {
                max = inputArray[i] * inputArray[i + 1];
            }
        }
        return max;
    }

    /**
     * Below we will define an n-interesting polygon. Your task is to find the area of a polygon
     * for a given n.
     * A 1-interesting polygon is just a square with a side of length 1. An n-interesting polygon
     * is obtained by taking the n - 1-interesting polygon and appending 1-interesting polygons to
     * its rim, side by side.
     */
    int shapeArea(int n) {
        if (n == 1) {
            return 1;
        }
        return shapeArea(n - 1) + (4 * (n - 1));
    }

    /**
     * Ratiorg got statues of different sizes as a present from CodeMaster for his birthday,
     * each statue having an non-negative integer size. Since he likes to make things perfect,
     * he wants to arrange them from smallest to largest so that each statue will be bigger than
     * the previous one exactly by 1. He may need some additional statues to be able to
     * accomplish that. Help him figure out the minimum number of additional statues needed.
     */
    int makeArrayConsecutive2(int[] statues) {
        int min = statues[0];
        int max = statues[0];
        for (int i = 1; i < statues.length; i++) {
            if (min > statues[i]) {
                min = statues[i];
            } else if (max < statues[i]) {
                max = statues[i];
            }
        }
        if (min == 0)
            return Math.abs((max - min) - statues.length + 1);
        else return Math.abs((max - min + 1) - statues.length);
    }

    /**
     * Given a sequence of integers as an array, determine whether it is possible to obtain a strictly
     * increasing sequence by removing no more than one element from the array.
     */
    boolean almostIncreasingSequence(int[] sequence) {
        int count = 0;
        int i = 0;
        while (i < sequence.length - 1) {
            if (sequence[i] < sequence[i + 1]) {
                i = i + 1;
                continue;
            } else {
                count++;
                if (i > 0 && i + 2 < sequence.length
                        && sequence[i + 1] <= sequence[i - 1]
                        && sequence[i + 2] <= sequence[i]) {
                    count++;
                } else {
                    i = i + 1;
                }
                if (count > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Given matrix, a rectangular matrix of integers, where each value represents the cost of the room,
     * your task is to return the total sum of all rooms that are suitable for the CodeBots (ie: add up
     * all the values that don't appear below a 0).
     */
    int matrixElementsSum(int[][] matrix) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    if (i < matrix.length - 1) {
                        matrix[i + 1][j] = 0;
                    } else {
                        continue;
                    }
                } else {
                    sum = sum + matrix[i][j];
                }
            }
        }
        return sum;
    }
}
