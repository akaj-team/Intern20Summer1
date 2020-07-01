package com.asiantech.intern20summer1.Intro;

public class TheJourneyBegins {
    /*
    * returns the sum of two numbers.
    */
    static int add(int param1, int param2) {
        return param1 + param2 ;
    }

    /*
     * return the century the year is in
     */
    static int centuryFromYear(int year) {
        if (year % 100 == 0){
            return year/100;
        }
        return year/100 + 1 ;
    }
    /*
     * check palindrome.
     */
    static boolean checkPalindrome(String inputString) {
        char [] arrayInputString = inputString.toCharArray();
        for (int i =0 ; i <= inputString.length()/2 ; i++){
            if (arrayInputString[i] != arrayInputString[inputString.length()-1 - i]){
                return false;
            }
        }
        return true;
    }
    public static void main(){
        int n1 = 15 ,n2 = 20;
        int Sum =  add(n1,n2);
        System.out.println(n1+ "+"+ n2 + " = "+ Sum);
        int year = 2000;
        int Century = centuryFromYear(year);
        System.out.println(year +" in "+Century);
        String s = "aabaa";
        boolean CheckPalindrome = checkPalindrome(s);
        System.out.print(s +" is Palindrome :  "+checkPalindrome(s) );
    }
}
