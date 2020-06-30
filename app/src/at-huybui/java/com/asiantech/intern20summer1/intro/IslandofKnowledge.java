package com.asiantech.intern20summer1.intro;

import java.util.Arrays;

public class IslandofKnowledge {

    /** 19: Are Equally Strong **
     * Call two arms equally strong if the heaviest weights they each are able to lift are equal.
     *
     * Call two people equally strong if their strongest arms are equally strong
     * (the strongest arm can be both the right and the left), and so are their weakest arms.
     *
     * Given your and your friend's arms' lifting capabilities find out if you two are equally strong.
     */


    boolean areEquallyStrong(int yourLeft, int yourRight, int friendsLeft, int friendsRight) {
        /**
         * true Nếu tay trái của bạn bằng tay trái của họ và tay phải của bạn bằng tay phải của họ
         * hoặc nếu tay trái của bạn bằng tay phải của họ và tay phải của bạn bằng tay trái của họ
         * còn lai false
         */

        if(   (yourLeft     == friendsLeft  )   && (yourRight   == friendsRight)||
              (yourLeft     == friendsRight )   && (yourRight   == friendsLeft )  ){
            return true;
        }else{
            return false;
        }
    }


    /** 20 array Maximal Adjacent Difference **
     *Given an array of integers,
     * find the maximal absolute difference between any two of its adjacent elements.
     */
    int arrayMaximalAdjacentDifference(int[] inputArray) {
        int Output = 0;
        for(int i = 0; i < inputArray.length - 1; i++){
            int x = Math.abs(inputArray[i] - inputArray[i+1]);
            if(Output < x){
                Output = x;
            }
        }
        return Output;
    }


    /** 21 : isIPv4Address
     * Given a string, find out if it satisfies the IPv4 address naming rules.
     **/
    boolean isIPv4Address(String inputString) {
        String buf = inputString;   // Tạo bộ đệm lưu chuỗi
        int mCountNumber = 0;       // Biến đếm số lượng chữ số
        while(!buf.isEmpty()){  //Kiểm tra chuỗi có rỗng không
            int index = buf.indexOf('.');   //Tìm vị trí dấu '.'
            String NumberSt = "";           // Tạo chuỗi đệm lưu số kiểu string
            if(index != -1){     // phát hiện dấu '.' thì vô cắt chuỗi lấy số
                NumberSt = buf.substring(0,index);
                buf = buf.substring(index+1);
            } else {
                NumberSt = buf; // hết dấu chấm thì gán chuỗi còn lại cho NumberSt
                buf = "";
            }

            for(int i = 0; i < NumberSt.length(); i++){

                /** tất cả các trường hợp sau đều false
                 *  bên trong chuỗi có ký tự ko phải số
                 *  trước chuỗi có ký tự là số '0'
                 *  chuỗi dài hơn 3 ký tự
                 *  chuỗi rỗng
                 */
                if(Character.isLetter(NumberSt.charAt(i))){ // Kiểm tra xem bên trong chuỗi có phải số không
                    return false;
                }

                if(NumberSt.length() > 1 && NumberSt.charAt(0) =='0'){ // Kiểm tra xem trước sô có số 0 không
                    return false;
                }

                if(NumberSt.length() > 3){ // kiểm tra xem độ dài chuỗi có lớn hơn 3 không
                    return false;
                }
            }

            if(NumberSt.isEmpty()){ // chuỗi rỗng là false
                return false;
            }else {
                if(mCountNumber > 3){   // quá 3 lần lặp là false
                    return false;
                }
                int number = Integer.parseInt(NumberSt); // số < 0 || > 225 là false
                if(number <0 || number >255){
                    return false;
                }
                mCountNumber++;
            }

        }
        if (mCountNumber != 4){
            return false;
        }
        return true;
    }

    /** 22 : avoidObstacles
     *You are given an array of integers representing coordinates of obstacles situated on a straight line.
     *
     * Assume that you are jumping from the point with coordinate 0 to the right.
     * You are allowed only to make jumps of the same length represented by some integer.
     *
     * Find the minimal length of the jump enough to avoid all the obstacles.*/
    int avoidObstacles(int[] inputArray)  {
        int OutPut = 1;
        // printSln("old:"+Arrays.toString(inputArray));
        Arrays.sort(inputArray);  // Nổi bọt
        // printSln("new:"+Arrays.toString(inputArray));
        boolean flat =true;  // Tạo biến cờ nhảy
        while(flat) {        // Nếu chưa tìm được bước nhảy phù hợp thì tiếp tục nhảy
            flat= false;     // xóa cờ nhảy
            OutPut +=1;     // tăng giá trị bước nhảy mỗi lần nhảy
            for (int j : inputArray) { // duyệt các giá trị của mảng
                if (j%OutPut == 0) {   // một trong các giá trị chia hết cho bước nhảy thì sẽ trùng.
                    flat =true;        // bật lại cờ nhảy thoát vòng lặp tăng bước và nhảy tiếp
                    break;
                }
            }
        }
        return OutPut;
    }

    /** 23 : boxBlur
     * Last night you partied a little too hard.
     * Now there's a black and white photo of you that's about to go viral!
     * You can't let this ruin your reputation, so you want to apply the box blur algorithm to the photo to hide its content.
     *
     * The pixels in the input image are represented as integers.
     * The algorithm distorts the input image in the following way:
     * Every pixel x in the output image has a value equal to the average value of the pixel values
     * from the 3 × 3 square that has its center at x, including x itself.
     * All the pixels on the border of x are then removed.
     *
     * Return the blurred image as an integer, with the fractions rounded down.
     */

    int[][] boxBlur(int[][] image) {
        int mHorizon    = image[0].length;
        int mVertical   = image.length;
        int newHorizon  = mHorizon - 2; // chiều ngang ma trận mới
        int newVertial  = mVertical - 2;// chiều dọc ma trận mới
        int[][] newArray= new int[newVertial][newHorizon]; // khởi tạo ma trận mới
        for(int i = 0; i < newVertial; i++){
            for (int j = 0; j < newHorizon; j++){
                newArray[i][j] =  ( image[i][j]   + image[i][j+1]     + image[i][j+2]   +
                                    image[i+1][j] + image[i+1][j+1]   + image[i+1][j+2] +
                                    image[i+2][j] + image[i+2][j+1]   + image[i+2][j+2] )/9;

            }
        }
        return newArray;
    }

}




