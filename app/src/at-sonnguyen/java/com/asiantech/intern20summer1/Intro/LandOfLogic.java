package com.asiantech.intern20summer1.Intro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LandOfLogic {
    /*
    * Define a word as a sequence of consecutive English letters.
    * Find the longest word from the given string.
    */
    static String longestWord(String text) {
        int i = 0;
        int maxLength = 0;
        String stringSplit = text.replaceAll("[^a-zA-Z]", " ");
        String [] arrayOfText = stringSplit.split(" ");
        for (int index =0 ; index < arrayOfText.length;index++){
            if (arrayOfText[index].length() > maxLength ){
                maxLength = arrayOfText[index].length();
                i = index ;
            }
        }
        System.out.println(stringSplit);
        return arrayOfText[i] ;
    }
    /*
    *Check if the given string is a correct time representation of the 24-hour clock.
    */
    static boolean validTime(String time) {
        String stringOfTime = time.replaceAll("[^0-9]", " ");
        String [] arrayOfTime = stringOfTime.split(" ");
        if ( arrayOfTime.length!=2){
            return false ;
        }else if ( Integer.parseInt(arrayOfTime[0].toString()) >23 || Integer.parseInt(arrayOfTime[0].toString()) < 0 ){
            return false ;
        } else if (Integer.parseInt(arrayOfTime[1].toString()) >59||Integer.parseInt(arrayOfTime[0].toString()) < 0){
            return false;
        }
        return true ;
    }
    /*
    * returns the sum of numbers that appear in the given inputString
    */
    static int sumUpNumbers(String inputString) {
        int sum = 0;
        String stringOfNumber = inputString.replaceAll("[^0-9]", " ");
        String[] arrayOfNumber = stringOfNumber.split(" ");

        for (int index = 0 ; index < arrayOfNumber.length ; index++ ){
            if(arrayOfNumber[index].isEmpty()){
                continue;
            }
            sum += Integer.parseInt(arrayOfNumber[index]);
        }
        return sum;
    }
    /*
    *Given a rectangular matrix containing only digits,
    * calculate the number of different 2 × 2 squares in it.
    */
    static int differentSquares(int[][] matrix) {
        HashSet<String> hs = new HashSet<String>();
        if(matrix.length == 1 || matrix[0].length == 1) return 0;
        for(int i = 1; i<matrix.length;i++){
            for(int j = 1;j<matrix[0].length;j++){
                hs.add( matrix[i-1][j-1]+","+matrix[i-1][j]+","+matrix[i][j-1]+","+matrix[i][j]);
            }
        }
        return hs.size();
    }
    /*
    * Given an integer product, find the smallest positive integer the product
    * of whose digits is equal to product. If there is no such integer, return -1 instead.
    */
    static int digitsProduct(int product) {
        if (product == 0 ){
            return 10;
        } else if ( product ==1 ){
            return 1;
        }
        String result = "";
        for(int num = 9 ; num>1 ; num-- ){
            while (product % num == 0){
                product /= num;
                result = num + result ;
            }

        }
        if (product > 1){
            return -1;
        }
        return Integer.parseInt(result);
    }
    /*
    * Return an array of names that will be given to the files.
    */
    static String[] fileNaming(String[] names) {
        List<String> namesList = new ArrayList<>();
        for (int index = 0 ; index < names.length ; index++){
            int count = 0;
            String nameInNames = names[index];
            while(namesList.contains(nameInNames)){
                count++;
                nameInNames = names[index] + "(" + count + ")";
            }
            namesList.add(nameInNames);
        }
        String[] arrayNames = new String [namesList.size()];
        arrayNames = namesList.toArray(arrayNames) ;
        return arrayNames ;
    }
    /*
    * decode the message.
    */
    static String messageFromBinaryCode(String code) {
        String resultString = "";
        for(int index = 0; index < code.length(); index+=8) {
            String temp = code.substring(index, index+8);
            int num = Integer.parseInt(temp,2);
            char letter = (char) num;
            resultString = resultString+letter;
        }
        return resultString;
    }
    /*
    * Construct a square matrix with a size N × N containing integers
    * from 1 to N * N in a spiral order, starting from top-left and in clockwise direction
    */
    static int[][] spiralNumbers(int n) {
        int [][] matrix = new int [n][n];
        int row = 0;
        int collum = n-1;
        int value = 0;
        if(n%2 == 1) matrix[n/2][n/2]=n*n;
        while (row<=collum){

            for(int j = row; j <= collum - 1; j++)
                if (value >= n*n) break;
                else matrix[row][j] = ++value;

            for(int i = row; i <= collum -1; i++){
                if (value >= n*n){
                    break;
                }
                else matrix[i][collum] = ++value;
            }


            for(int j = collum; j >= row + 1; j--)
                if (value >= n*n) break;
                else matrix[collum][j] = ++value;

            for(int i = collum; i >= row + 1; i--)
                if (value >= n*n) break;
                else matrix[i][row] = ++value;
            row++;
            collum--;
        }
        return matrix;
    }

    public static void main(){
        String text = "Ready, steady, go!";
        System.out.print(text);
        String time = "13:58";
        System.out.print(validTime(time));
        String inputString = "2 apples, 12 oranges";
        int[][] matrix ={{1,2,1},{2,2,2},{2,2,2},{1,2,3},{2,2,1}};
        System.out.print(differentSquares(matrix));
        System.out.print(digitsProduct(165));
        String[] names = {"doc","doc", "image", "doc(1)", "doc"};
        System.out.print(fileNaming(names));
        String codeBinary = "010010000110010101101100011011000110111100100001";
        System.out.print(messageFromBinaryCode(codeBinary));
        System.out.print(spiralNumbers(9));
    }
}
