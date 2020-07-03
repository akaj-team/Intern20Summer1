package intro;

import java.util.HashSet;
import java.util.Set;

public class DivingDeeper {
    static int[] extractEachKth(int[] inputArray, int k) {
        int length = inputArray.length - (inputArray.length / k);
        int[] array = new int[length];
        int j = 0;
        if (j < length) {
            for (int i = 0; i < inputArray.length; i++) {
                if ((i + 1) % k != 0) {
                    array[j] = inputArray[i];
                    j++;
                }
            }
        }
        return array;
    }
    static char firstDigit(String inputString) {
        char result = ' ';
        char[] ch = inputString.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (Character.isDigit(ch[i])) {
                result = ch[i];
                break;
            }
        }
        return result;

    }
    static int differentSymbolsNaive(String s) {
        Set<Character> array = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            array.add(s.charAt(i));
        }
        return array.size();
    }
    static int arrayMaxConsecutiveSum(int[] inputArray, int k) {
        int max =0 ;
        int sum = 0;
        int count = 1;
        for(int i = 0;i <= inputArray.length - k;i++){
            sum = inputArray[i];
            while(count<k){
                sum = sum + inputArray[i+count];
                count++;
            }
            count = 1;
            if(sum > max){
                max = sum;
            }
        }
        return max;
    }
    public static void main(String[] args) {
        /**
         * Given array of integers, remove each k^th element from it.
         */
        int[] inputArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] resultKth = extractEachKth(inputArray,3);
        System.out.println("34.Result:");
        for (int item: resultKth) {
            System.out.print(item+"\t");
        }
        /**
         * Find the leftmost digit that occurs in a given string.
         */
        System.out.println();
        System.out.println("35.Result:"+firstDigit("1234ssssssss"));
        /**
         * Given a string, find the number of different characters in it.
         */
        System.out.println("36.Result:"+differentSymbolsNaive("abcdeaab"));
        /**
         * Given array of integers, find the maximal possible sum of some of its k consecutive elements.
         */
        int[] inputArrayMaxConsecutive = {2, 3, 5, 1, 6};
        System.out.println("37.Result:"+arrayMaxConsecutiveSum(inputArrayMaxConsecutive,2));
    }
}
