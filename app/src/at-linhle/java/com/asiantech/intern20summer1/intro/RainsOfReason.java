package com.asiantech.intern20summer1.intro;

public class RainsOfReason {
    /**
     * Given an array of integers, replace all the occurrences of elemToReplace with
     * substitutionElem.
     */
    int[] arrayReplace(int[] inputArray, int elemToReplace, int substitutionElem) {
        for (int i = 0; i < inputArray.length; i++) {
            if (inputArray[i] == elemToReplace) {
                inputArray[i] = substitutionElem;
            }
        }
        return inputArray;
    }

    /**
     * Check if all digits of the given integer are even.
     */
    boolean evenDigitsOnly(int n) {
        while (n != 0) {
            if (n % 10 % 2 != 0) {
                return false;
            }
            n = n / 10;
        }
        return true;
    }

    /**
     * Correct variable names consist only of English letters, digits and underscores and they
     * can't start with a digit.
     * Check if the given string is a correct variable name.
     */
    boolean variableName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (!('a' <= name.charAt(i) && name.charAt(i) <= 'z' ||
                    'A' <= name.charAt(i) && name.charAt(i) <= 'Z' ||
                    '0' <= name.charAt(i) && name.charAt(i) <= '9' ||
                    name.charAt(i) == '_')) {
                return false;
            }
        }
        return '0' > name.charAt(0) || name.charAt(0) > '9';
    }

    /**
     * Given a string, your task is to replace each of its characters by the next one in the
     * English alphabet; i.e. replace a with b, replace b with c, etc (z would be replaced by a).
     */
    String alphabeticShift(String inputString) {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < inputString.length(); i++) {
            if (inputString.charAt(i) == 'z') {
                temp.append('a');
            } else {
                temp.append((char) ((int) inputString.charAt(i) + 1));
            }
        }
        return temp.toString();
    }

    /**
     * Given two cells on the standard chess board, determine whether they have the same color
     * or not.
     */
    boolean chessBoardCellColor(String cell1, String cell2) {
        char column1 = cell1.charAt(0), column2 = cell2.charAt(0);
        char row1 = cell1.charAt(1), row2 = cell2.charAt(1);
        return (column1 + row1) % 2 == (column2 + row2) % 2;
    }
}
