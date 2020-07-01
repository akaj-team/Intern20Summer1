package com.asiantech.intern20summer1.intro;

public class RainbowOfClarity {
    public static void main(String[] args) {
        char symbol = 'g';
        String cell = "h1";
        String s = "aabbbc";
        int n = 35123;
        System.out.println(isDigit(symbol));
        System.out.println(lineEncoding(s));
        System.out.println(chessKnight(cell));
        System.out.println(deleteDigit(n));
    }

    static boolean isDigit(char symbol) {
//        Determine if the given character is a digit or not.
        // return(symbol>='0'&&symbol<='9');
        return (Character.isDigit(symbol));
    }

    static String lineEncoding(String s) {
//        Given a string,return its encoding defined as follows:
//
//        First, the string is divided into the least possible number of disjoint
//        substrings consisting of identical characters
//        for example, "aabbbc" is divided into["aa", "bbb", "c"]
//        Next, each substring with length greater than one is replaced with a concatenation of
//        its length and the repeating character
//        for example, substring "bbb" is replaced by "3b"
//        Finally, all the new strings are concatenated together in the same order and a new string
//        is returned.
        int so = 1;
        String string = "";
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i + 1) == s.charAt(i)) {
                so++;
            } else {
                if (so == 1) {
                    string += s.charAt(i);
                } else {
                    string += so + "" + s.charAt(i);
                    so = 1;
                }
            }
        }
        if (so == 1) {
            string += s.charAt(s.length() - 1);
        } else {
            System.out.print(string);
            string += so + "" + s.charAt(s.length() - 1);
        }
        return string;
    }

    static int chessKnight(String cell) {
//        Given a position of a knight on the standard chessboard, find the number of different
//        moves the knight can perform.
//
//                The knight can move to a square that is two squares horizontally and one square
//        vertically, or two squares vertically and one square horizontally away from it.The complete
//        move therefore looks like the letter L.Check out the image below to see all valid moves for
//        a knight piece that is placed on one of the central squares.
        int count = 0;
        if ((cell.charAt(0) - 1 >= 'a' && cell.charAt(0) - 1 <= 'h')
                && (cell.charAt(1) - 2 >= '1' && cell.charAt(1) - 2 <= '8')) {
            count++;
        }
        if ((cell.charAt(0) - 2 >= 'a' && cell.charAt(0) - 2 <= 'h')
                && (cell.charAt(1) - 1 >= '1' && cell.charAt(1) - 1 <= '8')) {
            count++;
        }
        if ((cell.charAt(0) + 1 >= 'a' && cell.charAt(0) + 1 <= 'h')
                && (cell.charAt(1) + 2 >= '1' && cell.charAt(1) + 2 <= '8')) {
            count++;
        }
        if ((cell.charAt(0) + 2 >= 'a' && cell.charAt(0) + 2 <= 'h')
                && (cell.charAt(1) + 1 >= '1' && cell.charAt(1) + 1 <= '8')) {
            count++;
        }
        if ((cell.charAt(0) - 1 >= 'a' && cell.charAt(0) - 1 <= 'h')
                && (cell.charAt(1) + 2 >= '1' && cell.charAt(1) + 2 <= '8')) {
            count++;
        }
        if ((cell.charAt(0) - 2 >= 'a' && cell.charAt(0) - 2 <= 'h')
                && (cell.charAt(1) + 1 >= '1' && cell.charAt(1) + 1 <= '8')) {
            count++;
        }
        if ((cell.charAt(0) + 1 >= 'a' && cell.charAt(0) + 1 <= 'h')
                && (cell.charAt(1) - 2 >= '1' && cell.charAt(1) - 2 <= '8')) {
            count++;
        }
        if ((cell.charAt(0) + 2 >= 'a' && cell.charAt(0) + 2 <= 'h')
                && (cell.charAt(1) - 1 >= '1' && cell.charAt(1) - 1 <= '8')) {
            count++;
        }
        return count++;
    }

    static int deleteDigit(int n) {
//        Given some integer, find the maximal number you can obtain by deleting exactly one digit of
//        the given number.
                String s = Integer.toString(n);
        int max = Integer.valueOf(s.substring(1));
        for (int i = 1; i < s.length(); i++) {
            if (i == s.length() - 1) {
                if (max < Integer.valueOf(s.substring(0, i))) {
                    max = Integer.valueOf(s.substring(0, i));
                }
            } else if (max < Integer.valueOf(s.substring(0, i) + s.substring(i + 1, s.length()))) {
                max = Integer.valueOf(s.substring(0, i) + s.substring(i + 1, s.length()));
            }

            System.out.println(max);
        }
        return max;
    }

}
