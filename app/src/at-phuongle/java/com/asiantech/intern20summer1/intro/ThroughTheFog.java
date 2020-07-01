package com.asiantech.intern20summer1.intro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.abs;

public class ThroughTheFog {
    /**
     * Write a funtion that returns the number which is written in the radially opposite position to firstNumber.
     */
    int circleOfNumbers(int n, int firstNumber) {
        int[] numbers1 = new int[n / 2];
        int[] numbers2 = new int[numbers1.length];
        int m = 0;
        int indexOfOpposite = 0;

        for (int i = 0; i < numbers1.length; i++) {
            numbers1[i] = m;
            m++;

        }

        for (int i = 0; i < numbers2.length; i++) {
            numbers2[i] = m;
            m++;
        }

        for (int i = 0; i < numbers1.length; i++) {
            if (firstNumber == numbers1[i]) {
                indexOfOpposite = i;
                return numbers2[indexOfOpposite];
            } else if (firstNumber == numbers2[i]) {
                indexOfOpposite = i;
                return numbers1[indexOfOpposite];
            }
        }
        return 0;
    }

    /**
     * Write a funtion that returns the number of year take for your balance to pass threshold.
     */
    int depositProfit(int deposit, int rate, int threshold) {
        double r = rate / 100D;
        double myDeposit = deposit;
        int numberOfYear = 0;
        while (true) {
            myDeposit += (r * myDeposit);
            numberOfYear++;
            if (myDeposit >= threshold) {
                break;
            }
        }
        return numberOfYear;
    }

    /**
     * Write a funtion that returns an integer representing the element from a
     * that minimizes the sum of its absolute differences with all other elements
     */
    int absoluteValuesSumMinimization(int[] a) {
        int s;
        int minSum;
        int result = 0;
        Map map = new HashMap<>();
        List<Integer> resultArr = new ArrayList<>();

        for (int i = 0; i < a.length; i++) {
            int x = a[i];
            s = 0;
            for (int j = 0; j < a.length; j++) {
                s += abs(a[j] - x);
            }
            map.put(a[i], s);
        }

        minSum = (int) map.values().toArray()[0];
        for (int i = 0; i < map.keySet().size(); i++) {
            if ((int) map.values().toArray()[i] < minSum) {
                minSum = (int) map.values().toArray()[i];
            }
        }

        Set set = map.keySet();
        for (Object key : set) {
            if ((int) map.get(key) == minSum) {
                resultArr.add((int) key);
            }
        }

        result = resultArr.get(0);
        for (int i = 0; i < resultArr.size(); i++) {
            if (result > resultArr.get(i)) {
                result = resultArr.get(i);
            }
        }

        return result;
    }
}
