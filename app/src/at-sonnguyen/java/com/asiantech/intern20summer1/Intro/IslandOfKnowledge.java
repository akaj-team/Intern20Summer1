package com.asiantech.intern20summer1.Intro;

public class IslandOfKnowledge {
    /*
    * Check Weather 2 people are Equally Strong
    */
    static boolean areEquallyStrong(int yourLeft, int yourRight, int friendsLeft, int friendsRight) {
        return yourLeft==friendsLeft && yourRight==friendsRight || yourLeft==friendsRight && yourRight== friendsLeft;
    }
    /*
    *find the maximal absolute difference between any two of its adjacent elements
    */
    static int arrayMaximalAdjacentDifference(int[] inputArray) {
        int Max = 0 ;
        for (int index = 0 ;index< inputArray.length - 1 ; index++){
            if (Math.abs(inputArray[index] - inputArray[index+1] )> Max){
                Max = Math.abs(inputArray[index] - inputArray[index+1] );
            }
        }
        return Max;
    }
    /*
    * find out if it satisfies the IPv4 address naming rules.
    */
    static boolean isIPv4Address(String inputString) {
        String[] splitString = inputString.split("[.]");
        if (splitString.length != 4) {
            return false;
        } else {
            for (String string : splitString) {

                if (string.isEmpty()) {
                    return false;
                }
                if (!string.matches("[0-9]{1,3}")) {
                    return false;
                }
                int number = Integer.parseInt(string);

                if (!(number >= 0 && number <= 255)) {
                    return false;
                }
                System.out.println(number);

                System.out.println(string.length());
                System.out.println(string.charAt(0));
                if (string.length() > 1 && string.charAt(0) == '0' ){
                    return false;
                }
            }
        }

        return true;
    }
    /*
    * Find the minimal length of the jump enough to avoid all the obstacles.
    */
    static int avoidObstacles(int[] inputArray) {
        int MaxOfInputArray = inputArray[0];
        for (int index = 0 ; index < inputArray.length ; index++){
            if (inputArray[index] > MaxOfInputArray){
                MaxOfInputArray = inputArray[index];
            }
        }
        System.out.println(MaxOfInputArray);
        for ( int i = 2 ; i < MaxOfInputArray + 2; i++ ){
            int count = 0 ;
            for (int index = 0 ; index < inputArray.length ; index++){
                if (inputArray[index] % i == 0){
                    count++ ;
                    break ;
                }
            }
            if (count == 0 ){
                return i ;
            }
        }
        return 0 ;
    }
    /*
    * Return the blurred image as an integer, with the fractions rounded down.
    */
    static int[][] boxBlur(int[][] image) {
        int[][] result = new int[image.length-2][image[0].length-2];
        for (int i =1; i < image.length-1; i++)
            for (int j = 1; j < image[0].length-1; j++) {
                int sum=0;
                for(int ii=i-1;ii<=i+1;ii++) {
                    for(int jj=j-1;jj<=j+1;jj++) {
                        sum+=image[ii][jj];
                    }
                }
                result[i-1][j-1]=sum/9;
            }
        return result;
    }
    /*
    * Starting off with some arrangement of mines we want to create a Minesweeper game setup.
    */
    static int[][] minesweeper(boolean[][] matrix) {
        int[][] sol = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                for (int ii = Math.max(0,i - 1); ii <= Math.min(i + 1,matrix.length-1) ; ii++)
                    for (int jj = Math.max(0,j - 1); jj <= Math.min(j + 1,matrix[0].length-1); jj++) {
                        if (matrix[ii][jj] && (i!=ii || jj!=j)) {
                            sol[i][j]++;
                        }
                    }

        return sol;
    }

    public static void main(){
        System.out.print(areEquallyStrong(15,10,10,15));
        int [] inputArray = {1,5,6,3,4,5,3};
        System.out.print(arrayMaximalAdjacentDifference(inputArray));
        String string = "192.168.143.1";
        System.out.print(isIPv4Address(string));
        System.out.print(avoidObstacles(inputArray));
        int[][] Matrix = {{1,2,3},{1,2,1},{3,4,2}};
        int[][] BoxBlur = boxBlur(Matrix);
        for ( int i = 0 ; i < BoxBlur.length ; i++ ){
            for ( int j = 0 ; j < BoxBlur[0].length ; j++ ){
                System.out.print(BoxBlur[i][j]);
            }
        }
        boolean[][] MaxtrixBoolean = {{true,true,false},{true,false,false},{true,false,true}};
        int [][] MineSeeperMatrix = minesweeper(MaxtrixBoolean);
        for(int i = 0 ; i < MineSeeperMatrix.length ; i++){
            for ( int j = 0 ; j < MineSeeperMatrix[0].length;j++){
                System.out.print(MineSeeperMatrix[i][j]);
            }
        }

    }

}
