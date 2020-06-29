package com.asiantech.intern20summer1.intro;

public class IslandOfKnowledge {
    /**
     * Call two arms equally strong if the heaviest weights they each are able to lift are equal.
     * Call two people equally strong if their strongest arms are equally strong (the strongest arm
     * can be both the right and the left), and so are their weakest arms.
     * Given your and your friend's arms' lifting capabilities find out if you two are equally
     * strong.
     */
    boolean areEquallyStrong(int yourLeft, int yourRight, int friendsLeft, int friendsRight) {
        if (yourLeft == friendsLeft && yourRight == friendsRight) {
            return true;
        } else if (yourLeft != friendsLeft && yourRight != friendsRight) {
            return yourLeft == friendsRight && yourRight == friendsLeft;
        }
        return false;
    }

    /**
     * Given an array of integers, find the maximal absolute difference between any two of its
     * adjacent elements.
     */
    int arrayMaximalAdjacentDifference(int[] inputArray) {
        int max = 0;
        for (int i = 0; i < inputArray.length - 1; i++) {
            if (max < Math.abs(inputArray[i] - inputArray[i + 1])) {
                max = Math.abs(inputArray[i] - inputArray[i + 1]);
            }
        }
        return max;
    }

    /**
     * You are given an array of integers representing coordinates of obstacles situated on a
     * straight line.
     * Assume that you are jumping from the point with coordinate 0 to the right. You are allowed
     * only to make jumps of the same length represented by some integer.
     * Find the minimal length of the jump enough to avoid all the obstacles.
     */
    int avoidObstacles(int[] inputArray) {
        for (int i = 1; ; i++) {
            int count = 0;
            for (int value : inputArray) {
                if (value % i == 0) {
                    break;
                }
                count++;
                if (count >= inputArray.length) {
                    return i;
                }
            }
        }
    }

    /**
     * An IP address is a numerical label assigned to each device (e.g., computer)
     * participating in a computer network that uses the Internet Protocol for communication.
     * There are two versions of the Internet protocol, and thus two versions of addresses.
     * One of them is the IPv4 address.
     * Given a string, find out if it satisfies the IPv4 address naming rules.
     */
    boolean isIPv4Address(String inputString) {
        String[] pieces = inputString.split("\\.");
        for (String piece : pieces) {
            try {
                if (Integer.parseInt(piece) == 0) {
                    if (piece.length() > 1) {
                        return false;
                    }
                } else {
                    if ((int) piece.charAt(0) == 48) {
                        return false;
                    }
                }
                int num = Integer.parseInt(piece);
                if (pieces.length != 4 || num > 255) return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
}
