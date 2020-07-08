package com.asiantech.intern20summer1.kotlin

object IslandOfKnowledge {
    fun areEquallyStrong(yourLeft : Int, yourRight : Int, friendsLeft : Int, friendsRight : Int) : Boolean {
        var myHeaviestWeights : Int
        var friendsHeaviestWeights : Int
        var mySum : Int = yourLeft + yourRight
        var friendsSum : Int = friendsLeft + friendsRight

        if (yourLeft > yourRight) {
            myHeaviestWeights = yourLeft
        } else {
            myHeaviestWeights = yourRight
        }

        if (friendsLeft > friendsRight) {
            friendsHeaviestWeights = friendsLeft
        } else {
            friendsHeaviestWeights = friendsRight
        }

        if (mySum == friendsSum) {
            if(myHeaviestWeights == friendsHeaviestWeights) {
                return true;
            }
        }
        return false;
    }

    fun arrayMaximalAdjacentDifference(inputArray : Array<Int>) : Int {
        var maxDiff : Int = 0;

        for (i in 0 until inputArray.size - 1) {
            var diff : Int = Math.abs(inputArray[i] - inputArray[i+1]);
            if (maxDiff < diff) {
                maxDiff = diff;
            }
        }

        return maxDiff;
    }

    fun isIPv4Address(inputString : String) : Boolean {
        var check : Boolean = true
        var regex : String = "[.]"
        var numbers : Array<String> = inputString.split(regex.toRegex()).toTypedArray()

        if (numbers.size != 4) {
            check = false
        } else {
            for (i in numbers.indices) {
                try {
                    if ((numbers[i].length == 2) && Integer.parseInt(numbers[i]) < 10) {
                        check = false
                    }
                    if (numbers[i].isNullOrEmpty()) {
                        check = false
                    }
                    if(Integer.parseInt(numbers[i]) < 0 || Integer.parseInt(numbers[i]) > 255) {
                        check = false
                    }
                } catch (ex1 : IndexOutOfBoundsException) {
                    check = false
                }
            }
        }

        return check
    }

    fun avoidObstacles(inputArray : Array<Int>) : Int {
        var check : Boolean = false
        var count : Int = 1
        while (true) {
            for (i in 0 until inputArray.size) {
                if (inputArray[i] % count != 0) {
                    check = true
                } else {
                    check = false
                    break
                }
            }

            if (check == true) {
                break
            }

            count++;
        }

        return count
    }


}
