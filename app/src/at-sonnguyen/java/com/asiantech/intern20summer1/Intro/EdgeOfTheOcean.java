package com.asiantech.intern20summer1.Intro;

public class EdgeOfTheOcean {
    public static void main(){
    int Shape = shapeArea(15);
    int [ ] inputArray = {1,2,5,8,9};
    int AddToArray = makeArrayConsecutive2(inputArray);
    boolean checkSequence = almostIncreasingSequence(inputArray);
    int [][] Matrix = {{2,3,5},{1,3,6},{5,4,9}};
    int Sum = matrixElementsSum(Matrix);
    }
    /*
    * Given an array of integers, find the pair of adjacent elements that has the largest product and return that product.
    * */
    static int adjacentElementsProduct(int[] inputArray) {
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
    static int shapeArea(int n) {
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
    static int makeArrayConsecutive2(int[] statues) {
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
    static boolean almostIncreasingSequence(int[] sequence) {
        int count1 = 0, count2 = 0;
        for (int i = 0; i < sequence.length-1;i++){
            if (sequence[i]>=sequence[i+1]){
                count1 +=1;
            }
        }
        for (int i = 0; i < sequence.length-2;i++){
            if (sequence[i]>=sequence[i+2]){
                count2 +=1;
            }
        }
        if (count1 > 1  ) {
            return false;
        }else if (count2 >1){
            return false;
        }else return true;

    }
    /*
    * Given matrix, a rectangular matrix of integers,
    *  where each value represents the cost of the room,
    * your task is to return the total sum of all rooms that are suitable for the CodeBots
    * */
    static int matrixElementsSum(int[][] matrix) {
        int Sum =0;
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                if(matrix[i][j]==0){
                    for(int k=i;k<matrix.length;k++){
                        matrix[k][j]=0;
                    }
                    continue;
                }
                else
                    Sum+=matrix[i][j];
            }
        }
        return Sum;
    }
}
