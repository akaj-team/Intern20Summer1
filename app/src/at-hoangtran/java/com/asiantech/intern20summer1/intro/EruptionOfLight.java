package com.asiantech.intern20summer1.intro;

public class EruptionOfLight {
    public static void main(String[] args) {
        String s = "aaabbccddd";
        String mail = "asdasdadffgfs@adnfdjgbduf.com";
        int vote[] = {5, 4, 4, 8, 6, 2, 7};
        int k = 3;
        System.out.println(isBeautifulString(s));
        System.out.println(findEmailDomain(mail));
//        System.out.println(buildPalindrome(s));
        System.out.println(electionsWinners(vote, k));
        System.out.println(isMAC48Address(s));
    }


    static boolean isBeautifulString(String inputString) {
//        A string is said to be beautiful if each letter in the string appears at most as many
//        times as the previous letter in the alphabet within the string;
//        ie:
//        b occurs no more times than a;
//        c occurs no more times than b;
//        etc.
//
//                Given a string, check whether it is beautiful.
        char min = 'a';
        int maxcount = 0;
        for (int i = 0; i < inputString.length(); i++) {
            if (inputString.charAt(i) == min) {
                maxcount++;
            }
        }
        for (int i = min + 1; i <= 'z'; i++) {
            int count = 0;
            for (int j = 0; j < inputString.length(); j++) {
                if (inputString.charAt(j) == i) {
                    count++;
                }
                if (count > maxcount) {
                    return false;
                }
            }
            maxcount = count;
        }
        return true;
    }

    static String findEmailDomain(String address) {
//        An email address such as "John.Smith@example.com" is made up of a local
//        part("John.Smith"), an "@" symbol, then a domain part("example.com").
//
//                The domain name part of an email address may only consist
//        of letters, digits, hyphens and dots.The local part, however, also allows a lot of
//        different special characters.Here you can look at several examples of correct and
//        incorrect email addresses.
//
//                Given a valid email address, find its domain part.
        String[] s = address.split("@");
        String domain = s[s.length - 1];
        return domain;
    }

    static String buildPalindrome(String st) {
//        Given a string, find the shortest possible string which can be achieved by adding
//        characters to the end of initial string to make it a palindrome.
        int count = 0;
        for (int i = 0; i < st.length() / 2; i++) {
            if (st.charAt(i) == st.charAt(st.length() - 1 - i)) {
                count++;
                for (int j = 0; j <= i; j++) {
                    st += st.charAt(j);
                }
                return st;
            }
        }
        if (count == st.length() / 2) {
            return st;
        }
        for (int i = 0; i < count; i++) {
            st += st.charAt(count - i - 1);
        }
        return st;
    }

    static int electionsWinners(int[] votes, int k) {
//        Elections are in progress !
//
//                Given an array of the numbers of votes given to each of the candidates so far, and
//        an integer k equal to the number of voters who haven
//        't cast their vote yet, find the number of candidates who still have a chance to win the election.
//
//        The winner of the election must secure strictly more votes than any other candidate.If two
//        or more candidates receive the same (maximum) number of votes, assume there is no winner
//        at all.
        int max = votes[0];
        int count = 0;
        int a = 0;
        for (int i = 1; i < votes.length; i++) {
            if (max < votes[i]) {
                max = votes[i];
            }
        }
        for (int i = 0; i < votes.length; i++) {
            if (max == votes[i]) {
                a++;
            }
        }
        if (a == 1 && k == 0) {
            return 1;
        }
        for (int i = 0; i < votes.length; i++) {
            if (votes[i] + k > max) {
                count++;
            }

        }
        return count;
    }

    static boolean isMAC48Address(String inputString) {
//        A media access control address(MAC address) is a unique identifier assigned to
//        network interfaces for communications on the physical network segment.
//
//        The standard (IEEE 802)format for printing MAC -48 addresses in human - friendly form is
//        six groups of two hexadecimal digits (0 to 9 or A to F),separated by
//        hyphens(e.g. 01 - 23 - 45 - 67 - 89 - AB).
//
//                Your task is to check by given string inputString whether it corresponds to MAC - 48
//        address or not.
        return (inputString.matches("([0-9A-F][0-9A-F]-){5}[0-9A-F][0-9A-F]"));
    }

}
