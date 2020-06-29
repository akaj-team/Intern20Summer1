package com.asiantech.intern20summer1.intro;

public class RainsOfReason {
    public static void main(String[] args) {
        //RUN main() with Coverage
        int[] ex25 = arrayReplace(new int[]{1, 2, 3, 4, 5}, 3, 0);
        System.out.print("Ex 25: [\t");
        for (Integer item : ex25) {
            System.out.print(+item + "\t");
        }
        System.out.println("]");

        System.out.println("Ex 26: " + evenDigitsOnly(248622));

        System.out.println("Ex 27: " + variableName("var_1__Int"));

        System.out.println("Ex 28: " + alphabeticShift("crazy"));

        System.out.println("Ex 29: " + chessBoardCellColor("A1", "C3"));
    }

    static int[] arrayReplace(int[] a, int elemToReplace, int substitutionElem) {
        /**
         * Given an array of integers, replace all the occurrences of elemToReplace
         * with substitutionElem.
         */
        for (int i = 0; i < a.length; i++) {
            if (a[i] == elemToReplace) {
                a[i] = substitutionElem;
            }
        }
        return a;
    }

    static boolean evenDigitsOnly(int n) {
        /**
         * Check if all digits of the given integer are even.
         */
        String n2 = String.valueOf(n);
        for (int i = 0; i < n2.length(); i++) {
            if (Integer.valueOf(n2.charAt(i)) % 2 != 0) {
                return false;
            }
        }
        return true;
    }

    static boolean variableName(String name) {
        /**
         * Correct variable names consist only of English letters,
         * digits and underscores and they can't start with a digit.
         *
         * Check if the given string is a correct variable name.
         */
        return name.matches("^[a-zA-Z_]{1}[a-zA-Z0-9_]{0,9}$");
    }

    static String alphabeticShift(String a) {
        /**
         * Given a string, your task is to replace each of its characters
         * by the next one in the English alphabet;
         * i.e. replace a with b, replace b with c,
         * etc (z would be replaced by a).
         */
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == 122) {
                char replace = 'a';
                a = a.substring(0, i) + replace + a.substring(i + 1);
            } else {
                int value = a.charAt(i);
                char replace = (char) (value + 1);
                a = a.substring(0, i) + replace + a.substring(i + 1);
            }
        }
        return a;
    }


    static boolean chessBoardCellColor(String cell1, String cell2) {
        /**
         * Given two cells on the standard chess board,
         * determine whether they have the same color or not.
         */
        int alphabet1 = cell1.charAt(0);
        int alphabet2 = cell2.charAt(0);
        return (alphabet1 + Integer.valueOf(cell1.charAt(1))) % 2 == (alphabet2 + Integer.valueOf(cell2.charAt(1))) % 2;
    }

}
