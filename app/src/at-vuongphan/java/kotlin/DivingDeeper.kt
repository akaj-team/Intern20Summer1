object DivingDeeper {
    @JvmStatic
    fun main(args: Array<String>) {
        /**
         * Given array of integers, remove each kth element from it.
         */
        println("34.Result:${extractEachKth(mutableListOf(1,2,3,4,5,6,7,8,9,10),3)}")
        /**
         *Find the leftmost digit that occurs in a given string.
         */
        println("35.Result:${firstDigit("q2q-q")}")
        /**
         *Given a string, find the number of different characters in it.
         */
        println("36.Result:${differentSymbolsNaive("cabca")}")
        /**
         *Given array of integers, find the maximal possible sum of some of its k consecutive elements.
         */
        println("37.Result:${arrayMaxConsecutiveSum(mutableListOf(2,3,5,1,6),2)}")
    }
    fun arrayMaxConsecutiveSum(arr: MutableList<Int>, k: Int): Int {
        var max : Int = 0
        var sum : Int = 0
        var count = 1
        for(i in 0 until arr.size ){
            sum = arr[i]
            while(count < k ){
                sum = sum + arr[i + count]
                count++
            }
            count = 1
            if(sum > max){
                max = sum
            }
        }
        return max
    }
    fun differentSymbolsNaive(s: String): Int {
        var a = mutableSetOf<Char>()
        for (i in 0 until s.length) {
            a.add(s.get(i));
        }
        return a.size;
    }
    fun firstDigit(n: String): Char {
        var c : CharArray = n.toCharArray()
        var result : Char = ' '
        for(i in 0 until c.size)  {
            if(c.get(i).isDigit()){
                result = c[i]
                break
            }
        }
        return result
    }
    fun extractEachKth(inputArray: MutableList<Int>, k: Int): MutableList<Int> {
        var length = inputArray.size - (inputArray.size / k)
        var array = mutableListOf<Int>()
        var j = 0
        if( j < length){
            for(i in 0 until inputArray.size){
                if((i+1) % k !=0){
                    array.add(inputArray[i])
                    j++
                }
            }
        }
        return array
    }
}
