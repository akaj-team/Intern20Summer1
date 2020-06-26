package com.asiantech.intern20summer1.Intro;

public class EdgeOfTheOcean {
    /*
    * Given an array of integers, find the pair of adjacent elements that has the largest product and return that product.
    * */
    int adjacentElementsProduct(int[] inputArray) {
        int max = inputArray[0] * inputArray[1];
        for (int i = 0; i < inputArray.length - 1; i++) {
            if (inputArray[i] * inputArray[i + 1] > max) {
                max = inputArray[i] * inputArray[i + 1];
            }
        }
        return max;
    }

    /*
    *Below we will define an n-interesting polygon.
    * Your task is to find the area of a polygon for a given n.
    * An n-interesting polygon is obtained by taking the n - 1-interesting
    polygon and appending 1-interesting polygons to its rim, side by side.
    * You can see the 1-, 2-, 3- and 4-interesting polygons in the picture below.
    */
    int shapeArea(int n) {
        return n*n + (n-1)*(n-1);
    }
    /*
    * Ratiorg got statues of different sizes as a present from CodeMaster for his birthday,
    * each statue having an non-negative integer size. Since he likes to make things perfect,
    * he wants to arrange them from smallest to largest so that each statue will be bigger
     than the previous one exactly by 1.
    * He may need some additional statues to be able to accomplish that.
    * Help him figure out the minimum number of additional statues needed.
    * */
    int makeArrayConsecutive2(int[] statues) {
        int max = statues[0];
        int min = statues[0];
        for (int i = 0;i<statues.length; i++){

            if (statues[i]> max){
                max = statues[i];
            }
            if (statues[i]<min){
                min = statues[i];
            }
        }
        return (max - min - statues.length + 1);
    }
    /*
    * Given a sequence of integers as an array,
    * determine whether it is possible to obtain a strictly increasing sequence
    * by removing no more than one element from the array.
    * */


}
