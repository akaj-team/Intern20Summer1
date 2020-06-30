package com.asiantech.intern20summer1.intro;

public class DarkWilderness {
    public static void main(String[] args) {
        //RUN main() with Coverage
        System.out.println("Ex 38: " + growingPlant(100, 10, 910));

        System.out.println("Ex 39: " + knapsackLight(10, 5, 6, 4, 8));

        System.out.println("Ex 40: " + longestDigitsPrefix("123aa1"));

        System.out.println("Ex 41: " + digitDegree(73));

        System.out.println("Ex 42: " + bishopAndPawn("a1", "c3"));
    }

    static int growingPlant(int upSpeed, int downSpeed, int desiredHeight) {
        /**
         * Caring for a plant can be hard work, but since you tend to it regularly,
         * you have a plant that grows consistently.
         * Each day, its height increases by a fixed amount represented by the integer upSpeed.
         * But due to lack of sunlight, the plant decreases in height every night,
         * by an amount represented by downSpeed.
         *
         * Since you grew the plant from a seed, it started at height 0 initially.
         * Given an integer desiredHeight, your task is to find how many days it'll take
         * for the plant to reach this height.
         */
        int height = 0;
        for (int day = 1; ; day++) {
            height += upSpeed;
            if (height >= desiredHeight) return day;
            height -= downSpeed;
        }
    }

    static int knapsackLight(int value1, int weight1, int value2, int weight2, int maxW) {
        /**
         * You found two items in a treasure chest!
         * The first item weighs weight1 and is worth value1,
         * and the second item weighs weight2 and is worth value2.
         * What is the total maximum value of the items you can take with you,
         * assuming that your max weight capacity is maxW and you can't come back for the items later?
         *
         * Note that there are only two items and you can't bring more than one item of each type,
         * i.e. you can't take two first items or two second items.
         */
        if (maxW >= weight1 + weight2) return value1 + value2;
        if (maxW < weight1 && maxW < weight2) return 0;
        if (weight1 > maxW) return value2;
        if (weight2 > maxW) return value1;
        return Math.max(value1, value2);
    }

    static String longestDigitsPrefix(String a) {
        /**
         * Given a string, output its longest prefix which contains only digits.
         */
        String result = "";
        for (int i = 0; i < a.length(); i++) {
            if (Character.isDigit(a.charAt(i))) {
                result += a.charAt(i);
            } else {
                break;
            }
        }
        return result;
    }

    static int digitDegree(int n) {
        /**
         * Let's define digit degree of some positive integer
         * as the number of times we need to replace this number
         * with the sum of its digits until we get to a one digit number.
         *
         * Given an integer, find its digit degree.
         */
        int count = 0;
        int sum = 0;
        for (; ; count++) {
            if (String.valueOf(n).length() == 1) return count;
            for (int i = 0; i < String.valueOf(n).length(); i++) {
                sum += Character.getNumericValue(String.valueOf(n).charAt(i));
            }
            n = sum;
            sum = 0;
        }
    }

    static boolean bishopAndPawn(String bishop, String pawn) {
        /**
         * Given the positions of a white bishop and a black pawn on the standard chess board,
         * determine whether the bishop can capture the pawn in one move.
         *
         * The bishop has no restrictions in distance for each move,
         * but is limited to diagonal movement.
         */
        int bishopValue = bishop.charAt(0) + bishop.charAt(1);
        int pawnValue = pawn.charAt(0) + pawn.charAt(1);
        if (bishopValue == pawnValue) return true;
        if (pawn.charAt(0) > bishop.charAt(0)) {
            if (bishopValue == pawnValue - (pawn.charAt(0) - bishop.charAt(0)) * 2) {
                return true;
            } else return false;
        } else {
            if (bishopValue == pawnValue + (bishop.charAt(0) - pawn.charAt(0)) * 2) {
                return true;
            } else return false;
        }
    }
}
