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

}
