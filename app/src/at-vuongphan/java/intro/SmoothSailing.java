package intro;
import java.util.ArrayList;
import java.util.Scanner;

public class SmoothSailing {
    static Scanner input =new Scanner(System.in);
    public static void main(String[] args) {
        /**
         * Given an array of strings, return another array containing all of its longest strings.
         */
        String[] inputArray={"aba", "aa", "ad", "vcd", "aba"};
        String[] result=allLongestStrings(inputArray);
        for (String item : result){
            System.out.print("\t"+item);
        }
        /**
         *Given two strings, find the number of common characters between them.
         */
        String inputArray1 = "abcd";
        String inputArray2 = "aabcaa";
        System.out.print("Result commonCharacter:"+commonCharacterCount(inputArray1,inputArray2));
        /**
         * Ticket numbers usually consist of an even number of digits. A ticket number is considered
         * lucky if the sum of the first half of the digits is equal to the sum of the second half.
         */

        System.out.print("Input Number:");
        int number = Integer.parseInt(input.nextLine());
        System.out.println("ISLUCKY:"+isLucky(number));
        /**
         * Some people are standing in a row in a park. There are trees between them which cannot be moved. Your task is to
         * rearrange the people by their heights in a non-descending order without moving the trees. People can be very tall!
         */
        int[] inputArrNumber ={-1, 150, 190, 170, -1, -1, 160, 180};
        int[] resultSort = sortByHeight(inputArrNumber);
        //resultSort.forEach(item -> System.out.println(resultSort+"\t"));
        for(int item : resultSort){
            System.out.print(item+"\t");
        }
        /**
         * Write a function that reverses characters in (possibly nested) parentheses in the input string.
         */
        String inputString="(bar)";
        System.out.print("Result ReserveString:"+reverseInParentheses(inputString)  );
    }
    static String[] allLongestStrings(String[] inputArray) {
        int maxLength = 1;
        ArrayList<String> answer = new ArrayList<String>();
        for (String value : inputArray) {
            if (value.length() > maxLength) {
                maxLength = value.length();
            }
        }
        for (String s : inputArray) {
            if (s.length() == maxLength) {
                answer.add(s);
            }
        }
        return answer.toArray(new String[0]);
    }
    static int commonCharacterCount(String s1, String s2){
        boolean[] isCommon = new boolean[s2.length()];
        int count=0;
        for(int i=0;i<s1.length();i++){
            for(int j=0;j<s2.length();j++){
                if(s1.charAt(i)==s2.charAt(j) && isCommon[j]==false){
                    isCommon[j]=true;
                    count++;
                    break;
                }
            }
        }
        return count;
    }
    static boolean isLucky(int n) {
        int sum1=0;
        int sum2=0;
        String lucky = String.valueOf(n);
        char[] characterLucky= lucky.toCharArray();
        for(int i=0;i<characterLucky.length/2;i++){
            sum1+=Character.getNumericValue(characterLucky[i]);
        }
        for(int j=characterLucky.length-1;j>=characterLucky.length/2;j--){
            sum2+=Character.getNumericValue(characterLucky[j]);
        }
        if(sum1 == sum2){
            return true;
        }
        return false;
    }
    static int[] sortByHeight(int[] inputArray) {
        for(int i=0;i<inputArray.length;i++){
            for(int j=0;j<inputArray.length;j++)
                if(inputArray[i]==-1){
                    continue;
                }else{
                    if(inputArray[j]==-1){
                        continue;
                    }else{
                        if(inputArray[i]<inputArray[j]){
                            int tmp=inputArray[i];
                            inputArray[i]=inputArray[j];
                            inputArray[j]=tmp;
                        }
                    }
                }
        }
        return inputArray;
    }
    static String reverseInParentheses(String inputString) {
        String tmpCh = new String("");
        String tmpChRe = new String("");
        String tmp = new String("");
        int l = inputString.length();
        int n = 0;
        int j = 0;
        for (int i = 0; i < l; i++) {
            if (inputString.charAt(i) == '(')
                n++;
        }
        int T[] = new int[n];
        for (int i = 0; i < l; i++) {
            if (inputString.charAt(i) == '(') {
                T[j] = i;
                j++;
            }
        }
        j = 0;
        while (n > 0) {
            j = T[n - 1] + 1;
            while (inputString.charAt(j) != ')') {
                tmpCh = tmpCh + inputString.charAt(j);
                j++;
            }
            for (int q = tmpCh.length() - 1; q >= 0; q--)
                tmpChRe = tmpChRe + tmpCh.charAt(q);
            tmp = inputString.substring(0, T[n - 1]) + tmpChRe + inputString.substring(T[n - 1] + tmpChRe.length() + 2);
            inputString = tmp;
            n--;
            tmp = "";
            tmpCh = "";
            tmpChRe = "";
        }
        return inputString;
    }
}
