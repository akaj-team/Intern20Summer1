package intro;

public class EruptionofLight {
    public static void main(String[] args) {
        /**
         * An email address such as "John.Smith@example.com" is made
         * up of a local part ("John.Smith"), an "@" symbol, then a domain part ("example.com").
         */
        System.out.println("44.Result domain:"+findEmailDomain("vuong.phan@asiantech.vn"));
        /**
         * Given a string, find the shortest possible string which can be
         * achieved by adding characters to the end of initial string to make it a palindrome.
         */
        System.out.println("45.Result:"+buildPalindrome("ababab"));
        /**
         * Given an array of the numbers of votes given to each of the candidates so far,
         * and an integer k equal to the number of voters who haven't
         * cast their vote yet, find the number of candidates who still have a chance to win the election.
         */
        int[] votes = {1,2,3,3};
        System.out.println("46.Result:"+electionsWinners(votes,0));
        /**
         * A media access control address (MAC address) is a unique identifier
         * assigned to network interfaces for communications on the physical network segment.
         */
        System.out.println("47.Result:"+isMAC48Address("00-1B-63-84-45-E6"));

    }
    static String findEmailDomain(String address) {
        String result = "";
        int index =0;
        index = address.lastIndexOf('@');
        for(int j= index+1;j<address.length();j++ ){
            result =result + address.charAt(j);
        }
        return result;
    }
    static String buildPalindrome(String st) {
        String str = new StringBuilder(st).reverse().toString();
        if(str.equals(st)){
            return st;
        }
        for(int i=0; i<st.length();i++){
            if((st.substring(0,i)+str).equals(new StringBuilder(st.substring(0,i)+str).reverse().toString())){
                return st.substring(0,i)+str;
            }
        }
        return  null;
    }
    static int electionsWinners(int[] votes, int k) {
        int max =0;
        boolean checkRepeat = false;
        for(int i=0;i<votes.length;i++){
            if(max<votes[i]){
                max = votes[i];
                checkRepeat=false;
            }else if(max == votes[i]){
                checkRepeat = true;
            }
        }
        int count=0;
        for(int i=0;i<votes.length;i++){
            if((votes[i]+k)>max && k!=0){
                count++;
            }
        }
        if(k==0 && checkRepeat==false){
            count =1;
        }else if(k==0 && checkRepeat==true){
            count=0;
        }
        return count;
    }
    static boolean isMAC48Address(String inputString) {
        if(inputString.length()!=17){
            return false;
        }
        for(int i=0;i<inputString.length();i++){
            if(i % 3 == 2){
                if(!(inputString.charAt(i) == '-')){
                    return false;
                }
            }else{
                char character=inputString.charAt(i);
                if(('0'>character || character>'9') && ('A'> character || character >'F') ){
                    return false;
                }
            }
        }
        return true;
    }
}
