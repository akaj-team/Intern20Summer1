package com.asiantech.intern20summer1.Intro;

import java.util.Arrays;

public class ExploringTheWaters{

    /*
    *Return an array of two integers, where the first element is the total weight of team 1,
    * and the second element is the total weight of team 2 after the division is complete.
    */
    static int[] alternatingSums(int[] a) {
        int[] Result = new int [2];
        for ( int index = 0; index < a.length; index++ ){
            if (index%2 == 0){
                Result[0] += a[index];
            } else {
                Result[1] += a[index];
            }
        }
        return Result;
    }
    /*
    * add a border of asterisks(*) to a Matrix
    */

    static String[] addBorder(String[] picture) {
        String[] bordered = new String[picture.length+2];
        String borders = "";
        for(int i = 0; i < picture[0].length()+2;i++) {
            borders += "*";
        }
        bordered[0] = borders;
        bordered[bordered.length-1] = borders;
        for(int i = 0;i < picture.length;i++) {
            bordered[i+1] = "*" + picture[i] + "*";
        }
        return bordered;
    }
    /*
    *  check whether 2 Arrays are similar.
    */
    static boolean areSimilar(int[] A, int[] B) {
        if(A.length != B.length) return false;

        int countSwap = 0;
        int[] copyA = Arrays.copyOf(A, A.length);
        int[] copyB = Arrays.copyOf(B, B.length);

        // checking both contain the same elements...
        Arrays.sort(copyA); Arrays.sort(copyB);
        if(!Arrays.equals(copyA, copyB)) return false;

        // checking for min 2 swaps using original arrays...
        for(int i = 0; i < A.length; i++) {
            if(A[i] != B[i]) countSwap++;
        }

        return (countSwap == 2 || countSwap == 0);
    }
    /*
    *Find the minimal number of moves required to obtain a strictly increasing sequence from the input.
    */
    static int arrayChange(int[] inputArray) {
        int count = 0;
        for ( int index = 0 ; index < inputArray.length - 1 ; index++){
            while (inputArray[index]>=inputArray[index+1]){
                inputArray[index+1] += 1;
                count++;
            }
        }
        return count;
    }
    /*
    *find out if its characters can be rearranged to form a palindrome.
    */
    static boolean palindromeRearranging(String inputString) {

        int[] s = new int[26];

        for(int i = 0; i < inputString.length(); i++){
            s[inputString.charAt(i) - 'a']++;
        }
        int count = 0, countSingle = 0;
        for(int i = 0; i < s.length; i++){
            if(s[i] != 0 && s[i] % 2 == 0){
                count ++;
            }else if (s[i] != 0 && s[i] % 2 == 1){
                if(inputString.length() % 2 != 0){
                    if(countSingle == 1)
                        return false;
                    else
                        countSingle++;
                }else
                    return false;
            }
        }

        return true;
    }
    public static void main(){
        int [] inputArray = {150,160,170,120,153,162};
        int [] Result = new  int[2];
        Result = alternatingSums(inputArray);
        for (int index = 0 ; index < Result.length ; index++){
            System.out.print(Result[index]);
        }
        String[] StringPicture = {"abc" , "adc"};
        String[] Result2 = addBorder(StringPicture);
        for (int i = 0 ; i < Result2.length ; i++){
            System.out.print(Result2[i]);
        }
        int [] inputArray1 = {3,5,6,7};
        int[] inputArray2 = {3,4,6,7};
        System.out.print(areSimilar(inputArray1,inputArray2));
        System.out.print(arrayChange(inputArray));
        String string = "asdfgfdsa";
        System.out.print(palindromeRearranging(string));
        }
}
