package com.asiantech.intern20summer1.intro;

public class EruptionOfLight {
    /**
     * Write a funtion that returns the domain part of the given email address.
     */
    String findEmailDomain(String address) {
        int indexOfAt = 0;
        for (int i = 0; i < address.length(); i++) {
            if (address.charAt(i) == '@') {
                indexOfAt = i;
            }
        }

        return address.substring(indexOfAt + 1);
    }

    /**
     * Write a funtion that checks if the given string corresponds to MAC-48 address naming rules.
     */
    boolean isMAC48Address(String inputString) {
        return inputString.matches("(([0-9A-F]{2}[:-]?){6})");
    }

    /**
     * Write a function that returns the shortest possible string which
     * can be achieved by adding characters to the end of initial string to make it a palindrome.
     */
    String buildPalindrome(String st) {
        boolean canConvert;
        for (int i = st.length(); ; i++) {
            canConvert = true;
            for (int j = 0; j < i - j - 1; j++) {
                if (i - j <= st.length() && (st.charAt(j) != st.charAt(i - j - 1))) {
                    canConvert = false;
                    break;
                }
            }
            if (canConvert) {
                for (int j = st.length(); j < i; j++) {
                    st += st.charAt(i - j - 1);
                }
                return st;
            }
        }
    }
}