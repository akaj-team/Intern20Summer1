package com.asiantech.intern20summer1.intro;

import java.util.ArrayList;
import java.util.List;

public class DarkWilderness {

    /** 38 **
     * Caring for a plant can be hard work, but since you tend to it regularly,
     * you have a plant that grows consistently.
     * Each day, its height increases by a fixed amount represented by the integer upSpeed.
     * But due to lack of sunlight, the plant decreases in height every night, by an amount represented by downSpeed.
     *
     * Since you grew the plant from a seed, it started at height 0 initially.
     * Given an integer desiredHeight, your task is to find how many days it'll take for the plant to reach this height.
     * */

    int growingPlant(int upSpeed, int downSpeed, int desiredHeight) {
        int mCurrent = 0;     // biến độ cao cây hiện tại
        int dayNumber   = 0;  // biến đếm này
        while(mCurrent < desiredHeight){  // lặp so sánh độ cao cây
            dayNumber++;      //đến ngày
            mCurrent = mCurrent + upSpeed;   // tăng độ cao cây ban ngày
            if (mCurrent >= desiredHeight){  // nếu đủ cao thì thoát lặp
                break;
            }
            mCurrent = mCurrent - downSpeed; // không đủ cao thì tối bị thấp đi

        }
        return dayNumber;// xuất ngày

    }


    /** 39 **
     *You found two items in a treasure chest!
     * The first item weighs weight1 and is worth value1,
     * and the second item weighs weight2 and is worth value2.
     * What is the total maximum value of the items you can take with you,
     * assuming that your max weight capacity
     * is maxW and you can't come back for the items later?
     *
     * Note that there are only two items and you can't bring more than one item of each type,
     * i.e. you can't take two first items or two second items.*/
    int knapsackLight(int value1, int weight1, int value2, int weight2, int maxW) {
        if(maxW >= weight1+weight2){
            return value1+value2;
        }else if(value1 >= value2 && maxW >= weight1){
            return value1;
        }else if(value1 >= value2 && maxW >= weight2 ){
            return value2;
        }else if(value1 < value2 && maxW >= weight2 ){
            return value2;
        }else if(value1 < value2 && maxW >= weight1 ) {
            return value1;
        }
        return 0;
    }

    /** 40 **
     * Given a string, output its longest prefix which contains only digits.
     */

    String longestDigitsPrefix(String inputString) {
        List<Character> listNumber = new ArrayList<>();  // mảng đệm lưu ký tự số
        boolean flat =false;    // cờ thoát khi đọc xong số
        for(int i = 0; i < inputString.length(); i++){
            char c = inputString.charAt(i);
            if(Character.isDigit(c)){   // kiểm tra ký tự c có phải số hay ko
                flat = true;           // đúng thì bật cờ
                if(i !=0){             // nếu i != thì kiểm tra ký tự phía trước có khác dấu cách ' ' khônt
                    if(inputString.charAt(i-1) != ' '){
                        listNumber.add(c);   // nếu khác thì add số vào list
                    }else {
                        break;  // giống thì thoát
                    }
                }else{
                    listNumber.add(c);  // nếu mà i = 0 thì cứ add trực tiếp vào mảng vì trước đó ko có gì cả
                }
            }
            if(!Character.isDigit(c) && flat){ // kết thúc việc đọc số
                break;
            }
        }

        String Out = "";
        for(Character c: listNumber){ // quét mảng và gán lại vào chuỗi
            Out += c;
        }
        return Out;
    }

    /** 41 **
     * Let's define digit degree of some positive integer as the number of times we need
     * to replace this number with the sum of its digits until we get to a one digit number.
     *
     * Given an integer, find its digit degree.
     */

    static int digitDegree(int n) {
        int mCount = 0;
        while( n > 10){ // nếu n còn lớn hơn 10 thì tăng count và gán lại m = SumNumber
            mCount++;
            n = SumNumber(n);
        }
        return mCount;
    }

    static int SumNumber(int n){  // hàm tính tổng các sô ở trong chữ số
        int sum = 0;
        while(n > 0){
            sum += n%10;
            n /=10;
        }
        return sum;
    }


    /** 42
     * Given the positions of a white bishop and a black pawn on the standard chess board,
     * determine whether the bishop can capture the pawn in one move.
     *
     * The bishop has no restrictions in distance for each move,
     * but is limited to diagonal movement. Check out the example below to see how it can move:
     */
    boolean bishopAndPawn(String bishop, String pawn) {

        return     Math.abs(bishop.charAt(0) - pawn.charAt(0) )
                == Math.abs(bishop.charAt(1) - pawn.charAt(1));
    }


}
