package com.asiantech.intern20summer1.intro;

import java.util.ArrayList;
import java.util.List;

public class DivingDeeper {

    /** 34 **
     * Given array of integers, remove each kth element from it.
     */

    Integer[] extractEachKth(int[] inputArray, int k) {
        List<Integer> Output = new ArrayList<>();  // Tạo mảng đệm đầu ra
        int RemoveAt = k;    // vị tri xóa giá trị,
        for (int i = 0; i < inputArray.length; i++) {
            if(i+1 == RemoveAt) {   // quét mảng nếu vị trí xóa trùng với vị tri quét thì continue
                RemoveAt += k;
                continue;
            }
            Output.add(inputArray[i]); // nếu không thì add phần tử mới cho mảng Output
        }
        Integer[] outputArray = Output.toArray(new Integer[Output.size()]); // ép lại kiểu rồi xuất ra
        return outputArray;
    }

}
