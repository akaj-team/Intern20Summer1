package com.asiantech.intern20summer1.intro;

public class IslandOfKnowledge {
    public static void main(String[] args) {
        //RUN main() with Coverage
        System.out.println("Ex 19: " + areEquallyStrong(10, 20, 20,10));

        System.out.println("Ex 20: " + arrayMaximalAdjacentDifference(new int[]{-1, 4, 10, 3, -2}));

        System.out.println("Ex 21: " + isIPv4Address("64.233.161.00"));
    }

    static boolean areEquallyStrong(int yourLeft, int yourRight, int friendsLeft, int friendsRight) {
        /**
         * Call two arms equally strong if the heaviest weights they each are able to lift are equal.
         *
         * Call two people equally strong if their strongest arms are equally strong
         * (the strongest arm can be both the right and the left), and so are their weakest arms.
         *
         * Given your and your friend's arms' lifting capabilities find out if you two are equally strong.
         */
        if((yourLeft == friendsLeft && yourRight == friendsRight)
                || yourLeft == friendsRight && yourRight == friendsLeft ){
            return true;
        } else{
            return false;
        }
    }

    static int arrayMaximalAdjacentDifference(int[] a) {
        /**
         * Given an array of integers,
         * find the maximal absolute difference between any two of its adjacent elements.
         */
        int max = 0;
        for (int i = 0; i < a.length-1; i++ ) {
            max = (Math.abs(a[i]-a[i+1]) > max) ? Math.abs(a[i]-a[i+1]) : max;
        }
        return max;
    }

    static boolean isIPv4Address(String a) {
        /**
         * An IP address is a numerical label assigned to each device
         * (e.g., computer, printer) participating in a computer network
         * that uses the Internet Protocol for communication.
         * There are two versions of the Internet protocol,
         * and thus two versions of addresses. One of them is the IPv4 address.
         *
         * Given a string, find out if it satisfies the IPv4 address naming rules.
         */
        String[] b = a.split("[.]");
        if (b.length!=4) return false;
        try{
            for ( String item : b) {
                if(item.matches("[0][1-9]")||item.matches("[0][0]")){
                    return false;
                }
                if ((Integer.parseInt(item) < 0) || (Integer.parseInt(item) > 255)){
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
