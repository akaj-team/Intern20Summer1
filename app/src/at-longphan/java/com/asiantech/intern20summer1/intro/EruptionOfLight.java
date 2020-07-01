package com.asiantech.intern20summer1.intro;

import java.util.Arrays;
import java.util.List;

public class EruptionOfLight {
    public static void main(String[] args) {
        //RUN main() with Coverage
        System.out.println("Ex 43: " + isBeautifulString("bbbaacdafe"));

        System.out.println("Ex 44: " + findEmailDomain("someaddress@yandex.ru"));

        System.out.println("Ex 45: " + buildPalindrome("abcdc"));

        System.out.println("Ex 46: " + electionsWinners(new int[]{2, 3, 5, 2}, 3));

        System.out.println("Ex 47: " + isMAC48Address("00-1B-63-84-45-E6"));
    }

    static boolean isBeautifulString(String a) {
        /**
         * A string is said to be beautiful if each letter
         * in the string appears at most as many times as the previous letter
         * in the alphabet within the string; ie: b occurs no more times than a;
         * c occurs no more times than b; etc.
         *
         * Given a string, check whether it is beautiful.
         */
        String[] x = a.split("");
        List<String> letters = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
        int[] countLetters = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < a.length(); i++) {
            countLetters[letters.indexOf(x[i])]++;
        }
        for (int i = 1; i < countLetters.length; i++) {
            if (countLetters[i] > countLetters[i - 1]) {
                return false;
            }
        }
        return true;
    }

    static String findEmailDomain(String address) {
        /**
         * An email address such as "John.Smith@example.com" is made up of a local part ("John.Smith"),
         * an "@" symbol, then a domain part ("example.com").
         *
         * The domain name part of an email address may only consist of letters, digits,
         * hyphens and dots. The local part, however, also allows a lot of different special characters.
         * Here you can look at several examples of correct and incorrect email addresses.
         *
         * Given a valid email address, find its domain part.
         */
        int lastIndex = address.lastIndexOf("@");
        return address.substring(lastIndex + 1);
    }

    static String buildPalindrome(String a) {
        /**
         * Given a string, find the shortest possible string
         * which can be achieved by adding characters to
         * the end of initial string to make it a palindrome.
         */
        StringBuilder aReverse = new StringBuilder();
        aReverse.append(a);
        aReverse = aReverse.reverse();
        if (aReverse.toString().equals(a)) {
            return a;
        } else {
            String insertStr = "";
            for (int i = 0; i < a.length(); i++) {
                char newCh = a.charAt(i);
                insertStr = newCh + insertStr;
                StringBuilder reverse = new StringBuilder();
                reverse.append(a + insertStr);
                reverse = reverse.reverse();
                if (reverse.toString().equals(a + insertStr)) return reverse.toString();
            }
        }
        return null;
    }

    static int electionsWinners(int[] votes, int k) {
        /**
         * Elections are in progress!
         *
         * Given an array of the numbers of votes given to each of the candidates so far,
         * and an integer k equal to the number of voters who haven't cast their vote yet,
         * find the number of candidates who still have a chance to win the election.
         *
         * The winner of the election must secure strictly more votes than any other candidate.
         * If two or more candidates receive the same (maximum) number of votes,
         * assume there is no winner at all.
         */
        if (k == 0) {
            int moreOne = 1;
            int max = 0;
            for (Integer item : votes) {
                if (item > max) {
                    max = item;
                    moreOne = 0;
                }
                if (item == max) moreOne++;
            }
            if (moreOne > 1) return 0;
            else return 1;
        }
        int count = 0;
        int voteCanWin = 0;
        for (Integer item : votes) {
            if (item > voteCanWin) voteCanWin = item;
        }
        voteCanWin++;
        for (Integer item : votes) {
            if (item + k >= voteCanWin) count++;
        }
        return count;
    }

    static boolean isMAC48Address(String a) {
        /**
         * A media access control address (MAC address) is a unique identifier assigned
         * to network interfaces for communications on the physical network segment.
         *
         * The standard (IEEE 802) format for printing MAC-48 addresses
         * in human-friendly form is six groups of two hexadecimal digits (0 to 9 or A to F),
         * separated by hyphens (e.g. 01-23-45-67-89-AB).
         *
         * Your task is to check by given string inputString whether it corresponds to MAC-48 address or not.
         */
        if (a.matches("[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}")) {
            return true;
        } else return false;
    }
}
