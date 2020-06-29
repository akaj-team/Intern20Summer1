package com.asiantech.intern20summer1.intro;

import java.util.Arrays;

public class ExploringtheWaters {


    /** 14 : Alternating Sums
     *Several people are standing in a row and need to be divided into two teams.
     *The first person goes into team 1,
     *the second goes into team 2, the third goes into team 1 again, the fourth into team 2, and so on.
     * You are given an array of positive integers - the weights of the people.
     * Return an array of two integers, where the first element is the total weight of team 1,
     * and the second element is the total weight of team 2 after the division is complete.
     * @param a
     * @return   array of two team
     */
    int[] alternatingSums(int[] a) {
        int mSumTeam1 = 0;   // tổng trọng số team lẻ
        int mSumTeam2 = 0;   // tổng trọng số team chẵn
        for(int i = 0;i < a.length; i++){
            if( (i+1) %2 == 1){ // chia 2 dư 1 là lẻ, cộng thêm 1 vì maảng bắt đầu từ 0
                mSumTeam1 += a[i];
            }else{ //còn lại thì chẵn
                mSumTeam2 += a[i];
            }
        }
        int[] out = {mSumTeam1,mSumTeam2};   // xuất trọng số 2 team
        return out;
    }




    /** 15 : Add Border **
     * Given a rectangular matrix of characters, add a border of asterisks(*) to it.
     */

    static String[] addBorder(String[] picture) {
        int mHorizon = picture[0].length() + 2;      // chiều ngang ma trận
        int mVertical = picture.length + 2;          // chiều dọc ma trận
        String[] OutPuts = new String[mVertical];     // Tạo ma trận đầu ra mới
        /**
         để chống nullpointer Exception cần khởi tạo giá trị trước cho các biến, ta tạo luôn một ma trân "*" */
        for (int i = 0; i < mVertical; i++) {
            OutPuts[i] = "*";
            for (int j = 0; j < mHorizon-1; j++) {
                OutPuts[i] += "*";
            }
        }

        /**
         Kiểm tra điều kiện địa chỉ và gán các giá trị mới cho ma trận*/
        for (int i = 0; i < mVertical; i++) {
            if (i != 0 && i != mVertical - 1) {
                OutPuts[i] = "*" + picture[i-1] + "*";
                // nếu địa chỉ khác 0 và mVertical -1 thì chèn ma trận cũ vào ma trận mới
            }
        }
        return OutPuts; // xuất
    }


    /** 16 : Are similar
     * Two arrays are called similar if one can be obtained from another 
     * by swapping at most one pair of elements in one of the arrays.
     * Given two arrays a and b, check whether they are similar.
     * */

    static boolean areSimilar(int[] a, int[] b) {
        if(Arrays.equals(a,b)){  // kiểm tra xem 2 mảng có bằng nhau không
            return true;
        }
        int[] buf = {0,0,0};
        for(int i = 0; i < b.length; i++){
            if(a[i] != b[i]){   // kiểm tra vi trí 2 mảng khác nhau và lưu vào buff
                buf[buf[2]] = i;
                buf[2]++;
            }
            if(buf[2] > 2){ // nếu nhiều hơn 2 vị trí khác nhau thì false
                return false;
            }

        }
        // không nhiều hơn 2 vị trí thì kiểm tra chéo
        if(a[buf[0]] == b[buf[1]] && a[buf[1]] == b[buf[0]]){
            return true;
        } else{
            return false;
        }
    }


    /**
     * You are given an array of integers.
     * On each move you are allowed to increase exactly one of its element by one.
     * Find the minimal number of moves required to obtain a strictly increasing sequence from the input.
     */
    static   int arrayChange(int[] inputArray) {
        int mLeng = inputArray.length;    // đo chiều dài mảng
        int mCount= 0;      // biến count output
        for(int i = 0; i < mLeng -1; i++){  // lặp để kiểm tra từng giá trị
            while(inputArray[i] >= inputArray[i+1]){  // nếu còn nhỏ thì tiếp tục while
                inputArray[i+1]++; // tăng giá trị lên
                mCount++;   // tăng count
            }
        }
        return mCount;
    }
}

