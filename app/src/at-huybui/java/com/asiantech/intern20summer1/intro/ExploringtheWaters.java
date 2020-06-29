package com.asiantech.intern20summer1.intro;

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

}
