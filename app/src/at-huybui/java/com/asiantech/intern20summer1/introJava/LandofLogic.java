package com.asiantech.intern20summer1.introJava;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LandofLogic {

    /** 52 **
     *Define a word as a sequence of consecutive English letters.
     * Find the longest word from the given string.
     */

    String longestWord(String text) {

        String[] arrayString = text.split("[^a-zA-Z]+"); //tách các chữ đi với nhau vào 1 mảng
        int maxLeng = 0;
        int index = 0;
        for(int i = 0;i < arrayString.length; i++){ // duyệt mảng xem từ nào dài nhất
            if(maxLeng < arrayString[i].length()){
                maxLeng = arrayString[i].length();
                index = i;
            }
        }
        return arrayString[index];
    }

    /** 53 **
     * Check Clock
     */

    boolean validTime(String time) {
        return time.matches("(([01][\\d])|([2][0-3]))[:][0-5][\\d]");
    }


    /** 54 **
     *  writing a function that returns the sum of numbers that appear in the given inputString.
     */
    static int sumUpNumbers(String inputString) {
        int mSum = 0;
        for (String number : inputString.split("[^0-9]+")) {
            if(!number.equals("")) {
                mSum += Integer.parseInt(number);
            }
        }
        return mSum;
    }

    /** 55
     *Given a rectangular matrix containing only digits,
     * calculate the number of different 2 × 2 squares in it.
     */
    int differentSquares(int[][] matrix) {
        HashSet<String> set = new HashSet<>();
        for(int i=0; i<matrix.length-1; i++){
            for(int j=0; j<matrix[0].length-1; j++){
                String st = "["+ matrix[i][j] + matrix[i][j+1] + matrix[i+1][j] + matrix[i+1][j+1] + "]";
                set.add(st); // add các phần tử khác nhau vào hashset
            }
        }
        return set.size();
    }

    /** 56
     *Given an integer product, find the smallest positive (i.e. greater than 0)
     * integer the product of whose digits is equal to product.
     * If there is no such integer, return -1 instead.
     */int digitsProduct(int product)
    {
        if (product == 0)
            return 10;
        if (product < 10)
            return product;

        String Output = "";
        for (int i = 9; i > 1; i--){
            for (; product % i == 0; product /= i){
                Output = i + Output;
            }
        }
        return product == 1 ? new Integer(Output) : -1;
    }

    /** 57 **
     *You are given an array of desired filenames in the order of their creation.
     * Since two files cannot have equal names, the one which comes later will have an addition
     * to its name in a form of (k), where k is the smallest positive integer such that the obtained name is not used yet.
     *
     * Return an array of names that will be given to the files.
     */

    String[] fileNaming(String[] names) {
        List<String> listName = new ArrayList<>(); // tạo 1 list lưu trữ các tên đã thay đổi
        for(String st: names){  // duyệt mảng names
            int Count = 0;      // biến đếm lắp vô cuối tên cuối
            String buf = st;    // buf lưu tên tạm thời
            while(true){
                Count++;  // tăng biến đếm
                if(listName.contains(buf)){ // nếu đã tồn tại tên thì thêm count ở đuôi
                    buf = st + "(" + Count + ")";
                }else {   // chưa tồn tại thì add trực tiếp
                    listName.add(buf);
                    break;
                }
            }
        }
        String[] Out = new String[listName.size()];
        listName.toArray(Out);
        return Out;
    }

    /** 58
     *You are taking part in an Escape Room challenge designed specifically for programmers.
     * In your efforts to find a clue, you've found a binary code written on the wall behind a vase,
     * and realized that it must be an encrypted message. After some thought, your first guess is
     * that each consecutive 8 bits of the code stand for the character with the corresponding extended ASCII code.
     * Assuming that your hunch is correct, decode the message.
     */

    String messageFromBinaryCode(String code) {
        String Out = "";
        for(int i = 0; i < code.length();i+=8){ // bắt chuõi 8 ký tự 1 lần
            int x = Integer.parseInt(code.substring(i,i+8),2); // Cắt rồi chuyển từ bin sang Dex
            Out += "" + (char)x; // Dex int ép sang Dex char rồi cộng với Out
        }
        return Out;
    }

    /**59 **
     *
     */

    int[][] spiralNumbers(int n) {
        int[][] array = new int[n][n];
        int mLeft = 0;        // tạo các biến lưu cạnh cho vòng lặp
        int mRight = n - 1;
        int mTop = 0;
        int mDown = n - 1;

        for (int count = 1; count <= n * n; )
        {
            for (int x = mLeft; x <= mRight; x++){ // chạy từ trái qua phải nếu dính tường thì out tăng top
                array[mTop][x] = count++;
            }
            mTop++;

            for (int y = mTop; y <= mDown; y++){  // Các vòng khác tương tự
                array[y][mRight] = count++;
            }
            mRight--;

            for (int x = mRight; x >= mLeft; x--){
                array[mDown][x] = count++;
            }
            mDown--;

            for (int y = mDown; y >= mTop; y--){
                array[y][mLeft] = count++;
            }
            mLeft++;
        }
        return array;
    }

    /** 60 **
     *
     */
    /**
     * ý tưởng tạo 1 Hashset để add các phần từ trong cùng hàng hoặc cùng cột
     * hoặc cùng khối vào nếu add ko thành công thì false
     */

    boolean sudoku(int[][] grid) {
        for (int i = 0; i< 9; i++){  // quét theo chiều ngang add theo hàng
            HashSet<Integer> buf = new HashSet<Integer>();
            for(int j = 0; j< 9; j++){
                if(!buf.add(grid[i][j])){
                    return false;
                }
            }
        }
        for (int i = 0; i< 9; i++){ //// quét theo chiều dọc add theo cột
            HashSet<Integer> buf = new HashSet<Integer>();
            for(int j = 0; j< 9; j++){
                if(!buf.add(grid[j][i])){
                    return false;
                }
            }
        }
        for (int i = 0; i< 9; i+=3){ // quét theo khối
            for(int j = 0; j< 9; j+=3){
                HashSet<Integer> buf = new HashSet<Integer>();
                for(int k = 0; k<3;k++){
                    for (int h = 0; h<3;h++){
                        if(!buf.add(grid[i+k][j+h])){
                            return false;

                        }
                    }
                }
            }
        }
        return true;
    }



}
