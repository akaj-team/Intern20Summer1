package com.asiantech.intern20summer1.intro;

public class RainbowOfClarity {
    /**
     * Determine if the given character is a digit or not.
     */
    boolean isDigit(char symbol) {
        return Character.isDigit(symbol);
    }

    /**
     * Given a string, return its encoding defined as follows:
     * First, the string is divided into the least possible number of disjoint substrings
     * consisting of identical characters for example, "aabbbc" is divided into ["aa", "bbb", "c"]
     * Next, each substring with length greater than one is replaced with a concatenation of its
     * length and the repeating character for example, substring "bbb" is replaced by "3b"
     * Finally, all the new strings are concatenated together in the same order and a new string
     * is returned.
     */
    String lineEncoding(String s) {
        StringBuilder s1 = new StringBuilder();
        int count = 1, n = 0;
        for (int i = 1; i < s.length(); i++) {
            n = i;
            if (s.charAt(i) == s.charAt(i - 1)) count++;
            else {
                if (count == 1) s1.append(s.charAt(i - 1));
                else {
                    s1.append(count).append(s.charAt(i - 1));
                    count = 1;
                }
            }
        }
        if (count == 1) s1.append(s.charAt(n));
        else {
            s1.append(count).append(s.charAt(n));
        }
        return s1.toString();
    }

    /**
     * Given a position of a knight on the standard chessboard, find the number of different moves
     * the knight can perform.
     * The knight can move to a square that is two squares horizontally and one square vertically,
     * or two squares vertically and one square horizontally away from it. The complete move
     * therefore looks like the letter L. Check out the image below to see all valid moves for a
     * knight piece that is placed on one of the central squares.
     */
    int chessKnight(String cell) {
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

    /**
     * Given some integer, find the maximal number you can obtain by deleting exactly one digit of
     * the given number.
     */
    int deleteDigit(int n) {
        int max = 0;
        for (int t = 1; t < n; t *= 10)
            max = Math.max(n / 10 / t * t + n % t, max);
        return max;
    }
}
