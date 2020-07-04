package com.asiantech.intern20summer1.Intro;

public class RainsOfReason {
    /*
    * replace all the occurrences of elemToReplace with substitutionElem
    */
    static int[] arrayReplace(int[] inputArray, int elemToReplace, int substitutionElem) {
        for(int index=0;index<inputArray.length;index++) {
            if(inputArray[index]==elemToReplace)
                inputArray[index]=substitutionElem;
        }
        return inputArray;
    }
    /*
    *Check if all digits of the given integer are even.
    */
    static boolean evenDigitsOnly(int n) {
        while ( n > 0 ){
            if (n%2 != 0 ){
                return false;
            }
            n /= 10 ;
        }
        return true ;
    }
    /*
    * Check  variable name
    */
    static boolean variableName(String name) {
        if (!((name.charAt(0) >= 'a' && name.charAt(0) <= 'z')
                || (name.charAt(0)>= 'A' && name.charAt(0) <= 'Z')
                || name.charAt(0) == '_'))
            return false;
        for (int i = 1; i < name.length(); i++)
        {
            if (!((name.charAt(i) >= 'a' && name.charAt(i) <= 'z')
                    || (name.charAt(i) >= 'A' && name.charAt(i) <= 'Z')
                    || (name.charAt(i) >= '0' && name.charAt(i) <= '9')
                    || name.charAt(i) == '_'))
                return false;
        }
        return true;
    }
    /*
    * eplace each of its characters by the next one
    */
    static String alphabeticShift(String inputString) {
        char[] inputStringArray = inputString.toCharArray();
        for (int i = 0; i < inputStringArray.length; i++) {
            inputStringArray[i]++;
            if (inputStringArray[i] > 'z')
                inputStringArray[i] = 'a';
        }
        return new String(inputStringArray);
    }
    /*
    *Given two cells on the standard chess board, determine whether they have the same color or not.
    */
    static boolean chessBoardCellColor(String cell1, String cell2) {
        if((cell1.charAt(0) + cell1.charAt(1)) % 2 == 0 &&
                (cell2.charAt(0) + cell2.charAt(1)) % 2 == 0 ||
                (cell1.charAt(0) + cell1.charAt(1)) % 2 != 0 &&
                        (cell2.charAt(0) + cell2.charAt(1)) % 2 != 0) {
            return true;

        }
        return false;
    }

    public static void main(){
        int[] inputArray = {1,2,3,4,1,5,1};
        int[] ResultArray = arrayReplace(inputArray , 1,3);
        for ( int i = 0 ; i< ResultArray.length ; i++){
            System.out.print(ResultArray[i]);
        }
        System.out.print("Check EvenDigitsOnly: "+evenDigitsOnly(168));
        String StringToCheck = "variable___to_check";
        System.out.print( "Result Check VariableName"+variableName(StringToCheck));
        String StringToReplace = "abcaz";
        String CheckReplaceChar = alphabeticShift(StringToReplace);
        System.out.print("after Replace" +CheckReplaceChar);
        System.out.print("Same Color: "+chessBoardCellColor("A1","C3"));


    }
}
