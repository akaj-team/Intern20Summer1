package com.asiantech.intern20summer1.intro;

import java.util.ArrayList;
import java.util.List;

public class ExploringTheWaters {
    /**
     * Write a function that returns an array of two integers, where the first element is the total weight of team 1,
     * and the second element is the total weight of team 2 after the division is complete.
     */
    int[] alternatingSums(int[] a) {
        int[] aSums = new int[2];
        for (int i = 0; i < a.length; i++) {
            if (i % 2 == 0) {
                aSums[0] += a[i];
            } else {
                aSums[1] += a[i];
            }
        }
        return aSums;
    }

    /**
     * Write a function that adds a border of asterisks(*) to the given rectangular matrix of characters.
     */
    String[] addBorder(String[] picture) {
        int borderRow = picture.length + 2;
        int borderCol = picture[0].length() + 2;
        String[] newPicture = new String[borderRow];
        String tmp = "";

        for (int i = 0; i < newPicture.length; i++) {
            if (i == 0 || i == borderRow - 1) {
                for (int j = 0; j < borderCol; j++) {
                    tmp += "*";
                }
                newPicture[i] = tmp;
                tmp = "";
            } else {
                tmp = "*";
                tmp += picture[i - 1];
                tmp += "*";
                newPicture[i] = tmp;
                tmp = "";
            }
        }

        return newPicture;
    }

    /**
     * Write a class Position
     */
    class Position {
        int position;
        int aElement;
        int bElement;

        Position(int pos, int a, int b) {
            position = pos;
            aElement = a;
            bElement = b;
        }
    }

    /**
     * Write a function that checks if two given integer array are similar. With at most one pair have wrong place.
     */
    boolean areSimilar(int[] A, int[] B) {
        ArrayList<Position> mismatchedPosition = new ArrayList<>();

        for (int i = 0; i < A.length; i++) {

            int a = A[i];
            int b = B[i];

            if (a != b) {
                Position p = new Position(i, a, b);
                mismatchedPosition.add(p);
            }
        }

        if (mismatchedPosition.size() == 0) {
            return true;
        } else if (mismatchedPosition.size() == 2) {
            Position pos1 = mismatchedPosition.get(0);
            Position pos2 = mismatchedPosition.get(1);

            int aPos1 = pos1.aElement;
            int bPos1 = pos1.bElement;

            int aPos2 = pos2.aElement;
            int bPos2 = pos2.bElement;

            if (aPos1 == bPos2 && bPos1 == aPos2)
                return true;
            else
                return false;
        } else
            return false;
    }

    /**
     * Write a function that returns the minimal number of moves
     * required to obtain a strictly increasing sequence from the input.
     */
    int arrayChange(int[] inputArray) {
        int result = 0;

        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i - 1] >= inputArray[i]) {
                int difference = Math.abs(inputArray[i - 1] - inputArray[i]) + 1;
                inputArray[i] += difference;
                result += difference;
            }
        }

        return result;
    }

    /**
     * Write a function that checks the given string can be rearranged to form a palindrome.
     */
    boolean palindromeRearranging(String inputString) {
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < inputString.length(); i++) {
            if (list.contains(inputString.charAt(i))) {
                list.remove((Character) inputString.charAt(i));
            } else {
                list.add(inputString.charAt(i));
            }
        }

        if (list.size() == 0 || list.size() == 1) {
            return true;
        } else {
            return false;
        }
    }
}
