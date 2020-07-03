package intro;

public class DarkWilderness {
    public static void main(String[] args) {
        /**
         * Caring for a plant can be hard work, but since you tend to it regularly,
         * you have a plant that grows consistently. Each day, its height increases by a
         * fixed amount represented by the integer upSpeed. But due to lack of
         * sunlight, the plant decreases in height every night, by an amount represented by downSpeed.
         */
        System.out.println("38.Result:"+growingPlant(100,10,910));
        /**
         * You found two items in a treasure chest! The first item weighs weight1 and is worth value1,
         * and the second item weighs weight2 and is worth value2. What is the total maximum value of the items you can take with
         * you, assuming that your max weight capacity is maxW and you can't come back for the items later?
         */
        System.out.println("39.Result:"+knapsackLight(10,5,6,4,8));
        /**
         * Given a string, output its longest prefix which contains only digits.
         */
        System.out.println("40.Result:"+longestDigitsPrefix("123aa1234"));
        /**
         * Let's define digit degree of some positive integer as the number of times we need to replace
         * this number with the sum of its digits until we get to a one digit number.
         */
        System.out.println("41.Result:"+digitDegree(99991));
        /**
         * Given the positions of a white bishop and a black pawn on the standard chess board,
         * determine whether the bishop can capture the pawn in one move.
         */
        System.out.println("42.Result:"+bishopAndPawn("a1","c3"));
    }
    static boolean bishopAndPawn(String bishop, String pawn) {
        if((bishop.charAt(0)+bishop.charAt(1))==(pawn.charAt(0)+pawn.charAt(1)) ||(bishop.charAt(0)-bishop.charAt(1))==(pawn.charAt(0)-pawn.charAt(1))){
            return true;
        }
        return false;
    }
    static int growingPlant(int upSpeed, int downSpeed, int desiredHeight) {
        int day = 1;
        int night = upSpeed - downSpeed;
        while (upSpeed < desiredHeight) {
            upSpeed += night;
            day++;
        }
        return day;
    }
    static int knapsackLight(int value1, int weight1, int value2, int weight2, int maxW) {
        if (weight1 + weight2 <= maxW) {
            return value1 + value2;
        }
        if (weight1 + weight2 > maxW && weight1 <= maxW && weight2 <= maxW && value1 > value2) {
            return value1;
        }
        if (weight1 + weight2 > maxW && weight1 <= maxW && weight2 <= maxW && value1 < value2) {
            return value2;
        }
        if (weight1 + weight2 > maxW && weight1 <= maxW && weight2 <= maxW && value1 == value2) {
            return value2;
        }
        if (weight1 + weight2 > maxW && weight1 <= maxW && weight2 > maxW) {
            return value1;
        }
        if (weight1 + weight2 > maxW && weight2 <= maxW && weight1 > maxW) {
            return value2;
        }
        if (weight1 + weight2 > maxW && weight2 <= maxW && weight1 <= maxW) {
            return value2;
        }
        return 0;
    }
    static String longestDigitsPrefix(String inputString) {
        String Result = "";


        for (int index = 0 ; index < inputString.length() ; index++){
            if ( Character.isWhitespace(inputString.charAt(index)) == true ){
                return "";
            }
        }
        for (int index = 0 ; index < inputString.length() ; index++){
            String temp = "";
            if (Character.isDigit(inputString.charAt(index))==true){

                for (int index2 = index ; index2 < inputString.length(); index2++){
                    if(Character.isDigit(inputString.charAt(index2)) == true){
                        temp += inputString.charAt(index2);
                    } else {
                        break;
                    }
                }
            }
            if (temp.length() > Result.length()){
                Result = temp ;
            }

        }
        return Result ;
    }
    static int digitDegree(int n) {
        String array = Integer.toString(n);
        int sum=0;
        int count=0;
        while(array.length()>1){
            for(int i=0;i<array.length();i++){
                sum=sum+Character.getNumericValue(array.charAt(i));
            }
            array=Integer.toString(sum);
            count++;
            sum=0;
        }
        return count;
    }

}
