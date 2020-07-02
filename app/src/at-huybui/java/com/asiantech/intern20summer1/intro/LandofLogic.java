package com.asiantech.intern20summer1.intro;

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




}
