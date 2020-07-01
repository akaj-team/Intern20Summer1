package com.asiantech.intern20summer1.intro;

public class RainsOfReason {
    /**
     * Write a function that replaces all the occurrences of elemToReplace with substitutionElem.
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
     * Write a function that checks if all digits of the given integer are even.
     */
    boolean evenDigitsOnly(int n) {
        while (n > 0) {
            int digit = n % 10;
            if (digit % 2 != 0) {
                return false;
            }
            n /= 10;
        }
        return true;
    }

    /**
     * Write a function that checks if the given string is a correct variable name.
     */
    boolean variableName(String name) {
        Character firstChar = name.charAt(0);

        if (Character.isDigit(firstChar)) {
            return false;
        }

        return name.matches("\\w*");
    }

    /**
     * Write a function that replaces each of inputString's characters by the next one in the English alphabet.
     */
    String alphabeticShift(String inputString) {
        String newString = "";
        for (int i = 0; i < inputString.length(); i++) {
            char temp = inputString.charAt(i);
            int n = temp + 1;
            if (n == 123) {
                n = 97;
            }
            temp = (char) n;
            newString += temp;
        }
        System.out.println(newString);
        return newString;
    }

    /**
     * Write a function that checks if both cells have the same color.
     */
    boolean chessBoardCellColor(String cell1, String cell2) {
        int i1Cell1 = cell1.charAt(0) - 65, i2Cell1 = cell1.charAt(1) - 49;
        int i1Cell2 = cell2.charAt(0) - 65, i2Cell2 = cell2.charAt(1) - 49;
        char check1;
        char check2;

        if ((i1Cell1 % 2 == 0 && i2Cell1 % 2 == 0) || (i1Cell1 % 2 == 1 && i2Cell1 % 2 == 1)) {
            check1 = 'X';
        } else {
            check1 = 'O';
        }
        if ((i1Cell2 % 2 == 0 && i2Cell2 % 2 == 0) || (i1Cell2 % 2 == 1 && i2Cell2 % 2 == 1)) {
            check2 = 'X';
        } else {
            check2 = 'O';
        }

        if (check1 == check2) {
            return true;
        }

        return false;
    }
}
