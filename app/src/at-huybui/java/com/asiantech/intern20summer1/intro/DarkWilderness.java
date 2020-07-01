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

}
