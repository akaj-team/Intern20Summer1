package com.asiantech.intern20summer1.intro;

import java.util.Arrays;

public class EruptionOfLight {
    /**
     * A string is said to be beautiful if each letter in the string appears at most as many times
     * as the previous letter in the alphabet within the string; ie: b occurs no more times than a;
     * c occurs no more times than b; etc.
     * Given a string, check whether it is beautiful.
     */
    boolean isBeautifulString(String inputString) {
        boolean result = true;
        for (char i = 'a'; i < 'z'; i++) {
            char next = (char) (i + 1);
            if (Count(inputString, i) < Count(inputString, next)) {
                result = false;
                break;
            }
        }
        return result;
    }

    int Count(String inputString, char inputChar) {
        int count = 0;
        for (int i = 0; i < inputString.length(); i++) {
            if (inputString.charAt(i) == inputChar)
                count++;
        }
        return count;
    }

    /**
     * An email address such as "John.Smith@example.com" is made up of a local part ("John.Smith"),
     * an "@" symbol, then a domain part ("example.com").
     * The domain name part of an email address may only consist of letters, digits, hyphens and
     * dots. The local part, however, also allows a lot of different special characters. Here you
     * can look at several examples of correct and incorrect email addresses.
     * Given a valid email address, find its domain part.
     */
    String findEmailDomain(String address) {
        String[] pieces = address.split("\\@");
        if (pieces.length == 2) {
            return pieces[1];
        } else {
            return pieces[pieces.length - 1];
        }
    }

    /**
     * Given a string, find the shortest possible string which can be achieved by adding characters
     * to the end of initial string to make it a palindrome.
     */
    String buildPalindrome(String st) {
        boolean flag;
        StringBuilder stBuilder = new StringBuilder(st);
        for (int i = stBuilder.length(); ; i++) {
            flag = true;
            for (int j = 0; j < i - j - 1; j++) {
                if (i - j <= stBuilder.length() && stBuilder.charAt(j) != stBuilder.charAt(i - j - 1)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                for (int j = stBuilder.length(); j < i; j++) {
                    stBuilder.append(stBuilder.charAt(i - j - 1));
                }
                return stBuilder.toString();
            }
        }
    }

    /**
     * Elections are in progress!
     * <p>
     * Given an array of the numbers of votes given to each of the candidates so far, and an
     * integer k equal to the number of voters who haven't cast their vote yet, find the number of
     * candidates who still have a chance to win the election.
     * The winner of the election must secure strictly more votes than any other candidate. If two
     * or more candidates receive the same (maximum) number of votes, assume there is no winner at
     * all.
     */
    int electionsWinners(int[] votes, int k) {
        int count = 0;
        Arrays.sort(votes);
        int max = votes[votes.length - 1];
        if (k == 0) {
            for (int vote : votes) {
                if (vote == max) count++;
            }
            if (count >= 2) return 0;
        } else {
            for (int vote : votes) {
                if (vote + k > max) count++;
            }
        }
        return count;
    }

    /**
     * A media access control address (MAC address) is a unique identifier assigned to network
     * interfaces for communications on the physical network segment.
     * The standard (IEEE 802) format for printing MAC-48 addresses in human-friendly form is six
     * groups of two hexadecimal digits (0 to 9 or A to F), separated by hyphens
     * (e.g. 01-23-45-67-89-AB).
     * Your task is to check by given string inputString whether it corresponds to MAC-48 address
     * or not.
     */
    boolean isMAC48Address(String inputString) {
        return inputString.matches("^([0-9A-F][0-9A-F]-){5}[0-9A-F][0-9A-F]$");
    }
}
