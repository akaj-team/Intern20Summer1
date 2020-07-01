package com.asiantech.intern20summer1.intro;

public class DarkWilderness {
    public static void main(String[] args) {
        int upSpeed = 10;
        int downSpeed = 1;
        int heigh = 5;
        int w1 = 1;
        int w2 = 2;
        int v1 = 1;
        int v2 = 2;
        int maxW = 1;
        String s ="asdasdad";
        String bis = "h1";
        String pawn = "c2";
        System.out.println(growingPlant(upSpeed,downSpeed, heigh));
        System.out.println(knapsackLight(w1, w2, v1, v2, maxW));
        System.out.println(longestDigitsPrefix(s));
        System.out.println(digitDegree(upSpeed));
        System.out.println(bishopAndPawn(bis, pawn));
    }

    static int growingPlant(int upSpeed, int downSpeed, int desiredHeight) {
//        Caring for a plant can be hard work, but since you tend to it regularly, you have a
//        plant that grows consistently.Each day, its height increases by a fixed amount
//        represented by the integer upSpeed.But due to lack of sunlight, the plant decreases
//        in height every night, by an amount represented by downSpeed.
//
//                Since you grew the plant from a seed, it started at height 0 initially.Given an
//        integer desiredHeight, your task is to find how many days it
//        'll take for the plant to reach this height.
        int h = 0;
        int count = 0;
        while (h < desiredHeight) {
            h += upSpeed;
            if (h < desiredHeight) {
                h -= downSpeed;
            }
            count++;
        }
        return count;
    }

    static int knapsackLight(int value1, int weight1, int value2, int weight2, int maxW) {
//        You found two items in a treasure chest !The first item weighs weight1 and is
//        worth value1, and the second item weighs weight2 and is worth value2.What is the total
//        maximum value of the items you can take with you, assuming that your max weight capacity is
//        maxW and you can 't come back for the items later?
//
//        Note that there are only two items and you can
//        't bring more than one item of each type, i.e. you can' t take two first items or two second
//        items.
        return ((weight1 > maxW && weight2 <= maxW) ? value2 :
                (weight2 > maxW && weight1 <= maxW) ? value1 :
                        (weight1 > maxW && weight2 > maxW) ? 0 :
                                (weight1 + weight2) <= maxW ? (value1 + value2) :
                                        value1 > value2 ? value1 : value2);
    }

    static String longestDigitsPrefix(String inputString) {
//        Given a string, output its longest prefix which contains only digits.
        String[] s = inputString.split("[^0-9]");
        if (s.length == 0) {
            return "";
        }
        return s[0];
    }

    static int digitDegree(int n) {
//        Let's define digit degree of some positive integer as the number of times we need to
//        replace this number with the sum of its digits until we get to a one digit number.
//
//        Given an integer, find its digit degree.
        int count = 0;
        while (n > 9) {
            int sum = 0;
            String s = Integer.toString(n);
            for (int i = 0; i < s.length(); i++) {
                sum += (s.charAt(i) - '0');
            }
            n = sum;
            count++;
        }
        return count;
    }

    static boolean bishopAndPawn(String bishop, String pawn) {
//        Given the positions of a white bishop and a black pawn on the standard
//        chess board, determine whether the bishop can capture the pawn in one move.
//
//        The bishop has no restrictions in distance for each move, but is limited to diagonal
//        movement.Check out the example below to see how it can move:
        return (Math.abs(bishop.charAt(0) - pawn.charAt(0)) ==
                Math.abs(bishop.charAt(1) - pawn.charAt(1)));
    }


}
