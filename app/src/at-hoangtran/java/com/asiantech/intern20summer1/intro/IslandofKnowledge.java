package com.asiantech.intern20summer1.intro;

public class IslandofKnowledge {
    public static void main(String[] args) {
        String ip = "1.562.32.54";
        int yl = 1;
        int yr = 2;
        int fl = 2;
        int fr = 1;
        int[] intArray = new int[]{1, 5, 4, 6, 2, 4, 6, 8};
        int[][] intMatrix = new int[][]{{1, 1, 1}, {1, 7, 1}, {1, 1, 1}};
        boolean[][] mines = new boolean[][]{{true, false, false}, {false, true, false}, {false, false, false}};
        System.out.println(areEquallyStrong(yl,yr,fl,fr));
        System.out.println(arrayMaximalAdjacentDifference(intArray));
        System.out.println(isIPv4Address(ip));
        System.out.println(avoidObstacles(intArray));
        System.out.println(boxBlur(intMatrix));
        System.out.println(minesweeper(mines));
    }

    static boolean areEquallyStrong(int yourLeft, int yourRight, int friendsLeft, int friendsRight) {
//        Call two arms equally strong if the heaviest weights they each are able to lift are equal.
//
//                Call two people equally strong if their strongest arms are equally strong (the
//        strongest arm can be both the right and the left),and so are their weakest arms.
//
//        Given your and your friend 's arms' lifting capabilities find out if you two are equally
//        strong.
        int count = 0;
        if (yourLeft == friendsLeft || yourLeft == friendsRight) {
            count++;
        }
        if (yourRight == friendsLeft || yourRight == friendsRight) {
            count++;
        }
        return count == 2;
    }
    static int arrayMaximalAdjacentDifference(int[] inputArray) {
//        Given an array of integers, find the maximal absolute difference between any two of its
//        adjacent elements.
        int l = inputArray.length;
        int max = 0;
        for(int i  = 0;i<l-1;i++){
            int tmp = Math.abs(inputArray[i]-inputArray[i+1]);
            if(tmp>max){
                max = tmp;
            }
        }
        return max;
    }

    static boolean isIPv4Address(String inputString) {
//        An IP address is a numerical label assigned to each device(e.g., computer, printer)
//        participating in a computer network that uses the Internet Protocol for
//        communication.There are two versions of the Internet protocol, and thus two versions of
//        addresses.One of them is the IPv4 address.
//
//                Given a string, find out if it satisfies the IPv4 address naming rules.
        String[] str = inputString.split("\\.");
        if (str.length != 4) {
            return false;
        }
        try {
            for (int i = 0; i < str.length; i++) {
                if (str[i].charAt(0) == '0' && str[i].length() > 1) {
                    return false;
                }
                for (int j = 0; j < str[i].length(); j++) {
                    if (str[i].charAt(j) >= 65) {
                        return false;
                    }
                }
                int sub = Integer.parseInt(str[i]);
                if (sub < 0 || sub > 255) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    static int avoidObstacles(int[] inputArray) {
//        You are given an array of integers representing coordinates of obstacles situated on a
//        straight line.
//
//        Assume that you are jumping from the point with coordinate 0 to the right.You are
//        allowed only to make jumps of the same length represented by some integer.
//
//                Find the minimal length of the jump enough to avoid all the obstacles.
        int l = inputArray.length;
        int jump = 2;
        for (int i = 0; i < l; i++) {
            if (inputArray[i] % jump == 0) {
                jump++;
                i = -1;
            }
        }
        return jump;
    }

    static int[][] boxBlur(int[][] image) {
//        Last night you partied a little too hard.Now there 's a black and white photo of you that'
//        s about to go viral !You can
//        't let this ruin your reputation, so you want to apply the box blur algorithm to the photo to hide its content.
//
//        The pixels in the input image are represented as integers.The algorithm distorts the
//        input image in the following way:Every pixel x in the output image has a value equal to
//        the average value of the pixel values from the 3 ×3 square that has its center at
//        x, including x itself.All the pixels on the border of x are then removed.
//
//                Return the blurred image as an integer, with the fractions rounded down.
        int col = image.length;
        int row = image[0].length;
        int[][] blur = new int[col - 2][row - 2];
        for (int i = 0; i < col - 2; i++) {
            for (int j = 0; j < row - 2; j++) {
                blur[i][j] = (image[i][j] + image[i][j + 1] + image[i][j + 2]
                        + image[i + 1][j] + image[i + 1][j + 1] + image[i + 1][j + 2]
                        + image[i + 2][j] + image[i + 2][j + 1] + image[i + 2][j + 2]) / 9;
            }
        }
        return blur;
    }

    static int[][] minesweeper(boolean[][] matrix) {
//        In the popular Minesweeper game you have a board with some mines and those cells that don
//        't contain a mine have a number in it that indicates the total number of mines in the neighboring
//        cells. Starting off with some arrangement of mines we want to create a Minesweeper game setup.
        int col = matrix.length;
        int row = matrix[0].length;
        int[][] mine = new int[col][row];
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                int count = 0;
                if (i > 0 && j > 0 && matrix[i - 1][j - 1]) {
                    count++;
                }
                if (i > 0 && matrix[i - 1][j]) {
                    count++;
                }
                if (i > 0 && j + 1 < row && matrix[i - 1][j + 1]) {
                    count++;
                }
                if (j > 0 && matrix[i][j - 1]) {
                    count++;
                }
                if (j + 1 < row && matrix[i][j + 1]) {
                    count++;
                }
                if (i + 1 < col && j > 0 && matrix[i + 1][j - 1]) {
                    count++;
                }
                if (i + 1 < col && matrix[i + 1][j]) {
                    count++;
                }
                if (i + 1 < col && j + 1 < row && matrix[i + 1][j + 1]) {
                    count++;
                }
                mine[i][j] = count;
            }
        }
        return mine;
    }

}
