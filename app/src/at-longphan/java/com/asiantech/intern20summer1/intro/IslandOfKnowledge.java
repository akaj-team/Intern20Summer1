package com.asiantech.intern20summer1.intro;

import java.util.Arrays;

public class IslandOfKnowledge {
    public static void main(String[] args) {
        //RUN main() with Coverage
        System.out.println("Ex 19: " + areEquallyStrong(10, 20, 20, 10));

        System.out.println("Ex 20: " + arrayMaximalAdjacentDifference(new int[]{-1, 4, 10, 3, -2}));

        System.out.println("Ex 21: " + isIPv4Address("64.233.161.00"));

        System.out.println("Ex 22: " + avoidObstacles(new int[]{5, 3, 6, 7, 9}));

        System.out.println("Ex 23: [");
        int[][] box = boxBlur(new int[][]{{7, 4, 0, 1}, {5, 6, 2, 2}, {6, 10, 7, 8}, {1, 4, 2, 0}});
        for (int i = 0; i < box.length; i++) {
            for (int j = 0; j < box[0].length; j++) {
                System.out.print(box[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("]");

        System.out.println("Ex 24: [");
        int[][] minesweeper = minesweeper(new boolean[][]{{false, true, true, false}, {true, true, false, true}, {false, false, true, false}});
        for (int i = 0; i < minesweeper.length; i++) {
            for (int j = 0; j < minesweeper[0].length; j++) {
                System.out.print(minesweeper[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("]");
    }

    static boolean areEquallyStrong(int yourLeft, int yourRight, int friendsLeft, int friendsRight) {
        /**
         * Call two arms equally strong if the heaviest weights they each are able to lift are equal.
         *
         * Call two people equally strong if their strongest arms are equally strong
         * (the strongest arm can be both the right and the left), and so are their weakest arms.
         *
         * Given your and your friend's arms' lifting capabilities find out if you two are equally strong.
         */
        if ((yourLeft == friendsLeft && yourRight == friendsRight)
                || yourLeft == friendsRight && yourRight == friendsLeft) {
            return true;
        } else {
            return false;
        }
    }

    static int arrayMaximalAdjacentDifference(int[] a) {
        /**
         * Given an array of integers,
         * find the maximal absolute difference between any two of its adjacent elements.
         */
        int max = 0;
        for (int i = 0; i < a.length - 1; i++) {
            max = (Math.abs(a[i] - a[i + 1]) > max) ? Math.abs(a[i] - a[i + 1]) : max;
        }
        return max;
    }

    static boolean isIPv4Address(String a) {
        /**
         * An IP address is a numerical label assigned to each device
         * (e.g., computer, printer) participating in a computer network
         * that uses the Internet Protocol for communication.
         * There are two versions of the Internet protocol,
         * and thus two versions of addresses. One of them is the IPv4 address.
         *
         * Given a string, find out if it satisfies the IPv4 address naming rules.
         */
        String[] b = a.split("[.]");
        if (b.length != 4) return false;
        try {
            for (String item : b) {
                if (item.matches("[0][1-9]") || item.matches("[0][0]")) {
                    return false;
                }
                if ((Integer.parseInt(item) < 0) || (Integer.parseInt(item) > 255)) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    static int avoidObstacles(int[] a) {
        /**
         * You are given an array of integers representing coordinates of
         * obstacles situated on a straight line.
         *
         * Assume that you are jumping from the point with coordinate 0 to the right.
         * You are allowed only to make jumps of the same length represented by some integer.
         *
         * Find the minimal length of the jump enough to avoid all the obstacles.
         */
        Arrays.sort(a);
        int max = a[a.length - 1];
        int x = 2;
        boolean isExistInArray = false;
        for (; ; x++) {
            isExistInArray = false;
            for (int item : a) {
                if (item == x) {
                    isExistInArray = true;
                    break;
                }
            }
            if (isExistInArray) {
                continue;
            }
            for (int jump = 1; x * jump <= max; jump++) {
                for (int item : a) {
                    if (item == x * jump) {
                        isExistInArray = true;
                        break;
                    }
                }
                if (isExistInArray) {
                    break;
                }
            }
            if (isExistInArray) {
                continue;
            } else {
                return x;
            }
        }
    }

    static int[][] boxBlur(int[][] image) {
        /**
         * Last night you partied a little too hard.
         * Now there's a black and white photo of you that's about to go viral!
         * You can't let this ruin your reputation, so you want to apply the box blur algorithm
         * to the photo to hide its content.
         *
         * The pixels in the input image are represented as integers.
         * The algorithm distorts the input image in the following way:
         * Every pixel x in the output image has a value equal to
         * the average value of the pixel values from the 3 × 3 square that has its center at x,
         * including x itself. All the pixels on the border of x are then removed.
         *
         * Return the blurred image as an integer, with the fractions rounded down.
         */
        int row = image.length;
        int col = image[0].length;
        int rowX = row - 2;
        int colX = col - 2;
        int[][] x = new int[rowX][colX];

        for (int i = 0; i < rowX; i++) {
            for (int j = 0; j < colX; j++) {
                for (int i2 = i; i2 <= i + 2; i2++) {
                    for (int j2 = j; j2 <= j + 2; j2++) {
                        x[i][j] += image[i2][j2];
                    }
                }
                x[i][j] = (int) (Math.floor((x[i][j]) / 9));
            }
        }
        return x;
    }

    static int[][] minesweeper(boolean[][] matrix) {
        /**
         * In the popular Minesweeper game you have a board with some mines
         * and those cells that don't contain a mine have a number in it
         * that indicates the total number of mines in the neighboring cells.
         * Starting off with some arrangement of mines we want to create a Minesweeper game setup.
         */
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] x = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                for (int i2 = i - 1; i2 <= i + 1; i2++) {
                    for (int j2 = j - 1; j2 <= j + 1; j2++) {
                        if (i2 < 0 || j2 < 0 || (i2 == i && j2 == j) || i2 >= row || j2 >= col) {
                            continue;
                        } else {
                            x[i][j] = (matrix[i2][j2] == true) ? x[i][j] + 1 : x[i][j];
                        }
                    }
                }
            }
        }
        return x;
    }


}
