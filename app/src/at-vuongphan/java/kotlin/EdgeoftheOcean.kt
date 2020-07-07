object EdgeoftheOcean {
    @JvmStatic
    fun main(args: Array<String>) {
        /**
         *Given an array of integers, find the pair of adjacent elements that has the largest product and return that product.
         */
        val inputArray = mutableListOf(3,6,-2,-5,7,3)
        println("4.Result: ${adjacentElementsProduct(inputArray)}")
        /**
         * Below we will define an n-interesting polygon. Your task is to find the area of a polygon for a given n.
         */
        println("5.Result: ${shapeArea(4)}")
        /**
         * Ratiorg got statues of different sizes as a present from CodeMaster for his birthday, each statue having an non-negative integer size. Since he likes to make t
         * hings perfect, he wants to arrange them from smallest to largest so that each statue will be bigger than the previous one exactly by 1. He may need some additional statues to be
         * able to accomplish that. Help him figure out the minimum number of additional statues needed.
         */
        val input6 = mutableListOf(6,2,3,8)
        println("6.Result: ${makeArrayConsecutive2(input6)}")
        /**
         * Given a sequence of integers as an array, determine whether it is possible to obtain a
         * strictly increasing sequence by removing no more than one element from the array.
         */
        val input7 = mutableListOf(1,3,2,1)
        println("7.Result:${almostIncreasingSequence(input7)}")
        /**
         * After becoming famous, the CodeBots decided to move into a new building together. Each of the rooms has a different cost, and some of them are free, but there's a rumour that all the free rooms are haunted! Since the CodeBots are quite superstitious,
         * they refuse to stay in any of the free rooms, or any of the rooms below any of the free rooms.
         */
        val input8 : MutableList<MutableList<Int>> = mutableListOf(mutableListOf(0,1,1,2), mutableListOf(0,5,0,0), mutableListOf(2,0,3,3))
        println("8.Result:${matrixElementsSum(input8)}")
    }
    private fun matrixElementsSum(matrix: MutableList<MutableList<Int>>): Int {
        var sum = 0
        for( i in matrix.indices){
            for(j in matrix[i].indices){
                if(matrix[i][j]==0){
                    for(k in matrix.indices){
                        matrix[k][j]=0
                    }
                }
                sum = sum + matrix[i][j]
            }
        }
        return sum
    }

    private fun almostIncreasingSequence(sequence: MutableList<Int>): Boolean {
        var check1 = 0
        var check2 = 0
        if(sequence.size == 2){
            return true;
        }
        for(index in 0 until sequence.size-1){
            if(sequence[index]>= sequence[index+1]){
                check1 +=1;
            }

        }
        for(index in 0 until  sequence.size-2 ){
            if(sequence[index]>= sequence[index+2]){
                check2 +=1;
            }

        }
        return if(check1 > 1){
            false;
        }else check2 <= 1
    }

    private fun adjacentElementsProduct(inputArray: MutableList<Int>): Int {
        var result = inputArray[0] * inputArray[1]
        var tmp :Int
        for( index in 0 until (inputArray.size-1)){
            tmp = inputArray[index] * inputArray[index+1]
            if(result < tmp){
                result = tmp
            }
        }
        return result;
    }

    private fun shapeArea(n: Int): Int {
        return (n*n)+((n-1)*(n-1))
    }
    private fun makeArrayConsecutive2(statues: MutableList<Int>): Int {
        statues.sort()
        var result = (statues[statues.size-1]-statues[0])-statues.size + 1
        return result
    }
}
