package com.asiantech.intern20summer1.intro;

public class SmoothSailing {
    public static void main(String[] args) {
        //RUN main() with Coverage
        String[] arrayString = allLongestStrings(new String[]{"aba", "aa", "ad", "vcd", "aba"});
        System.out.print("Ex 9: [ ");
        for (String str : arrayString) {
            System.out.print(str + " ");
        }
        System.out.println("]");
    }

    static String[] allLongestStrings(String[] inputArray) {
        /**
         * Given an array of strings,
         * return another array containing all of its longest strings.
         */
        String array = "";
        int maxLength = 0;
        for (String str : inputArray) {
            if (str.length() > maxLength) {
                maxLength = str.length();
                array = str + ",";
            } else if (str.length() == maxLength) {
                array += str + ",";
            }
        }
        return array.split(",");
    }
}
