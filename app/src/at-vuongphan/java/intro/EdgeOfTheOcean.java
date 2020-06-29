package intro;

import java.util.Scanner;

public class EdgeOfTheOcean {
    static Scanner input= new Scanner(System.in);
    public static void main(String[] args) {
        /**
         * Given an array of integers, find the pair of adjacent
         * elements that has the largest product and return that product.
         */
        int[] inputArray= {3,6,-2,-5,7,3};
        System.out.println(adjacentElementsProduct(inputArray));
        /**
         * Below we will define an n-interesting polygon.
         * Your task is to find the area of a polygon for a given n.
         */
        System.out.println("Area");
        System.out.print("Input please:");
        int numberArea = Integer.parseInt(input.nextLine());
        System.out.println(shapeArea(numberArea));
        /**
         * Ratiorg got statues of different sizes as a present
         * from CodeMaster for his birthday, each statue having an non-negative integer size.
         * Since he likes to make things perfect, he wants to arrange them from smallest to largest so
         * that each statue will be bigger than the previous one exactly by 1. He may need some
         * additional statues to be able to accomplish that. Help him figure out the minimum number of additional statues needed.
         */
        int[] status={6,2,3,8};
        System.out.println("Resule makeArrayConsecutive2: "+makeArrayConsecutive2(status));
        /**
         *Given a sequence of integers as an array, determine whether it is possible to obtain
         * a strictly increasing sequence by removing no more than one element from the array.
         */
        int[] sequence = {1,3,2,1};
        System.out.println("Result almostIncreasingSequence: "+almostIncreasingSequence(sequence));
        /**
         * After becoming famous, the CodeBots decided to move into a new building together. Each of the rooms has a different cost,
         * and some of them are free, but there's a rumour that all the free rooms are haunted! Since the CodeBots are quite superstitious, they refuse to stay in any of the free rooms, or any of the rooms below any of the free rooms.
         */
        int[][] matrix={{0,1,1,2},{0,5,0,0},{2,0,0,3}};
        System.out.println("Result matrixElementsSum: "+matrixElementsSum(matrix));

    }
    static int adjacentElementsProduct(int[] inputArray) {
        int result = inputArray[0] * inputArray[1];
        int tmp;
        for(int index =1;index<inputArray.length-1;index++){
            tmp = inputArray[index]*inputArray[index+1];
            if(result<tmp){
                result = tmp;
            }
        }
        return result;
    }
    static int shapeArea(int n){
        int result=0;
        result= (n * n) + (n - 1)* (n - 1);
        return result;
    }
    static int makeArrayConsecutive2(int[] statues){
        int min = statues[0];
        int max = statues[0];
        for(int in=0; in<statues.length;in++){
            if(statues[in]<min){
                min=statues[in];
            }
            if(max< statues[in]){
                max=statues[in];
            }
        }
        return max - min - statues.length +1;
    }
    static boolean almostIncreasingSequence(int[] sequence) {
        int count1 = 0;
        int count2 = 0;
        if (sequence.length == 2) {
            return true;
        }
        for (int i = 0; i < sequence.length - 1; i++) {
            if (sequence[i] >= sequence[i + 1]) {
                count1 += 1;
            }
        }
        for (int i = 0; i < sequence.length - 2; i++) {
            if (sequence[i] >= sequence[i + 2]) {
                count2 += 1;
            }
        }
        if (count1 > 1) {
            return false;
        } else if (count2 > 1) {
            return false;
        } else return true;
    }
    static int matrixElementsSum(int[][] matrix) {
        int sum = 0;
        for (int a = 0; a < matrix.length; a++) {
            for (int b = 0; b < matrix[0].length; b++) {
                if (matrix[a][b] == 0) {
                    for (int c = a; c < matrix.length; c++) {
                        matrix[c][b] = 0;
                    }

                }
                sum = sum + matrix[a][b];
            }
        }
        return sum;
    }
}
