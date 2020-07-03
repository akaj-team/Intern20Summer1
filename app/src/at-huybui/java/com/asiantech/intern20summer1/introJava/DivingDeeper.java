package com.asiantech.intern20summer1.introJava;

import java.util.ArrayList;
import java.util.List;

public class DivingDeeper {

    /** 34 **
     * Given array of integers, remove each kth element from it.
     */

    Integer[] extractEachKth(int[] inputArray, int k) {
        List<Integer> Output = new ArrayList<>();  // Tạo mảng đệm đầu ra
        int RemoveAt = k;    // vị tri xóa giá trị,
        for (int i = 0; i < inputArray.length; i++) {
            if(i+1 == RemoveAt) {   // quét mảng nếu vị trí xóa trùng với vị tri quét thì continue
                RemoveAt += k;
                continue;
            }
            Output.add(inputArray[i]); // nếu không thì add phần tử mới cho mảng Output
        }
        Integer[] outputArray = Output.toArray(new Integer[Output.size()]); // ép lại kiểu rồi xuất ra
        return outputArray;
    }


    /** 35 **
     * Find the leftmost digit that occurs in a given string.
     */
    static char firstDigit(String inputString) {
        char[] arrayBuf = inputString.toCharArray();  // chuyển chuỗi thành mảng ký tự
        for (char c: arrayBuf){// quét chuỗi kiểm tra chữ số đầu tiên và trả về nó
            if(Character.isDigit(c)){
                return c;
            }
        }
        return '0';
    }


    /** 36 **
     * Given a string, find the number of different characters in it.
     */

    int differentSymbolsNaive(String s){
        char[] charBuf  = s.toCharArray();  // chuyển chuỗi về mảng
        List<Character> listBuf = new ArrayList<>(); // tạo list lưu số ký tự khác nhau
        listBuf.add(charBuf[0]);    // add ký tự đầu tiên
        for(char c: charBuf){   // duyệt chuỗi
            if(!listBuf.contains(c)){   // nếu phần tự duyệt ko giống với bất cứ phần tử nào trong list thì add vô list
                listBuf.add(c);
            }
        }
        return listBuf.size(); // xuất size l
    }


    /** 37 **
     * Given array of integers,
     * find the maximal possible sum of some of its k consecutive elements.
     */

    int arrayMaxConsecutiveSum(int[] inputArray, int k) {
        int mSum = 0;    //tạo biến Sum lưu max Sum
        for(int i = 0; i <= inputArray.length-k; i++){ // quét mảng
            int newSum = 0;
            for(int j = 0; j < k; j++) {  // tính tổng
                newSum += inputArray[i + j];
            }
            if(newSum > mSum){  // so sánh tổng cũ với tổng mới
                mSum = newSum;
            }
        }
        return mSum;
    }
}
