package com.asiantech.intern20summer1.intro;

public class RainbowofClarity {

    /**48 **
     *Determine if the given character is a digit or not
     */
    boolean isDigit(char symbol) {
        return (symbol + "").matches("^[0-9]$"); // Ép về String rồi regex nó luôn
    }


    /** 49 **
     * Given a string, return its encoding defined as follows:
     * Example: "aaabbbccc" => "3a3b3c"
     *          "hhccccaa"  => "2h4c2a"
     *          "abccdeee"  => "ab2cd3e"
     */

    String lineEncoding(String s) {
        String mOutPut = ""; // tạo biến đệm Output
        for(int i = 0; i < s.length();){
            char currentChar;     // Biến lưu ký tự hiện tại
            int Count =0;// Đếm số ký tự hiện tại reset khhi đếm ký tự mới
            while(true){ // true loop
                Count++; // vào loop đếm số ký tự
                currentChar = s.charAt(i); // ký tự hiện tại
                i++; // tăng i để chút lấy ký tự tiếp theo
                if(i >= s.length()){
                    break;    // đặt điêu kiện nếu i >= chiều dài chuỗi thì kết thúc quá trình
                }
                if (currentChar == s.charAt(i)) { // nếu ký tự hiện tại giống ký tự tiếp theo thì continue
                    continue;
                }
                else{
                    break; // nếu khác thì kết thúc quá trình đếm lấy Count và ký tự hiện tại
                }
            }

            // nếu Count == 1 chỉ + c vào Output, nếu Count > 1 thì cộng cả Count và c vào Output
            mOutPut += Count ==1 ? (""+s.charAt(i - 1)) : (""+ Count + currentChar);

        }
        return mOutPut;
    }

}
