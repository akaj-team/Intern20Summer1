object IslaclsndofKnowledge {
    private const val DIV = 9
    private const val CHARTEST ='0'
    private const val NUMBERSTART = 0
    private const val NUMBEREND = 255
    private const val DIVIP = 4
    @JvmStatic
    fun main(args: Array<String>) {
        /**
         * Call two arms equally strong if the heaviest weights they each are able to lift are equal.

        Call two people equally strong if their strongest arms are equally strong
        (the strongest arm can be both the right and the left), and so are their weakest arms.
         */
        println("19.Result:${areEquallyStrong(10,15,15,10)}")
        /**
         * Given an array of integers, find the maximal absolute difference between any two of its adjacent elements.
         */
        println("20.Result:${arrayMaximalAdjacentDifference(mutableListOf(2,4,1,0))}")
        /**
         *An IP address is a numerical label assigned to each device (e.g., computer, printer) participating in a computer network that uses the Internet Protocol for communication.
         * There are two versions of the Internet protocol, and thus two versions of addresses. One of them is the IPv4 address.
         */
        println("21.Result: ${isIPv4Address("192.168.1.10")}")
        /**
         * You are given an array of integers representing coordinates of obstacles situated on a straight line.

         * Assume that you are jumping from the point with coordinate 0 to the right.
         * You are allowed only to make jumps of the same length represented by some integer.
         */
        println("22.Result: ${avoidObstacles(mutableListOf(5,3,6,7,9))}")
        /**
         *Last night you partied a little too hard. Now there's a black and white photo of you that's about to go viral!
         * You can't let this ruin your reputation, so you want to apply the box blur algorithm to the photo to hide its content.
         */
        val input23 : MutableList<MutableList<Int>> = mutableListOf(mutableListOf(1,1,1), mutableListOf(1,7,1), mutableListOf(1,1,1))
        println("23.Result:${boxBlur(input23)}")
    }
    private fun boxBlur(image: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
        var result : MutableList<MutableList<Int>> = mutableListOf()
        for(i in 0 until image.size -1){
            for(j in 0 until image[0].size-1){
                var sum = 0
                for (ii in i - 1 until i + 1) {
                    for ( jj in  j - 1 until  j + 1) {
                        sum += image[ii][jj];
                    }
                }
                result[i - 1][j - 1] = sum / DIV
            }
        }
        return result
    }
    private fun avoidObstacles(inputArray: MutableList<Int>): Int {
        var result =1
        var jump = false
        while(true){
            jump = true
            for(i in inputArray.indices){
                if(inputArray[i] % result ==0){
                    jump = false
                }
            }
            if(jump){
                return result
            }
            result ++
        }
    }
    private fun isIPv4Address(inputString: String): Boolean {
        var tokens = inputString.split(".")
        if(tokens.size != DIVIP){
            return false
        }
        for(i in 0 until tokens.size){
            if(tokens[i].length > 1 && tokens[i].get(0) == CHARTEST){
                return false;
            }
        }
        if(!tokens.all { it.toIntOrNull() in NUMBERSTART..NUMBEREND }){
            return false
        }
        return true
    }
    private fun areEquallyStrong(yourLeft: Int, yourRight: Int, friendsLeft: Int, friendsRight: Int): Boolean {
        return setOf(yourLeft,yourRight) == setOf(friendsLeft,friendsRight)
    }
    private fun arrayMaximalAdjacentDifference(inputArray: MutableList<Int>): Int {
        var subNext = 0;
        var check = 0;
        for (i in 0 until inputArray.size-1) {
            check=Math.abs(inputArray[i]-inputArray[i+1]);
            if (check > subNext) {
                subNext = check
            }

        }
        return subNext;
    }
}
