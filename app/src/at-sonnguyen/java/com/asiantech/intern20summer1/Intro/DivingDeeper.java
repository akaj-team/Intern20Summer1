package com.asiantech.intern20summer1.Intro;

public class DivingDeeper {
    /*
    * Given array of integers, remove each kth element from it.
    */
    static int[] extractEachKth(int[] inputArray, int k) {
        int index = 0;
        int n = inputArray.length;
        int[] a = new int[n - n / k ];
        for (int i = 0; i < n ;i++){
            if ( (i + 1) % k != 0){
                a[index++] = inputArray[i];
            }
        }
        return a;
    }
    /*
    *Find the leftmost digit that occurs in a given string.
    */
    static char firstDigit(String inputString) {
        for ( int index = 0 ; index < inputString.length() ; index++){
            if ( inputString.charAt(index) >= '0' && inputString.charAt(index) <= '9' ){
                return inputString.charAt(index);
            }
        }
        return inputString.charAt(0) ;
    }
    /*
    * Given a string, find the number of different characters in it.
    */
    static int differentSymbolsNaive(String s) {
        List<Character> DifferentCharacrers = new ArrayList<>();
        for ( int index = 0 ; index < s.length(); index++){
            if (!DifferentCharacrers.contains(s.charAt(index))){
                DifferentCharacrers.add(s.charAt(index));
            }
        }
        return DifferentCharacrers.size();
    }
    /*
    *Given array of integers, find the maximal possible sum of some of its k consecutive elements
    */
    static int arrayMaxConsecutiveSum(int[] inputArray, int k) {
        int Result = 0 ;
        int MaxTemp = 0;
        for (int index = 0 ; index < k - 1 ; index++){
            MaxTemp += inputArray[index];
        }
        for (int index = k - 1 ; index < inputArray.length ; index++){
            MaxTemp += inputArray[index];
            if(MaxTemp > Result){
                Result = MaxTemp ;
            }
            MaxTemp -= inputArray[index - k + 1];
        }
        return Result;
    }

    public static void main(){
        int[] inputArray = {1,5,3,6,8,9};
        int[] ResultArray = extractEachKth(inputArray,2);
        String inputString = "axcf35hhh";
        char FirtsDigit = firstDigit(inputString);
        int DefferenceChar = differentSymbolsNaive(inputString);
        int MaxSum = arrayMaxConsecutiveSum(inputArray,3);
    }
}
