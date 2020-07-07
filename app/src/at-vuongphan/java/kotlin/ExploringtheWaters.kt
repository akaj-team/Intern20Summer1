object ExploringtheWaters {
    private const val THREE = 3
    @JvmStatic
    fun main(args: Array<String>) {
        /**
         *Several people are standing in a row and need to be divided into two teams. The first person goes into team 1,
         *  the second goes into team 2, the third goes into team 1 again, the fourth into team 2, and so on.
         */
        val input14 = mutableListOf(50,60,60,45,70)
        println("14.Result:${alternatingSums(input14)}")
        /**
         * Given a rectangular matrix of characters, add a border of asterisks(*) to it.
         */
        val input15 = mutableListOf("abc","ded")
        println("15.Result: ${addBorder(input15)}")
        /**
         * Two arrays are called similar if one
         * can be obtained from another by swapping at most one pair of elements in one of the arrays.
         */
        println("16.Result: ${areSimilar(mutableListOf(1,2,3), mutableListOf(2,1,3))}")
        /**
         * You are given an array of integers. On each move you are allowed to increase exactly one of its element by one.
         * Find the minimal number of moves required to obtain a strictly increasing sequence from the input.
         */
        val input17 = mutableListOf(2,1,10,1)
        println("17.Result:${arrayChange(input17)}")
        /**
         * Given a string, find out if its characters can be rearranged to form a palindrome.
         */
        println("18.Result:${palindromeRearranging("aabbc")}")
    }

    private fun palindromeRearranging(inputString: String): Boolean {
        var oddCounter = 0

        val letterSet = inputString.toCharArray().toSet()

        for(char in letterSet){
            if (inputString.count{ it == char} % 2 == 1){
                oddCounter++
            }
        }

        return oddCounter < 2
    }

    private fun arrayChange(inputArray: MutableList<Int>): Int {
        var count = 0
        for(i in 0 until inputArray.size - 1){
            if(inputArray[i] >= inputArray[i+1]){
                do{
                    inputArray[i+1] +=1
                    count++
                }while(inputArray[i] >= inputArray[i+1])
            }
        }
        return count
    }

    private fun areSimilar(a: MutableList<Int>, b: MutableList<Int>): Boolean {
        var size = a.size - 1
        var result = true
        var count = 0

        for (i in 0 .. size) {
            if (a[i] != b[i]) {
                count++
                if (count == THREE) {
                    result = false
                    break
                }
            }
        }

        if (result) {
            var newA = a.sorted()
            var newB = b.sorted()
            val pairList = newA.zip(newB)

            result = pairList.all { (elt1, elt2) ->
                elt1 == elt2
            }
        }

        return result
    }

    private fun addBorder(picture: MutableList<String>): MutableList<String> {
        var border = picture
        var length = picture[0].length + 2
        var bar = ""
        for (i in 1 .. length) {
            bar += "*"
        }
        for ((index, line) in border.withIndex()) {
            border[index] = "*$line*"
        }

        border.add(0, bar)
        border.add(bar)

        return border
    }

    private fun alternatingSums(a: MutableList<Int>): MutableList<Int> {
        var odd = 0
        var even = 0
        for(i in a.indices){
            if(i % 2 == 0){
                odd +=a[i]
            }else{
                even +=a[i]
            }
        }
        return mutableListOf(odd,even)
    }
}
