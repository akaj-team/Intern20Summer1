package com.asiantech.intern20summer1.intro;

public class RainbowOfClarity {
    public static void main(String[] args) {
        //RUN main() with Coverage
        System.out.println("Ex 48: " + isDigit('!'));

        System.out.println("Ex 49: " + lineEncoding("aabbbc"));

        System.out.println("Ex 50: " + chessKnight("b1"));

        System.out.println("Ex 51: " + deleteDigit(152));
    }

    static boolean isDigit(char symbol) {
        /**
         * Determine if the given character is a digit or not.
         */
        if (symbol > 47 && symbol < 58) return true;
        else return false;
    }

    static String lineEncoding(String s) {
        /**
         * Given a string, return its encoding defined as follows:
         *
         * First, the string is divided into the least possible number of
         * disjoint substrings consisting of identical characters
         * for example, "aabbbc" is divided into ["aa", "bbb", "c"]
         * Next, each substring with length greater than one is replaced with
         * a concatenation of its length and the repeating character
         * for example, substring "bbb" is replaced by "3b"
         * Finally, all the new strings are concatenated together
         * in the same order and a new string is returned.
         */
        int size = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != s.charAt(i - 1)) {
                size++;
            }
        }
        Character[] letters = new Character[size];

        letters[0] = s.charAt(0);
        int indexLetters = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == letters[indexLetters]) {

            } else {
                indexLetters++;
                letters[indexLetters] = s.charAt(i);
            }
        }

        int[] repeats = new int[size];
        repeats[0] = 1;

        int indexRepeats = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                repeats[indexRepeats]++;
            } else {
                indexRepeats++;
                repeats[indexRepeats]++;
            }
        }

        String result = "";
        for (int i = 0; i < letters.length; i++) {
            if (repeats[i] == 1) {
                result += Character.toString(letters[i]);
            } else {
                result += repeats[i] + Character.toString(letters[i]);
            }
        }
        return result;
    }

    static int chessKnight(String cell) {
        /**
         * Given a position of a knight on the standard chessboard,
         * find the number of different moves the knight can perform.
         *
         * The knight can move to a square that is two squares horizontally
         * and one square vertically, or two squares vertically
         * and one square horizontally away from it.
         * The complete move therefore looks like the letter L.
         * Check out the image below to see all valid moves for a knight piece
         * that is placed on one of the central squares.
         */
        int moves = 8;
        if (cell.charAt(0) == 'b' || cell.charAt(0) == 'g') moves -= 2;
        if (cell.charAt(1) == '2' || cell.charAt(1) == '7') moves -= 2;
        if (cell.charAt(0) == 'a' || cell.charAt(0) == 'h') moves /= 2;
        if (cell.charAt(1) == '1' || cell.charAt(1) == '8') moves /= 2;
        return moves;
    }

    static int deleteDigit(int n) {
        /**
         * Given some integer, find the maximal number you can obtain by
         * deleting exactly one digit of the given number.
         */
        String stringN = String.valueOf(n);
        int length = stringN.length();
        int[] x = new int[length];
        for (int i = 0; i < length; i++) {
            String stringDeleted = stringN.substring(0, i) + stringN.substring(i + 1);
            x[i] = Integer.valueOf(stringDeleted);
        }
        int max = 0;
        for (Integer item : x) {
            max = (item > max) ? item : max;
        }
        return max;
    }
}
