package intro;

public class IslandofKnowledge {
    public static void main(String[] args) {
        /**
         * Call two arms equally strong if the heaviest weights they each are able to lift are equal.
         *
         * Call two people equally strong if their strongest arms are equally
         * strong (the strongest arm can be both the right and the left), and so are their weakest arms.
         */
        System.out.println("19.Result :"+areEquallyStrong(10,15,15,10));
        /**
         * Given an array of integers, find the maximal absolute difference between any two of its adjacent elements.
         */
        int[] inputArray = {2, 4, 1, 0};
        System.out.println("20.Result:"+arrayMaximalAdjacentDifference(inputArray));
        /**
         *An IP address is a numerical label assigned to each device (e.g., computer, printer) participating in a computer network that uses the Internet Protocol for communication. There are two versions of the
         *  Internet protocol, and thus two versions of addresses. One of them is the IPv4 address.
         */
        System.out.println(isIPv4Address("21.Result:" +"192.168.1.100"));
        /**
         * You are given an array of integers representing coordinates of obstacles situated on a straight line.
         *
         * Assume that you are jumping from the point with coordinate 0 to the right. You are allowed only to make jumps of the same length represented by some integer.
         */
        int[] inputArray22 = {5,3,6,7,9};
        System.out.println("22.Result:"+avoidObstacles(inputArray22));
        /**
         * Last night you partied a little too hard. Now there's a black and white photo of you that's about to go viral! You can't let this ruin your reputation,
         * so you want to apply the box blur algorithm to the photo to hide its content.
         */
        int[][] image = {{1,1,1},{1,7,1},{1,1,1}};
        System.out.println("23.Result:"+boxBlur(image));
        /**
         * In the popular Minesweeper game you have a board with some mines and those cells that don't contain a mine have a number in it that indicates the total number of mines in the neighboring cells.
         * Starting off with some arrangement of mines we want to create a Minesweeper game setup.
         */
       boolean[][] matrix = {{true, false, false},
                            {false, true, false},
                            {false, false, false}};
       System.out.println("24.Result:"+minesweeper(matrix));
    }
    static int[][] minesweeper(boolean[][] matrix) {
        int[][] sol = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                for (int ii = Math.max(0, i - 1); ii <=
                        Math.min(i + 1, matrix.length - 1); ii++) {
                    for (int jj = Math.max(0, j - 1); jj <=
                            Math.min(j + 1, matrix[0].length - 1); jj++) {
                        if (matrix[ii][jj] && (i != ii || jj != j)) {
                            sol[i][j]++;
                        }
                    }
                }
            }
        }
        return sol;
    }
    static int[][] boxBlur(int[][] image) {
        int[][] result = new int[image.length - 2][image[0].length - 2];
        for (int i = 1; i < image.length - 1; i++) {
            for (int j = 1; j < image[0].length - 1; j++) {
                int sum = 0;
                for (int ii = i - 1; ii <= i + 1; ii++) {
                    for (int jj = j - 1; jj <= j + 1; jj++) {
                        sum += image[ii][jj];
                    }
                }
                result[i - 1][j - 1] = sum / 9;
            }
        }
        return result;
    }
    static int avoidObstacles(int[] inputArray) {
        int result = 1;
        boolean jump = false;
        while(true) {
            jump = true;
            for(int i = 0;i<inputArray.length;i++){
                if(inputArray[i] % result == 0){
                    jump = false;
                }
            }
            if(jump){
                return result;
            }
            result++;
        }
    }
    static boolean areEquallyStrong(int yourLeft, int yourRight, int friendsLeft, int friendsRight) {

        if((yourLeft+yourRight)==(friendsLeft+friendsRight) && (Math.max(yourLeft,yourRight))==(Math.max(friendsRight,friendsLeft))){
            return true;
        }
        return false;
    }
    static int arrayMaximalAdjacentDifference(int[] inputArray) {
        int subNext = 0;
        int check = 0;
        for (int i = 0; i < inputArray.length - 1; i++) {
            check=Math.abs(inputArray[i]-inputArray[i+1]);
            if (check > subNext) {
                subNext=check;
            }

        }
        return subNext;
    }
    static boolean isIPv4Address(String inputString) {
        String[] ip = inputString.split("[.]");
        if (ip.length != 4) {
            return false;
        }
        for (int i = 0; i < ip.length; i++) {
            if (!ip[i].matches("[0-9]{1,3}")) {
                return false;
            }
            if (ip[i].isEmpty()) {
                return false;
            }
            if(ip[i].length()>1 && ip[i].charAt(0)=='0'){
                return false;
            }
            if ((Integer.parseInt(ip[i]) < 0) || (Integer.parseInt(ip[i]) > 255)) {
                return false;
            }
        }
        return true;
    }
}
