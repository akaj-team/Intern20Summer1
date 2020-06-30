package com.asiantech.intern20summer1.intro;

public class SmoothSailing {
    public static void main(String[] args) {
        String[] string = new String[]{"asdasd", "zcvxcb", "az"};
        int[] intArray = new int[]{1,4,6,7,9,6,4,2,1};
        System.out.println(allLongestStrings(string));
        System.out.println(commonCharacterCount("asdgxcv", "asdasdfg"));
        System.out.println(isLucky(24534));
        System.out.println(sortByHeight(intArray));
    }

    static String[] allLongestStrings(String[] inputArray) {
//        Given an array of strings, return another array containing all of its longest strings.
        int l = inputArray.length;
        int size = 0;
        int max = 0;
        int tmp = 0;
        for (int i = 0; i < l; i++) {
            if (inputArray[i].length() > max) {
                max = inputArray[i].length();
            }
        }
        for (int i = 0; i < l; i++) {
            if (inputArray[i].length() == max) {
                size++;
            }
        }
        String[] longest = new String[size];
        for (int i = 0; i < l; i++) {
            if (inputArray[i].length() == max) {
                longest[tmp] = inputArray[i];
                tmp++;
            }
        }
        return longest;
    }

    static int commonCharacterCount(String s1, String s2) {
//        Given two strings, find the number of common characters between them.
        int countcommon = 0;
        int l1 = s1.length();
        int l2 = s2.length();
        char[] c1 = new char[l1];
        char[] c2 = new char[l2];
        for (int i = 0; i < l1; i++) {
            c1[i] = s1.charAt(i);
        }
        for (int i = 0; i < l2; i++) {
            c2[i] = s2.charAt(i);
        }
        for (int i = 0; i < l1; i++) {
            int count = 0;
            for (int j = 0; j < l2; j++) {
                if (c1[i] == c2[j]) {
                    count++;
                }
            }
            int dup = 0;
            for (int j = 0; j < i; j++) {
                if (c1[j] == c1[i]) {
                    dup++;
                }
            }
            if (dup < count) {
                countcommon++;
            }
        }
        return countcommon;
    }

    static boolean isLucky(int n) {
//        Ticket numbers usually consist of an even number of digits.A ticket number is
//        considered lucky if the sum of the first half of the digits is equal to the sum of the
//        second half.
//
//        Given a ticket number n, determine if it 's lucky or not.
        String sn = String.valueOf(n);
        int head = 0;
        int tail = 0;
        int l = sn.length();
        int[] an = new int[l];
        if (l % 2 == 1) {
            return false;
        }
        for (int i = 0; i < l; i++) {
            an[i] = sn.charAt(i);
        }
        for (int i = 0; i < l / 2; i++) {
            head += an[i];
        }
        for (int i = l / 2; i < l && i >= l / 2; i++) {
            tail += an[i];
        }
        if (head == tail) {
            return true;
        }
        return false;
    }

    static int[] sortByHeight(int[] a) {
//        Some people are standing in a row in a park.There are trees between them which cannot be
//        moved.Your task is to rearrange the people by their heights in a non - descending
//        order without moving the trees.People can be very tall !
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                int tmp = 0;
                if (a[j] != -1 && a[i] != -1 && a[j] < a[i]) {
                    tmp = a[i];
                    a[i] = a[j];
                    a[j] = tmp;
                }
            }
        }
        return a;
    }

}
