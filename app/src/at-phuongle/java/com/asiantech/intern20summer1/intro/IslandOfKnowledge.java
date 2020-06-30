package com.asiantech.intern20summer1.intro;

import java.util.ArrayList;
import java.util.List;

public class IslandOfKnowledge {
    /**
     * Write a function that checks if you and your friend are equally strong.
     */
    boolean areEquallyStrong(int yourLeft, int yourRight, int friendsLeft, int friendsRight) {
        int myHeaviestWeights = (yourLeft > yourRight) ? yourLeft : yourRight;
        int friendsHeaviestWeights = (friendsLeft > friendsRight) ? friendsLeft : friendsRight;
        int mySum = yourLeft + yourRight;
        int friendsSum = friendsLeft + friendsRight;
        if (mySum == friendsSum) {
            if (myHeaviestWeights == friendsHeaviestWeights) {
                return true;
            }
        }
        return false;
    }

    /**
     * Write a function that returns the maximal absolute difference between any two of its adjacent elements.
     */
    int arrayMaximalAdjacentDifference(int[] inputArray) {
        int maxDiff = 0;
        for (int i = 0; i < inputArray.length - 1; i++) {
            int diff = Math.abs(inputArray[i] - inputArray[i + 1]);
            if (maxDiff < diff) {
                maxDiff = diff;
            }
        }
        return maxDiff;
    }

    /**
     * Write a function that validates IPv4 string
     */
    boolean isIPv4Address(String inputString) {
        String[] numbers = inputString.split("[.]");
        if (numbers.length != 4) {
            return false;
        } else {
            for (int i = 0; i < numbers.length; i++) {
                try {
                    if ((numbers[i].length() == 2) && Integer.parseInt(numbers[i]) < 10) {
                        return false;
                    }
                    if (numbers[i].isEmpty()) {
                        return false;
                    }
                    if (Integer.parseInt(numbers[i]) < 0 || Integer.parseInt(numbers[i]) > 255) {
                        return false;
                    }
                } catch (Exception ex1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Write a function that returns the minimal length of the jump enough to avoid all the obstacles.
     */
    int avoidObstacles(int[] inputArray) {
        boolean check = false;
        int count = 1;
        while (true) {
            for (int i = 0; i < inputArray.length; i++) {
                if (inputArray[i] % count != 0) {
                    check = true;
                } else {
                    check = false;
                    break;
                }
            }
            if (check == true) {
                return count;
            }
            count++;
        }
    }

    /**
     * Write a function that blurs images by Box Blur algorithm.
     */
    int[][] boxBlur(int[][] image) {
        int sum = 0;
        int count = 0;
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                count += 4;
                if (i < (image.length - 1)) {
                    count += 2;
                }
                if (j < (image[0].length - 1)) {

                    count += 2;
                }
                if (i < (image.length - 1) && j < (image[0].length - 1)) {

                    count += 1;
                }
                try {
                    if (count == 9) {
                        sum += image[i][j];
                        sum += image[i][j - 1];
                        sum += image[i - 1][j];
                        sum += image[i - 1][j - 1];
                        sum += image[i + 1][j];
                        sum += image[i + 1][j - 1];
                        sum += image[i][j + 1];
                        sum += image[i - 1][j + 1];
                        sum += image[i + 1][j + 1];

                        list.add(sum / count);
                    }
                } catch (Exception ex2) {
                    //Do something
                }
                sum = 0;
                count = 0;
            }
        }

        int[][] blurImage = new int[image.length - 2][image[0].length - 2];
        for (int i = 0; i < blurImage.length; i++) {
            for (int j = 0; j < blurImage[0].length; j++) {
                blurImage[i][j] = list.get(0);
                list.remove(0);
            }
        }

        return blurImage;
    }

    /**
     * Write a function that returns rectangular matrix of the same size as matrix each cell of which
     * contains an integer equal to the number of mines in the neighboring cells.
     */
    int[][] minesweeper(boolean[][] matrix) {
        int[][] result = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = 0;
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j]) {
                    try {
                        if (j > 0) result[i][j - 1]++;
                        if (j < matrix[0].length - 1) result[i][j + 1]++;
                        if (i > 0) result[i - 1][j]++;
                        if (i < matrix.length - 1) result[i + 1][j]++;
                        if (i > 0 && j > 0) result[i - 1][j - 1]++;
                        if (i < matrix.length - 1 && j > 0) result[i + 1][j - 1]++;
                        if (i > 0 && j < matrix[0].length - 1) result[i - 1][j + 1]++;
                        if (i < matrix.length - 1 && j < matrix[0].length - 1)
                            result[i + 1][j + 1]++;
                    } catch (Exception ex3) {
                        //Do something
                    }
                }
            }
        }

        return result;
    }
}
