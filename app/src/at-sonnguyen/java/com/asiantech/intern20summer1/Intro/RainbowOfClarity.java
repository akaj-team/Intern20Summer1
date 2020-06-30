package com.asiantech.intern20summer1.Intro;

public class RainbowOfClarity {

    /*
    *Determine if the given character is a digit or not.
    */
    static boolean isDigit(char symbol) {
        if(symbol<'0'||symbol>'9'){
            return false;
        }
        return true;
    }
    /*
    *all the new strings are concatenated together in the same order and a new string is returned.
    */
    static String lineEncoding(String s) {
        int count = 0;
        String pre_s = "";
        String result = "";
        for(int i = 0; i < s.length(); i++)
        {
            if(String.valueOf(s.charAt(i)).equals(pre_s) || i == 0)
            {count++;}
            else if(!String.valueOf(s.charAt(i)).equals(pre_s)){
                if(count > 1){result += count;}
                result += pre_s;
                count = 1;
            }
            pre_s = String.valueOf(s.charAt(i));
        }
        if(count > 1){result += count;}
        result += String.valueOf(s.substring(s.length()-1));
        return result;
    }
    /*
    *Given a position of a knight on the standard chessboard, find the number of different moves the knight can perform.
    */
    static int chessKnight(String cell) {
        int count = 0;
        int x = cell.charAt(0) % 97,
                y = cell.charAt(1) - '0' - 1;
        for (int i = x - 2 ; i <= x+2 ; i++ ){
            for (int j = y -2; j <= y+2 ; j++){
                if (i < 0 || j < 0||i>7||j>7) {
                    continue ;
                } else if ((Math.abs(i-x)==1&&Math.abs(j-y)==2)||(Math.abs(i-x)==2&&Math.abs(j-y)==1)){
                    count++ ;
                }
            }
        }
        return count ;
    }
    /*
    * Given some integer, find the maximal number you can obtain by deleting exactly one digit of the given number.
    */
    static int deleteDigit(int n) {
        String string = String.valueOf(n);
        int Max = 0 ;
        for (int index = 0 ; index < string.length(); index++){
            StringBuilder stringOfNumber = new StringBuilder(string);
            stringOfNumber.deleteCharAt(index);
            if (Integer.parseInt(stringOfNumber.toString()) > Max){
                Max = Integer.parseInt(stringOfNumber.toString());
            }
        }
        return Max ;

    }
    public static void main(){
        char charToCheck = '9';
        System.out.print(charToCheck);
        String string1 = "aabbbc";
        System.out.print(lineEncoding(string1));
        System.out.print(chessKnight("A1"));
        System.out.print(deleteDigit(1001));

    }
}
