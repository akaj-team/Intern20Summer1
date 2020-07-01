package intro;

import java.util.Random;

public class RainsofReason {
    public static void main(String[] args) {
        Random rd = new Random();
        /**
         * Given an array of integers, replace all the occurrences of elemToReplace with substitutionElem.
         */
        int[] inputArray = {1,2,1};
        int[] result255=arrayReplace(inputArray,1,3);
        System.out.println("25.Result:");
        for (int item: result255) {
            System.out.print(item+"\t");
        }
        System.out.println();
        /**
         * Check if all digits of the given integer are even.
         */
        int number = 1+rd.nextInt(10000);
        System.out.println("Input number:"+number);
        System.out.println("\n26.Result:"+evenDigitsOnly(number));
        /**
         *Correct variable names consist only of English letters, digits and underscores and they can't start with a digit.
         */
        String name = " var_1__Int";
        System.out.println("27.Result:"+variableName(name));
        /**
         *Given a string, your task is to replace each of its characters by the next one in the English alphabet;
         * i.e. replace a with b, replace b with c, etc (z would be replaced by a).
         */
        System.out.println("28.Result:"+alphabeticShift("crazy"));
        /**
         * Given two cells on the standard chess board, determine whether they have the same color or not.
         */
        System.out.println("29:Result:"+chessBoardCellColor("A1","B2"));

    }
    static int[] arrayReplace(int[] inputArray, int elemToReplace, int substitutionElem) {
        for(int i=0;i<inputArray.length;i++){
            if(inputArray[i]==elemToReplace){
                inputArray[i]=substitutionElem;
            }
        }
        return inputArray;
    }
    static boolean evenDigitsOnly(int n) {
        String stringNumber = String.valueOf(n);
        char[] charNumber = stringNumber.toCharArray();
        for(int i=0;i<charNumber.length;i++){
            if(!(Character.getNumericValue(charNumber[i])%2 ==0 )){
                return false;
            }
        }
        return true;
    }
    static boolean variableName(String name) {
        if (name.matches("[a-zA-Z_][0-9a-zA-Z_]*$")) {
            return true;
        }
        return false;
    }
    static String alphabeticShift(String inputString) {
        String string="";
        for(int i=0;i< inputString.length();i++){
            if(inputString.charAt(i)=='z'){
                string = string+ 'a';
            } else if(inputString.charAt(i)=='Z'){
                string = string + 'A';
            }else{
                string = string +(char) (inputString.charAt(i)+1);
            }

        }
        return string;
    }
    static boolean chessBoardCellColor(String cell1, String cell2) {
        if( (cell1.charAt(0) + cell1.charAt(1)) %2 == 0 &&
                (cell2.charAt(0) + cell2.charAt(1)) %2 == 0 ||
                (cell1.charAt(0) + cell1.charAt(1)) %2 != 0 &&
                        (cell2.charAt(0) + cell2.charAt(1)) %2 != 0){
            return true;
        }
        return false;
    }
}
