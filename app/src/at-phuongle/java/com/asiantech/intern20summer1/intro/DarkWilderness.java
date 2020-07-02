package com.asiantech.intern20summer1.intro;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DarkWilderness {
    /**
     * Write a funtion that returns the number of days that it will take
     * for the plant to reach / pass desiredHeight.
     */
    int growingPlant(int upSpeed, int downSpeed, int desiredHeight) {
        int height = 0;
        int day = 0;

        while (true) {
            height += upSpeed;
            day++;
            if (height >= desiredHeight) {
                break;
            } else {
                height -= downSpeed;
            }
        }

        return day;
    }

    /**
     * Write a funtion that returns the total maximum value of the items you can take.
     */
    int knapsackLight(int value1, int weight1, int value2, int weight2, int maxW) {
        int result = 0;
        int[] values = new int[]{value1, value2};
        Map map = new HashMap();

        Arrays.sort(values);
        map.put(value1, weight1);
        map.put(value2, weight2);

        for (int i = values.length - 1; i >= 0; i--) {
            if (maxW >= (int) map.get((Object) values[i])) {
                result += values[i];
                maxW -= (int) map.get((Object) values[i]);
            }
        }

        return result;
    }

    /**
     * Write a funtion that returns the longest prefix which contains only digits.
     */
    String longestDigitsPrefix(String inputString) {
        String result = "";
        for (int i = 0; i < inputString.length(); i++) {
            if (Character.isDigit(inputString.charAt(i))) {
                result += inputString.charAt(i);
            } else {
                break;
            }
        }

        return result;
    }

    /**
     * Write a funtion that returns the sum of digits.
     */
    int sumOfDigits(int n) {
        int sum = 0;
        while (n > 0) {
            sum += (n % 10);
            n /= 10;
        }

        return sum;
    }

    /**
     * Write a funtion that returns its digit degree.
     */
    int digitDegree(int n) {
        int count = 0;
        while (n >= 10) {
            count++;
            n = sumOfDigits(n);
        }

        return count;
    }

    /**
     * Write a funtion that checks whether the bishop can capture the pawn in one move.
     */
    boolean bishopAndPawn(String bishop, String pawn) {
        int i1Bishop = 7 - (bishop.charAt(0) - 97);
        int i2Bishop = bishop.charAt(1) - 49;
        int i1Pawn = 7 - (pawn.charAt(0) - 97);
        int i2Pawn = pawn.charAt(1) - 49;

        if (i1Pawn > i1Bishop && i2Pawn > i2Bishop) {
            if ((i1Pawn - i1Bishop) == (i2Pawn - i2Bishop)) {
                return true;
            }
        } else if (i1Pawn < i1Bishop && i2Pawn < i2Bishop) {
            if ((i1Bishop - i1Pawn) == (i2Bishop - i2Pawn)) {
                return true;
            }
        } else if (i1Pawn > i1Bishop && i2Pawn < i2Bishop) {
            if ((i1Pawn - i1Bishop) == (i2Bishop - i2Pawn)) {
                return true;
            }
        } else if (i1Pawn < i1Bishop && i2Pawn > i2Bishop) {
            if ((i1Bishop - i1Pawn) == (i2Pawn - i2Bishop)) {
                return true;
            }
        }

        return false;
    }
}
