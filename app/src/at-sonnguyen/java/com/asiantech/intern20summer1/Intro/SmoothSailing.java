package com.asiantech.intern20summer1.Intro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmoothSailing {
    /*
    *return another array containing all of its longest strings.
    */

    static String[] allLongestStrings(String[] inputArray) {
        int MaxLength = inputArray[0].length() ;
        List<String> ListResult = new ArrayList<>();
        for (int index = 0; index <inputArray.length; index++){
            if (inputArray[index].length() > MaxLength){
                MaxLength = inputArray[index].length();
            }
        }
        for (int index = 0 ; index < inputArray.length ; index++ ){
            if (inputArray[index].length() == MaxLength) {
                ListResult.add(inputArray[index].toString());
            }
        }
        String[] ArrayResult = new String[ListResult.size()] ;
        ListResult.toArray(ArrayResult);
        return ArrayResult;
    }
    /*
    *find the number of common characters between 2 Arrays.
    */
    static int commonCharacterCount(String s1, String s2) {
        List<Integer> array = new ArrayList<Integer>();
        int Count = 0 ;
        for (int index1 =0 ; index1 < s1.length() ; index1++ ){
            for (int index2 =0 ; index2 < s2.length();index2++){
                if (!array.contains(index2) && s1.charAt(index1) == s2.charAt(index2) ){
                    array.add(index2);
                    Count+=1;
                    break;
                }
            }
        }
        return Count;
    }
    /*
    * Given a ticket number n, determine if it's lucky or not.
    */
    static boolean isLucky(int n) {
        List<Integer> ArrayListNumber = new ArrayList<Integer>();
        while (n > 0){
            System.out.println(n);
            ArrayListNumber.add(n%10);
            n = n / 10;

        }
        Integer[] ArrayNumber = new Integer[ArrayListNumber.size()];
        ArrayNumber =  ArrayListNumber.toArray(ArrayNumber);
        for (int i =0 ; i < ArrayNumber.length ; i++ ){
            System.out.println(ArrayNumber[i]);
        }
        int Sum1 = 0;
        int Sum2 = 0;
        for (int index =0 ; index < ArrayListNumber.size()/2;index++){
            Sum1 += ArrayNumber[index];
            System.out.println(Sum1);
        }
        for (int index = ArrayListNumber.size()/2  ; index < ArrayListNumber.size();index++){
            Sum2 += ArrayNumber[index];
            System.out.println(Sum2);
        }
        if (Sum1 == Sum2){
            return true;
        } else {
            return false;
        }
    }
    /*
    * rearrange the people by their heights in a non-descending order without moving the trees
    */
    static int[] sortByHeight(int[] a) {
        int[] temp = a.clone();
        Arrays.sort(a);
        List<Integer> list = new ArrayList<Integer>();
        for(int n: a){
            if(n != -1)
                list.add(n);
        }
        for(int i = 0, j=0; i<temp.length; i++){

            if(temp[i] != -1){
                temp[i] = list.get(j);
                j++;
            }
        }
        return temp;

    }
    /*
    * reverses characters in (possibly nested) parentheses in the input string
    */
    static String reverseInParentheses(String inputString) {
        String tmpCh = new String("");
        String tmpChRe = new String("");
        String tmp = new String("");
        int l = inputString.length();
        int n = 0;
        int j =0;
        for(int i = 0;i<l;i++){
            if(inputString.charAt(i)=='(')
                n++;
        }
        int T[] = new int[n];
        for(int i=0;i<l;i++){
            if(inputString.charAt(i)=='('){
                T[j]=i;
                j++;
            }
        }
        j=0;
        while(n>0){
            j = T[n-1] + 1;
            while(inputString.charAt(j)!=')'){
                tmpCh = tmpCh + inputString.charAt(j);
                j++;
            }
            for(int q=tmpCh.length()-1;q>=0;q--)
                tmpChRe = tmpChRe + tmpCh.charAt(q);
            tmp = inputString.substring(0,T[n-1]) + tmpChRe + inputString.substring(T[n-1]+tmpChRe.length()+2);
            inputString = tmp;
            n--;
            tmp = "";
            tmpCh = "";
            tmpChRe = "";
        }
        return inputString ;
    }
    public static void main(){
        //String [] longestString = allLongestStrings(['aaa','bbb','ccc','a', 'd','ccc']);
        int commonCharacter = commonCharacterCount("aabc", "abcd");
        boolean lucky = isLucky(156);
        int [] inputArray = {15,16,16,-1,-1,15};
        int[] Sort = sortByHeight(inputArray);
        String RevertString = reverseInParentheses("a(son)j");
    }

}
