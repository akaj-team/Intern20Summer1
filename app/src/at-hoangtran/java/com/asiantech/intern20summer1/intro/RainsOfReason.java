package com.asiantech.intern20summer1.intro;

public class RainsOfReason {
    public static void main(String[] args) {
        int replace = 4;
        int substiution = 5;
        String name = "abcxzsd/";
        String cell1 = "A5";
        String cell2 = "B4";
        int[] intArray = new int[]{1, 4, 2, 3, 4, 8};
        System.out.println(arrayReplace(intArray, replace, substiution));
        System.out.println(evenDigitsOnly(replace));
        System.out.println(variableName(name));
        System.out.println(alphabeticShift(name));
        System.out.println(chessBoardCellColor(cell1, cell2));
    }

    static int[] arrayReplace(int[] inputArray, int elemToReplace, int substitutionElem) {
//        Given an array of integers, replace all the occurrences of elemToReplace with substitutionElem.
        for (int i = 0; i < inputArray.length; i++) {
            if (inputArray[i] == elemToReplace) {
                inputArray[i] = substitutionElem;
            }
        }
        return inputArray;
    }

    static boolean evenDigitsOnly(int n) {
//        Check if all digits of the given integer are even.
        String s = Integer.toString(n);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) % 2 != 0) {
                return false;
            }
        }
        return true;
    }

    static boolean variableName(String name) {
//        Correct variable names consist only of English letters, digits and underscores and they can
//        't start with a digit.
//
//        Check if the given string is a correct variable name.
        return (name.matches("[a-zA-Z_]+[\\w_]*"));
    }

    static String alphabeticShift(String inputString) {
//        Given a string, your task is to replace each of its characters by the next one in the
//        English alphabet;
//        i.e.replace a with b, replace b with c, etc(z would be replaced by a).
        char[] c = new char[inputString.length()];
        for (int i = 0; i < inputString.length(); i++) {
            c[i] = inputString.charAt(i);
            if (c[i] == 'z') {
                c[i] = 'a';
            } else {
                c[i]++;
            }
        }
        return String.copyValueOf(c);
    }

    static boolean chessBoardCellColor(String cell1, String cell2) {
//        Given two cells on the standard chess board, determine whether they have the same color or not.
        return (Math.abs((cell1.charAt(0) - cell2.charAt(0)) % 2)
                == Math.abs((cell1.charAt(1) - cell2.charAt(1)) % 2));
    }
}
