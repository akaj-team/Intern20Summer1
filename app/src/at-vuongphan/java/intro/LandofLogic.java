package intro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

public class LandofLogic {
    public static void main(String[] args) {
        /**
         * Define a word as a sequence of consecutive English letters. Find the longest word from the given string.
         */
        System.out.println("52.Result:"+longestWord("ready[[,student"));
        /**
         * Check if the given string is a correct time representation of the 24-hour clock.
         */
        System.out.println("53.Result:"+validTime("24:33"));
        /**
         * CodeMaster has just returned from shopping. He scanned the check of the items he bought and gave the resulting string to Ratiorg to figure out the total number of purchased items. Since Ratiorg is a bot he is definitely going to automate it, so he needs a program that sums up all the numbers which appear in the given input.
         *
         * Help Ratiorg by writing a function that returns the sum of numbers that appear in the given inputString.
         */
        System.out.println("54.Result:"+sumUpNumbers("2 apples, 12 oranges"));
        /**
         * Given a rectangular matrix containing only digits, calculate the number of different 2 × 2 squares in it.
         */
        int[][] matrix = {{1, 2, 1},
        {2, 2, 2},
        {2, 2, 2},
        {1, 2, 3},
        {2, 2, 1}};
        System.out.println("55.Result:"+differentSquares(matrix));

        /**
         *Given an integer product, find the smallest positive (i.e. greater than 0) integer the product of whose digits
         * is equal to product. If there is no such integer, return -1 instead.
         */
        System.out.println("56.Result:"+digitsProduct(91));
        /**
         *You are given an array of desired filenames in the order of their creation. Since two files cannot have equal names, the one which comes later will have an addition to its name in a form of (k),
         * where k is the smallest positive integer such that the obtained name is not used yet.
         */
        String[] input ={"doc","doc","vuong","doc(1)"};
        String[] result= fileNaming(input);
        System.out.println("57.Result.");
        for(int i=0;i<result.length;i++){
            System.out.print(result[i]+"\t");
        }
        /**
         * You are taking part in an Escape Room challenge designed specifically for programmers. In your efforts to find a clue, you've found a binary code written on the wall behind a vase, and realized that it must be an encrypted message. After some thought, your first guess
         * is that each consecutive 8 bits of the code stand for the character with the corresponding extended ASCII code.
         */
        System.out.println("58.Result:"+messageFromBinaryCode("010010000110010101101100011011000110111100100001"));
        /**
         *Construct a square matrix with a size N × N containing
         *  integers from 1 to N * N in a spiral order, starting from top-left and in clockwise direction.
         */
        int[][] result59 = spiralNumbers(10);
        System.out.println("59.Result:");
        for(int i=0; i<result59.length;i++){
            for(int j=0;j<result59[0].length;j++){
                System.out.print(result59[i][j]+"\t");
            }
            System.out.println();
        }
        /**
         * Sudoku is a number-placement puzzle. The objective is to fill a 9 × 9 grid with digits so that each column, each row, and each of the nine 3 × 3 sub-grids that compose the grid contains all of the digits from 1 to 9.
         *
         * This algorithm should check if the given grid of numbers represents a correct solution to Sudoku.
         */
    }
    static boolean sudoku(int[][] grid) {
        int product = 1;
        int sum = 0;
        for(int i = 0;i < grid.length; i++){
            sum = 0;
            for(int j=0;j<grid[0].length;j++){
                sum += grid[i][j];
                product = product * grid[i][j];
            }
            if(sum != 45 && product!=362880){

                return false;
            }
        }
        for(int i=0;i<grid[0].length;i++){
            sum=0;
            for(int j=0;j<grid.length;j++){
                sum += grid[j][i];
                product = product * grid[j][i];
            }
            if(sum != 45 && product!= 362880){

                return false;
            }
        }
        for(int i=0;i<grid.length;i+=3){
            sum = 0;
            for(int j=i;j<i+3;j++){
                sum+=grid[i][j]+grid[i+1][j]+grid[i+2][j];
            }
            if(product!=45){
                return false;
            }
        }
        return true;
    }

    static int[][] spiralNumbers(int n) {
        int[][] arrResult = new int[n][n];
        int row1=0,row2 = n-1,col1 = 0, col2 = n-1;
        int value = 0;
        while(value < n * n){
            for(int i = col1 ; i <= col2; i++){
                arrResult[row1][i] = ++value;
            }
            row1++;
            for(int i=row1; i<=row2;i++){

                arrResult[i][row2]=++value;
            }
            col2--;
            for(int i=col2;i>=col1;i--){
                arrResult[row2][i]=++value;
            }
            row2--;
            for(int i = col2;i>=row1; i--){
                arrResult[i][col1]=++value;
            }
            col1++;
        }
        return arrResult;
    }

    static String messageFromBinaryCode(String code) {
        StringBuilder stringBuilder = new StringBuilder();
        int g ;
        for(int i=0;i<code.length();i++){
            if(i % 8 ==7){
                g= Integer.valueOf(code.substring(i-7,i+1),2);
                stringBuilder.append(Character.toChars(g));
            }
        }
        return stringBuilder.toString();
    }
    static String[] fileNaming(String[] names) {
        List<String> result = new ArrayList<>();
        for(int i = 0; i < names.length; i++){
            int count = 0;
            String s = names[i];
            while(result.contains(s)){
                count++;
                s = names[i] + "(" + String.valueOf(count) + ")";
            }
            result.add(s);
        }

        return result.toArray(new String[0]);
    }
    static  int digitsProduct(int product) {
        if(product==0){
            return 10;
        }

        String b="";
        for(int i=9;i>1;i--){
            while(product % i ==0){
                product = product / i;
                b = i + b;
            }
        }
        if(product >1){
            return -1;
        }
        return Integer.parseInt(b);
    }

    static int differentSquares(int[][] matrix) {
        HashSet<String> set = new HashSet<>();
        int row = matrix.length;
        int col = matrix[0].length;
        if( row == 1 || col == 1){
            return 0;
        }
        for(int i=0;i<row-1;i++){
            for(int j=0; j< col-1;j++ ){
                StringBuilder sBuilder = new StringBuilder();
                sBuilder.append(matrix[i][j]).append(matrix[i][j+1])
                        .append(matrix[i+1][j]).append(matrix[i+1][j+1]);
                set.add(sBuilder.toString());
            }
        }
        return set.size();
    }
    static int sumUpNumbers(String inputString) {
        String result = inputString.replaceAll("[^0-9 ]"," ");
        String[] numberString = result.split(" ");
        int sum = 0;
        for(int i=0; i< numberString.length;i++){
            if(numberString[i].isEmpty()){
                continue;
            }
            sum = sum + Integer.parseInt(numberString[i]);

        }
        return sum;

    }
    static boolean validTime(String time) {

        //    String s = time.replaceAll("[^0-9]"," ");
        //    String [] re = s.split(" ");
        //      if (re.length==2)
        //     {
        //         int hour = Integer.parseInt(re[0]);
        //         int mins = Integer.parseInt(re[1]);
        //         if (hour >= 0 && hour < 24 && mins >= 0 && mins < 60)
        //             return true;
        //     }
        //     return false;
        return Pattern.matches(("([01]?[0-9]|2[0-3]):[0-5][0-9]"),time);

    }
    static String longestWord(String text) {
        String input = text.replaceAll("[^a-zA-Z]", " ");
        String[] arrResult = input.split(" ");
        int maxLength = 0;
        String result = "";
        for(int i=0;i<arrResult.length;i++){
            if(arrResult[i].length()>maxLength){
                maxLength = arrResult[i].length();
                result = arrResult[i];
            }
        }
        return result;
    }
}
