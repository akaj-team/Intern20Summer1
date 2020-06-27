package com.asiantech.intern20summer1.intro;

public class EdgeOfTheOcean {
    /**
     * Write a function that sort array from min to max
     */
    void sortASC(int[] arr) {
        int temp = arr[0];
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }
    }

    /**
     * Write a function that check the palindrome string
     */
    int adjacentElementsProduct(int[] inputArray) {
        int size = inputArray.length;
        int n = inputArray[0] * inputArray[1];
        for (int i = 1; i < size - 1; i++) {
            int j = i + 1;
            if ((inputArray[i] * inputArray[j]) > n) {
                n = inputArray[i] * inputArray[j];
            }
        }
        return n;
    }

    /**
     * Write a function that returns the area of the polygons have edges n
     */
    int shapeArea(int n) {
        return n * n + (n - 1) * (n - 1);
    }

    /**
     * Write a function that returns number of elements to become a continuous array
     */
    int makeArrayConsecutive2(int[] statues) {
        int n = 0;
        sortASC(statues);
        for (int i = 0; i < statues.length - 1; i++) {
            n += (statues[i + 1] - statues[i] - 1);
        }
        return n;
    }

    /**
     * Write a function that checks the given sequence is an increasing sequence when remove 1 element
     */
    boolean almostIncreasingSequence(int[] sequence) {
        int n = 0;

        for (int i = 0; i < sequence.length - 1; i++) {
            if (sequence[i] - sequence[i + 1] >= 0) {
                n++;
                if (i - 1 >= 0 && i + 2 <= sequence.length - 1
                        && sequence[i] - sequence[i + 2] >= 0
                        && sequence[i - 1] - sequence[i + 1] >= 0) {
                    return false;
                }
            }
        }
        return n <= 1;
    }

    /**
     * Write a function that returns the total sum of all rooms that having the values don't appear below 0
     */
    int matrixElementsSum(int[][] matrix) {
        int sum = 0;
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j][i] > 0)
                    sum += matrix[j][i];
                else
                    break;
            }
        }
        return sum;
    }
}
