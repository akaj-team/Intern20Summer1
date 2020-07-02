package com.asiantech.intern20summer1.intro;

public class EruptionOfLight {
    /**
     * Write a funtion that returns the domain part of the given email address.
     */
    static String findEmailDomain(String address) {
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
    static boolean isMAC48Address(String inputString) {
        return inputString.matches("(([0-9A-F]{2}[:-]?){6})");
    }
}