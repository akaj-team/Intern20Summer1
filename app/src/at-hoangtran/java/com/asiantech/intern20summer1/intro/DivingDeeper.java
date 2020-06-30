package com.asiantech.intern20summer1.intro;

import java.util.ArrayList;

public class DivingDeeper {
    public static void main(String[] args) {
        int[] intArray = {2,5,4,8,6,3,4,8};
        int k = 2;
        String s = "adf5asd";
        System.out.println(extractEachKth(intArray, k));
        System.out.println(firstDigit(s));
        System.out.println(differentSymbolsNaive(s));
        System.out.println(arrayMaxConsecutiveSum(intArray,k));
    }
    static int[] extractEachKth(int[] inputArray, int k) {
//        Given array of integers, remove each kth element from it.
        int l = inputArray.length;
        ArrayList<Integer> a = new ArrayList<>();
        for(int i =0;i<l;i++){
            if((i+1)%k!=0){
                a.add(inputArray[i]);
            }
        }
        Object[] bo = new Object[a.size()];
        int[] bi = new int[a.size()];
        bo=a.toArray();
        for(int i = 0;i <bi.length;i++){
            bi[i] = (Integer)bo[i];
        }
        return bi;
    }
    static char firstDigit(String inputString) {
//        Find the leftmost digit that occurs in a given string.
        char c = ' ';
        int l = inputString.length();
        for(int i = 0;i<l;i++){
            if(inputString.charAt(i)>=48&&inputString.charAt(i)<=57){
                c =inputString.charAt(i);
                break;
            }
        }
        return c;
    }
    static int differentSymbolsNaive(String s) {
//        Given a string, find the number of different characters in it.
        int l = s.length();
        ArrayList<Character>c = new ArrayList<Character>();
        for(int i = 0;i<l;i++){
            int count = 0;
            for(int j =0;j<c.size();j++){
                if(s.charAt(i)==c.get(j)){
                    count++;
                }
            }
            if(count<1){
                c.add(s.charAt(i));
            }
        }
        return c.size();
    }
    static int arrayMaxConsecutiveSum(int[] inputArray, int k) {
//        Given array of integers, find the maximal possible sum of some of its k consecutive elements.
        int l = inputArray.length;
        int max = 0;
        for(int i =0;i<=l-k;i++){
            int sum = 0;
            for(int j = 0;j<k;j++){
                sum += inputArray[i+j];
            }
            if(sum>max){
                max = sum;
            }
        }
        return max;
    }

}
