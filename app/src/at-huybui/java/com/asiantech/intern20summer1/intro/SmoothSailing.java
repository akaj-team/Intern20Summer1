package com.asiantech.intern20summer1.intro;



public class SmoothSailing {

    /** 9 All Longest Strings **
     *Given an array of strings,
     *return another array containing all of its longest strings.
     */
    String[] allLongestStrings(String[] inputArray) {

        /* lay gia trị String dai nhat*/
        int lengMax = inputArray[0].length();   // biến lưu chiều dài lớn nhất
        int count = 0;                          //bien luu so gia tri trong input co lengMax
        for(String st: inputArray){
            if(st.length() > lengMax){
                lengMax = st.length();          // lengMax new
                count = 0;                      // neu co lengMax new nạp lại count =0
            }
            if(st.length() == lengMax){
                count++;                        // dem so gia tri co lengMax
            }
        }

        //Nap gia tri co lengMax cho output
        int j=0;
        String[] mReturn = new String[count];
        for(String st: inputArray){
            if(st.length() == lengMax){
                mReturn[j] = st;
                j++;
            }
        }
        return mReturn;
    }

    /** 10 Common Character Count
     * Given two strings, find the number of common characters between them.
     */
    static int commonCharacterCount(String s1, String s2) {
        int count =0;  // bien dem so ky tu trung
        for (int i = 0; i < s1.length(); i++) {
            int index = s2.indexOf( s1.charAt(i) ); //kiem tra index ký tự trùng
            if(index >= 0 ){
                count++; // tăng biến đếm nếu phát hiện ký tự trùng
                s2 = s2.substring(0,index) + s2.substring(index + 1,s2.length()); //cắt tạo chuỗi mới để so sánh lần sau
                System.out.println("new s2:" + s2);
            }
        }
        return count;
    }

    /** 11 Is Lucky
     *Ticket numbers usually consist of an even number of digits.
     * A ticket number is considered lucky if the sum of
     * the first half of the digits is equal to the sum of the second half.
     * Given a ticket number n, determine if it's lucky or not.
     */

    boolean isLucky(int n) {
        String st = "" + n;
        int leng = st.length() / 2;   // cắt đôi độ dài chuỗi
        int Left = (int) (n / (Math.pow(10, leng)));  // cắt lấy phần bên trái của số
        int Right = (int) (n % (Math.pow(10, leng))); // cắt lấy phần bên phải của số
        int sumL = 0;
        int sumR = 0;
        for (int i = 0; i < leng; i++) {
            sumL += (int) (Left / (Math.pow(10, i)) % 10); // lắp tính tống của số bên trái
            sumR += (int) (Right / (Math.pow(10, i)) % 10);// lặp tính tổng số bên phải
        }
        return sumL == sumR; // compare
    }

    /** 12 Sort by Height
     * Some people are standing in a row in a park.
     * There are trees between them which cannot be moved.
     * Your task is to rearrange
     * the people by their heights in a non-descending order without moving the trees.
     * People can be very tall!
     */

    int[] sortByHeight(int[] a) {
        //Đếm số cây trong mảng a
        int mCountTree = 0;
        for(int i : a){
            if(i == -1){
                mCountTree++;
            }
        }
        System.out.println(mCountTree);

        // đưa người vào mảng người mới để lưu t
        int mPeopleBuf[] = new int[a.length - mCountTree];
        for(int i = 0, j = 0; i < a.length;i++){
            if(a[i] != -1){
                System.out.print(a[i] + " ");
                mPeopleBuf[j] = a[i];
                j++;
            }
        }
        System.out.println("");

        //Nổi bọt mảng người
        for (int i = 0; i < mPeopleBuf.length -1; i++){
            for (int j = 0; j < mPeopleBuf.length -i-1; j++){
                if (mPeopleBuf[j] > mPeopleBuf[j+1]){
                    int buf = mPeopleBuf[j];
                    mPeopleBuf[j]   = mPeopleBuf[j+1];
                    mPeopleBuf[j+1] = buf;

                }
            }
        }

        //Gán lại mảng người đã nổi bot cho mảng
        for(int i = 0,j = 0; i < a.length; i++) {
            if (a[i] != -1) {
                a[i] = mPeopleBuf[j];
                j++;
            }
        }

        //in mảng a đã sắp xếp lại
        for(int i: a) {
            System.out.print(i + " ");
        }
        return a;
    }





}
