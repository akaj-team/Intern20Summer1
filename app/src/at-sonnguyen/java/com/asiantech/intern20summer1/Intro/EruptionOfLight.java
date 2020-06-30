package com.asiantech.intern20summer1.Intro;

public class EruptionOfLight {
    /*
    *  check whether a String is beautiful.
    * A string is said to be beautiful if each letter in the string appears
    * at most as many times as the previous letter in the alphabet within the string;
    * ie: b occurs no more times than a; c occurs no more times than b
    */
    static boolean isBeautifulString(String inputString) {
        int[] frequencyOfCharacter = new int[26];
        for (int index: inputString.getBytes()) {
            frequencyOfCharacter[index - 97]++;
        }
        for (int index=1; index<frequencyOfCharacter.length; index++) {
            if (frequencyOfCharacter[index] > frequencyOfCharacter[index-1]) {
                return false;
            }
        }
        return true;
    }

    /*
    *Given a valid email address, find its domain part.
    */
    static String findEmailDomain(String address) {
        return address.substring(address.lastIndexOf("@") + 1);
    }
    /*
    *Given a string, find the shortest possible string
    * which can be achieved by adding characters
    * to the end of initial string to make it a palindrome.
    */
    static String buildPalindrome(String st) {

        if(st.equals(new StringBuilder(st).reverse().toString())) {
            return st;
        }
        String mas = "";
        for (int i = 0;i<st.length();i++){
            mas+= st.charAt(i);
            String pal = st + new StringBuilder(mas).reverse().toString();
            if (pal.equals(new StringBuilder(pal).reverse().toString())){
                return pal;
            }
        }
        return "";
    }
    /*
    *find the number of candidates who still have a chance to win the election.
    */
    static int electionsWinners(int[] votes, int k) {
        int maxVotes=0;
        boolean checkFrequencyOfMax = false ;
        int count = 0 ;
        for(int index = 0 ; index < votes.length ;index++){
            if (maxVotes < votes[index]){
                maxVotes = votes[index];
                checkFrequencyOfMax = false ;
            } else if (maxVotes == votes[index]){
                checkFrequencyOfMax = true;
            }
        }
        for (int index = 0 ; index < votes.length ; index++){
            if((k+votes[index] > maxVotes)&&k!=0){
                count++;
            }
        }
        if (k==0&& checkFrequencyOfMax == false){
            count = 1 ;
        } else if (k==0 && checkFrequencyOfMax == true ){
            count =0;
        }
        return count ;
    }
    /*
    *Check by given string inputString whether it corresponds to MAC-48 address or not
    */
    static boolean isMAC48Address(String inputString) {
        if (inputString.length() != 17){
            return false;
        }
        for (int index =0 ; index < inputString.length(); index++){
            if (index%3 == 2){
                if(inputString.charAt(index) != '-'){
                    return false;
                }
            } else {
                if (!('A'<=inputString.charAt(index) && inputString.charAt(index) <= 'F') && !(inputString.charAt(index) >='0' && inputString.charAt(index) <= '9' )){
                    return false ;
                }
            }
        }
        return true ;
    }

    public static void main(){
        String string1 = "aaaabbbcdea";
        boolean checkStringBeautiful = isBeautifulString(string1);
        System.out.print(checkStringBeautiful);
        String string2 = "fully-qualified-domain@codesignal.com";
        System.out.print(findEmailDomain(string2));
        String string3 = "ababab";
        System.out.print(buildPalindrome(string3));
        int[] inputAray ={2, 3, 5, 2};
        System.out.print(electionsWinners(inputAray,3));
        String string4 = "00-1B-63-84-45-E6";
        System.out.print(isMAC48Address(string4));
    }
}
