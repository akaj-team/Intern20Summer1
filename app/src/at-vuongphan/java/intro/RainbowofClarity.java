package intro;

public class RainbowofClarity {
    public static void main(String[] args) {
        /**
         * Determine if the given character is a digit or not.
         */
        System.out.println("48.Result"+isDigit('0'));
        /**
         * Given a string, return its encoding defined as follows:
         *
         * First, the string is divided into the least possible number of disjoint substrings consisting of identical characters
         * for example, "aabbbc" is divided into ["aa", "bbb", "c"]
         * Next, each substring with length greater than one is replaced with a concatenation of its length and the repeating character
         * for example, substring "bbb" is replaced by "3b"
         * Finally, all the new strings are concatenated together in the same order and a new string is returned.
         */
        System.out.println("49.Result:"+lineEncoding("aabbcdaa"));
        /**
         * Given a position of a knight on the standard chessboard, find the number of different moves the knight can perform.
         *
         */
        System.out.println("50.Result:"+chessKnight("a1"));
        /**
         * Given some integer, find the maximal number you can obtain by deleting exactly one digit of the given number.
         *
         */
        System.out.println("51.Result:"+deleteDigit(1000203));
    }
    static int deleteDigit(int n) {
        String stringNumber = String.valueOf(n);
        int max=0;
        for(int i=0;i<stringNumber.length();i++){
            StringBuilder stringBuilder = new StringBuilder(stringNumber);
            stringBuilder.deleteCharAt(i);
            if(Integer.parseInt(stringBuilder.toString())>max){
                max = Integer.parseInt(stringBuilder.toString());
            }
        }
        return max;
    }
    static int chessKnight(String cell) {
        int count = 0;
        int x = cell.charAt(0) % 97,
                y = cell.charAt(1) - '0' - 1;
        for (int i = x - 2 ; i <= x+2 ; i++ ){
            for (int j = y -2; j <= y+2 ; j++){
                if (i < 0 || j < 0||i>7||j>7) {
                    continue ;
                } else if ((Math.abs(i-x)==1&&Math.abs(j-y)==2)||(Math.abs(i-x)==2&&Math.abs(j-y)==1)){
                    count++ ;
                }
            }
        }
        return count ;
    }
    static boolean isDigit(char symbol) {
        if(('0' > symbol) || (symbol > '9') ){
            return false;
        }
        return true;
    }
    static String lineEncoding(String s) {
        String s1 = "";
        int count = 1, n = 0;
        for(int i = 1; i < s.length(); i++){
            n = i;
            if(s.charAt(i) == s.charAt(i-1)) count++;
            else{
                if(count == 1) s1 += s.charAt(i-1);
                else {
                    s1 += String.valueOf(count) + s.charAt(i-1);
                    count = 1;
                }
            }
        }

        if(count == 1) s1 += s.charAt(n);
        else {
            s1 += String.valueOf(count) + s.charAt(n);
            count = 1;
        }
        return s1;
    }
}
