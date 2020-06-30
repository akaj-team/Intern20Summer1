package com.asiantech.intern20summer1.intro;

public class RainsofReason {


    /** 26
     * Given an array of integers, replace all the occurrences of elemToReplace with substitutionElem.
     */
    int[] arrayReplace(int[] inputArray, int elemToReplace, int substitutionElem) {

        for(int i = 0; i < inputArray.length ; i++){
            if(inputArray[i] == elemToReplace){
                inputArray[i] = substitutionElem;
            }
        }
        return inputArray;

    }


    /** 27
     * Check if all digits of the given integer are even.
     */
    boolean evenDigitsOnly(int n) {
        while(n > 0)
        {
            int surplus = n%10;
            n = n/10;
            if(surplus%2 ==1){
                return false;
            }
        }
        return true;
    }



    /** 28 : variableName
     * Correct variable names consist only of English letters,
     * digits and underscores and they can't start with a digit.
     *
     * Check if the given string is a correct variable name.
     */
    boolean variableName(String name) {
                /**Ký tự đầu tiên thỏa mãn
                 * Chỉ được là chữ hoặc ký tự '_'
                 * không thỏa mãn thì false
                 */
        char c0 = name.charAt(0);
        if(Character.isDigit(c0) ||  !(Character.isLetter(c0) || (c0 == '_')) ){
            return false;
        }

                /**Các ký tự sau thỏa mản
                 * Có thể là sô, chữ, dấu '_'
                 * khoông thỏa mãn thì false
                 */
        for(int i = 1; i < name.length() ; i++){
            int c = name.charAt(i);
            if( !(Character.isLetter(c) || Character.isDigit(c) || c == '_' )){
                return false;
            }
        }
        return true;
    }




    /** 28
     * Given a string, your task is to replace each of its characters by the next one in the English alphabet;
     * i.e. replace a with b, replace b with c, etc (z would be replaced by a).
     */

    String alphabeticShift(String inputString) {

        for(int i = 0; i < inputString.length(); i++){
            char c = inputString.charAt(i);
            if(c == 'z'){
                c = 'a';
            } else{
                c +=1;
            }
            inputString = inputString.substring(0,i) + c + inputString.substring(i+1);
        }
        return inputString;
    }


    /**29
     * Given two cells on the standard chess board, determine whether they have the same color or not.
     */

    boolean chessBoardCellColor(String cell1, String cell2) {
        char char1      = cell1.charAt(0); // trong bảng unicode A bắt đầu là số lẻ 61
        char char2      = cell2.charAt(0);
        int  number1    = Integer.parseInt(cell1.charAt(1) +"");
        int  number2    = Integer.parseInt(cell2.charAt(1) +"");
        // 2 ô cùng màu khi ví trí ngang  cộng vị trí dọc cùng chẵn cùng lẻ
        return (char1 + number1)%2 == (char2 + number2)%2;
    }

}
