package com.asiantech.intern20summer1.intro;

public class DarkWilderness {
    /**
     * Caring for a plant can be hard work, but since you tend to it regularly, you have a plant
     * that grows consistently. Each day, its height increases by a fixed amount represented by the
     * integer upSpeed. But due to lack of sunlight, the plant decreases in height every night, by
     * an amount represented by downSpeed.
     * Since you grew the plant from a seed, it started at height 0 initially. Given an integer
     * desiredHeight, your task is to find how many days it'll take for the plant to reach this
     * height.
     */
    int growingPlant(int upSpeed, int downSpeed, int desiredHeight) {
        if (upSpeed > desiredHeight) {
            return 1;
        }
        if ((desiredHeight - upSpeed) < (upSpeed - downSpeed)) {
            return (desiredHeight - upSpeed) / (upSpeed - downSpeed) + 2;
        }
        return (desiredHeight - upSpeed) / (upSpeed - downSpeed) + 1;
    }

    /**
     * You found two items in a treasure chest! The first item weighs weight1 and is worth value1,
     * and the second item weighs weight2 and is worth value2. What is the total maximum value of
     * the items you can take with you, assuming that your max weight capacity is maxW and you can't
     * come back for the items later?
     * Note that there are only two items and you can't bring more than one item of each type, i.e.
     * you can't take two first items or two second items.
     */
    int knapsackLight(int value1, int weight1, int value2, int weight2, int maxW) {
        if (weight1 > maxW && weight2 > maxW) {
            return 0;
        }
        if (weight1 + weight2 <= maxW) {
            return value1 + value2;
        }
        if (weight1 > maxW) {
            return value2;
        }
        if (weight2 > maxW) {
            return value1;
        }
        return Math.max(value1, value2);
    }

    /**
     * Given a string, output its longest prefix which contains only digits.
     */
    String longestDigitsPrefix(String inputString) {
        String result = "";
        for (int i = 0; i < inputString.length(); i++) {
            if ('0' <= inputString.charAt(i) && inputString.charAt(i) <= '9') {
                result += inputString.charAt(i);
            } else break;
        }
        return result;
    }

    /**
     * Let's define digit degree of some positive integer as the number of times we need to replace
     * this number with the sum of its digits until we get to a one digit number.
     * Given an integer, find its digit degree.
     */
    int digitDegree(int n) {
        if (n / 10 == 0)
            return 0;
        int num = 0;
        while (n != 0) {
            num += n % 10;
            n /= 10;
        }
        return 1 + digitDegree(num);
    }

    /**
     * Given the positions of a white bishop and a black pawn on the standard chess board,
     * determine whether the bishop can capture the pawn in one move.
     * The bishop has no restrictions in distance for each move, but is limited to diagonal movement
     */
    boolean bishopAndPawn(String bishop, String pawn) {
        return (Math.abs(bishop.charAt(0) - pawn.charAt(0))
                == Math.abs(bishop.charAt(1) - pawn.charAt(1)));
    }
}
