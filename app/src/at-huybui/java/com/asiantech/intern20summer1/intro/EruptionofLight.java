package com.asiantech.intern20summer1.intro;

public class EruptionofLight {

    /**43  ****
     * A string is said to be beautiful if each letter in the string appears
     * at most as many times as the previous letter in the alphabet within the string;
     * ie: b occurs no more times than a; c occurs no more times than b; etc.
     *
     * Given a string, check whether it is beautiful.
     */

    static boolean isBeautifulString(String inputString) {
        int mCount = 100;
        for(char c = 'a'; c <= 'z' ; c++){  // chạy vòng lặp từ a - z
            int newCount = 0;
            while(inputString.indexOf(c) !=-1){ // nếu phát hiện ký tự thì tăng newCount
                newCount++;
                inputString = sub(inputString,inputString.indexOf(c));
            }
            if(mCount >= newCount){ // kiểm tra nếu newCount mới >= cũ thì gán cũ thành mới
                mCount = newCount;
            }else {
                return false; // còn ngược lại thì false
            }
        }
        return true; // hoàn thành quá trình thì true
    }

    static String sub(String st, int index){  // hàm xóa phần tử thứ index trong chuỗi
        return st.substring(0,index) + st.substring(index+1);
    }

    /** 44 **
     * Given a valid email address, find its domain part.
     */

    String findEmailDomain(String address) {
        return  address.substring(address.lastIndexOf('@') + 1);
    }

    /** 45 **
     *Given a string, find the shortest possible string which can be achieved by adding 
     * characters to the end of initial string to make it a palindrome.
     */

    String buildPalindrome(String st) {  
        String mStOut = st;  // Tạo biến đệm cho chuỗi để gán giá trị đầu ra
        for(int i =0; i < st.length() ; i++){
            mStOut = st + MirroString(st.substring(0,i));  // lật từng đoạn chuỗi rồi cộng với st cho tới khi nào được chuỗi đối xừng
            if(MirroString(mStOut).equals(mStOut)){ // kiểm tra đối xứng
                return mStOut;
            }
        }
        return "1";
    }

    static  String MirroString(String st){ // hàm đạo chuỗi
        return   new StringBuffer(st).reverse().toString();
    }

    /** 46 **
     *
     */

}
