package com.asiantech.intern20summer1.introJava;

public class RainbowofClarity {

    /**
     * 48 **
     * Determine if the given character is a digit or not
     */
    boolean isDigit(char symbol) {
        return (symbol + "").matches("^[0-9]$"); // Ép về String rồi regex nó luôn
    }


    /**
     * 49 **
     * Given a string, return its encoding defined as follows:
     * Example: "aaabbbccc" => "3a3b3c"
     * "hhccccaa"  => "2h4c2a"
     * "abccdeee"  => "ab2cd3e"
     */

    String lineEncoding(String s) {
        String mOutPut = ""; // tạo biến đệm Output
        for (int i = 0; i < s.length(); ) {
            char currentChar;     // Biến lưu ký tự hiện tại
            int Count = 0;// Đếm số ký tự hiện tại reset khhi đếm ký tự mới
            while (true) { // true loop
                Count++; // vào loop đếm số ký tự
                currentChar = s.charAt(i); // ký tự hiện tại
                i++; // tăng i để chút lấy ký tự tiếp theo
                if (i >= s.length()) {
                    break;    // đặt điêu kiện nếu i >= chiều dài chuỗi thì kết thúc quá trình
                }
                if (currentChar == s.charAt(i)) { // nếu ký tự hiện tại giống ký tự tiếp theo thì continue
                    continue;
                } else {
                    break; // nếu khác thì kết thúc quá trình đếm lấy Count và ký tự hiện tại
                }
            }

            // nếu Count == 1 chỉ + c vào Output, nếu Count > 1 thì cộng cả Count và c vào Output
            mOutPut += Count == 1 ? ("" + s.charAt(i - 1)) : ("" + Count + currentChar);

        }
        return mOutPut;
    }


    /**
     * 50 **
     * Given a position of a knight on the standard chessboard,
     * find the number of different moves the knight can perform.
     */

    int chessKnight(String cell) {
        /** regex    / ta dùng biểu thức chính quy cho các trường hợp
         * 8: ^[c-f][3-6]$
         * 6: ^[c-f][27]|[bg][3-6]$
         * 4: ^[ah][3-6]|[c-f][18]|[bg][27]$
         * 3: ^[ah][27]|[bg][18]$
         * 2: ^[ah][18]$
         */
        if (cell.matches("^[c-f][3-6]$")) {
            return 8;
        }
        if (cell.matches("^[c-f][27]|[bg][3-6]$")) {
            return 6;
        }
        if (cell.matches("^[ah][3-6]|[c-f][18]|[bg][27]$")) {
            return 4;
        }
        if (cell.matches("^[ah][27]|[bg][18]$")) {
            return 3;
        }
        if (cell.matches("^[ah][18]$")) {
            return 2;
        }
        return 1;
    }

    /**
     * 51 **
     * Given some integer, find the maximal number
     * you can obtain by deleting exactly one digit of the given number.
     */
    int deleteDigit(int n) {
        int Output = 0;  // khai báo số đầu ra
        int Count = 0;   // biến đếm chỉ só lũy thừa
        while (true) {
            int firstNumber = ((int) (n / Math.pow(10, Count + 1)) * (int) Math.pow(10, Count)); // chia lũ thừa số đầu
            int lastNumber = (int) (n % Math.pow(10, Count)); // chia lũy thùa số sau
            int newValue = firstNumber + lastNumber; // giá trị mới bằng số đầu +  số sau
            Count++; // tăng lỹ thừa
            if (newValue > Output) {  // nếu newValue > Output thì gán lại
                Output = newValue;
            }
            if (firstNumber == 0) {  // nếu số đầu mà == 0 thì đã chia hết nên thoát
                break;
            }
        }
        return Output; // xuất
    }

}
