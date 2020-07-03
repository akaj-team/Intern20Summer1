package com.asiantech.intern20summer1.intro;

import java.util.ArrayList;
import java.util.HashSet;

public class LandOfLogic {
    public static void main(String[] args) {
        String s = "Ready, 2steady, 5go!";
        String time = "24:21";
        int i = 345;
        int n = 5;
        String code = "010010000110010101101100011011000110111100100001";
        String[] sa = new String[]{"doc", "doc", "image", "doc(1)", "doc"};
        int[][] matrix = new int[][]{{1, 2, 1}, {2, 2, 2}, {2, 2, 2}, {1, 2, 3}, {2, 2, 1}};
        int[][] grid = new int[][]
                {{1, 3, 2, 5, 4, 6, 9, 8, 7},
                        {4, 6, 5, 8, 7, 9, 3, 2, 1},
                        {7, 9, 8, 2, 1, 3, 6, 5, 4},
                        {9, 2, 1, 4, 3, 5, 8, 7, 6},
                        {3, 5, 4, 7, 6, 8, 2, 1, 9},
                        {6, 8, 7, 1, 9, 2, 5, 4, 3},
                        {5, 7, 6, 9, 8, 1, 4, 3, 2},
                        {2, 4, 3, 6, 5, 7, 1, 9, 8},
                        {8, 1, 9, 3, 2, 4, 7, 6, 5}};
        System.out.println(longestWord(s));
        System.out.println(validTime(time));
        System.out.println(sumUpNumbers(s));
        System.out.println(differentSquares(matrix));
        System.out.println(digitsProduct(i));
        System.out.println(fileNaming(sa));
        System.out.println(messageFromBinaryCode(code));
        System.out.println(spiralNumbers(n));
        System.out.println(sudoku(grid));
    }
        static String longestWord(String text) {
//        Define a word as a sequence of consecutive English letters. Find the longest word
//        from the given string.
        String newtext = "";
        for (int i = 0; i < text.length(); i++) {
            if (Character.isLetter(text.charAt(i)) || text.charAt(i) == ' ') {
                newtext += text.charAt(i);
            } else {
                newtext += ' ';
            }
        }
        String[] s = newtext.split(" ");
        if (s.length == 1) {
            return s[0];
        }
        String tmp = "";
        int longest = s[0].length();
        for (int i = 1; i < s.length; i++) {
            if (longest < s[i].length()) {
                longest = s[i].length();
                tmp = s[i];
            }
        }
        return tmp;
    }

    static boolean validTime(String time) {
//        Check if the given string is a correct time representation of the 24-hour clock.
        String[] s = time.split(":");
        return (Integer.parseInt(s[0]) < 24 && Integer.parseInt(s[1]) < 60);
    }

    static int sumUpNumbers(String inputString) {
//        CodeMaster has just returned from shopping.He scanned the check of the items he bought and
//        gave the resulting string to Ratiorg to figure out the total number of purchased
//        items.Since Ratiorg is a bot he is definitely going to automate it, so he needs a program
//        that sums up all the numbers which appear in the given input.
//
//        Help Ratiorg by writing a function that returns the sum of numbers that appear in the
//        given inputString.
        String[] s = inputString.split("[^0-9]");
        int sum = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i].length() != 0) {
                sum += Integer.parseInt(s[i]);
            }
        }
        return sum;
    }

    static int differentSquares(int[][] matrix) {
//        Given a rectangular matrix containing only digits, calculate the number of different 2 ×2
//        squares in it.
        ArrayList<String> strList = new ArrayList<>();
        int row = matrix.length;
        int col = matrix[0].length;
        for (int i = 0; i < row - 1; i++) {
            for (int j = 0; j < col - 1; j++) {
                String s = String.valueOf(matrix[i][j]);
                s += "" + String.valueOf(matrix[i][j + 1]);
                s += "" + String.valueOf(matrix[i + 1][j]);
                s += "" + String.valueOf(matrix[i + 1][j + 1]);
                if (!strList.contains(s)) {
                    strList.add(s);
                }
            }
        }
        return strList.size();
    }

    static int digitsProduct(int product) {
//        Given an integer product, find the smallest positive(i.e.greater than 0) integer the
//        product of whose digits is equal to product.If there is no such integer, return -1 instead.
        if (product == 0) {
            return 10;
        } else if (product < 10) {
            return product;
        }
        String s = "";
        for (int i = 9; i > 1; i--) {
            for (; product % i == 0; product /= i) {
                s = i + s;
            }
        }
        return (product > 1) ? -1 : Integer.valueOf(s);
    }

    static String[] fileNaming(String[] names) {
//        You are given an array of desired filenames in the order of their creation.Since two
//        files cannot have equal names, the one which comes later will have an addition to its
//        name in a form of(k), where k is the smallest positive integer such that the obtained
//        name is not used yet.
//
//                Return an array of names that will be given to the files.


        // String[] file = new String[names.length];
        // int k = 0;
        // for(int i = 0;i<names.length;i++){
        //     int count = 0;
        //     for(int j = 0;j<file.length;j++){
        //         if(names[i].equals(file[j])){
        //             count++;
        //         }
        //     }
        //     if(count == 0){
        //         file[i] = names[i];
        //     }
        //     else {
        //         file[i] = names[i]+ "("+count+")";
        //         for(int j = 0;j<i;j++){
        //             if(file[i].equals(file[j])){
        //                 count++;
        //                 file[i] = names[i]+ "("+count+")";
        //             }
        //         }
        //         for(int j = 0;j<i;j++){
        //             if(file[i].equals(file[j])){
        //                 count++;
        //                 file[i] = names[i]+ "("+count+")";
        //             }
        //         }
        //     }
        // }
        // return file;

        ArrayList<String> fileL = new ArrayList<>();
        ArrayList<String> tmpL = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            int count = 1;
            String str = names[i];
            if (fileL.contains(names[i])) {
                str = names[i] + "(" + count + ")";
                while (tmpL.contains(str)) {
                    count++;
                    str = names[i] + "(" + count + ")";
                }
            }
            fileL.add(str);
            tmpL.add(str);
        }
        String[] s = new String[fileL.size()];
        for (int i = 0; i < fileL.size(); i++) {
            s[i] = fileL.get(i);
        }
        return s;
    }

    static String messageFromBinaryCode(String code) {
//        You are taking part in an Escape Room challenge designed specifically for
//        programmers.In your efforts to find a clue, you
//        've found a binary code written on the wall behind a vase, and realized that it must be an encrypted message. After some thought, your first guess is that each consecutive 8 bits of the code stand for the character with the corresponding extended ASCII code.
//
//        Assuming that your hunch is correct, decode the message.
        String tmp = "";
        System.out.println(code.substring(0, 8));
        for (int i = 0; i < code.length(); i += 8) {
            String s = "";
            s = code.substring(i, i + 8);
            System.out.println(s);
            tmp += (char) Integer.parseInt(s, 2);
        }
        return tmp;
    }

    static int[][] spiralNumbers(int n) {
//        Construct a square matrix with a size N ×N containing integers from 1 to N *N in a spiral
//        order, starting from top -left and in clockwise direction.
        int[][] m = new int[n][n];
        int left = 0;
        int right = n - 1;
        int top = 0;
        int bot = n - 1;
        for (int k = 1; k <= n * n; ) {
            for (int i = left; i <= right; i++) {
                m[top][i] = k;
                k++;
            }
            top++;
            for (int i = top; i <= bot; i++) {
                m[i][right] = k;
                k++;
            }
            right--;
            for (int i = right; i >= left; i--) {
                m[bot][i] = k;
                k++;
            }
            bot--;
            for (int i = bot; i >= top; i--) {
                m[i][left] = k;
                k++;
            }
            left++;
        }
        return m;
    }

    static boolean sudoku(int[][] grid) {
//        Sudoku is a number -placement puzzle.The objective is to fill a 9 ×9 grid with digits so
//        that each column, each row, and each of the nine 3 ×3 sub - grids that compose the grid
//        contains all of the digits from 1 to 9.
//
//        This algorithm should check if the given grid of numbers represents a correct solution to
//        Sudoku.
        for (int i = 0; i < 9; i++) {
            HashSet<Integer> a = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                if (!a.add(grid[i][j]))
                    return false;
            }
        }
        for (int i = 0; i < 9; i++) {
            HashSet<Integer> a = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                if (!a.add(grid[j][i]))
                    return false;
            }
        }
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                HashSet<Integer> a = new HashSet<>();
                for (int l = i; l < i + 3; l++) {
                    for (int k = j; k < +3; k++) {
                        if (!a.add(grid[l][k])) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
