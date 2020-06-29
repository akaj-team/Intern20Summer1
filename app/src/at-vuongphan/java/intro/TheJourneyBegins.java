package intro;

import java.util.Scanner;

public class TheJourneyBegins {
    private static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        /**
         * Write a function that returns the sum of two numbers.
         */
        int mParam1,mParam2;
        System.out.print("Input param1 = ");
        mParam1 = Integer.parseInt(input.nextLine());
        System.out.print("Input param2 = ");
        mParam2 = Integer.parseInt(input.nextLine());
        System.out.println("Result = "+add(mParam1,mParam2));

        /**
         * Given a year, return the century it is in. The first century spans
         * from the year 1 up to and including the year 100, the second - from
         * the year 101 up to and including the year 200, etc.
         */
        int mYear;
        System.out.print("Input Year = ");
        mYear = input.nextInt();
        System.out.println(centuryFromYear(mYear));
        /**
         * Given the string, check if it is a palindrome.
         */
        System.out.print("Input String = ");
        String inputString = input.nextLine();
        System.out.println(checkPalindrome(inputString));


    }
    private static int add(int param1, int param2){
        return param1 + param2;
    }
    private static int centuryFromYear(int year){
        if(year % 100 == 0){
            return year / 100;
        }else {
            return (year / 100) + 1;
        }
    }
    private static boolean  checkPalindrome(String inputString) {
        char[] charString = inputString.toCharArray();
        for(int mIndex=0; mIndex<(charString.length)/2;mIndex++){
            if(charString[mIndex] != charString[(charString.length)-1-mIndex])
            {
                return false;
            }
        }
        return true;
    }
}
