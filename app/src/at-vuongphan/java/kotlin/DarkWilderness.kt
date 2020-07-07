object DarkWilderness {
    private const val ACI = 48
    @JvmStatic
    fun main(args: Array<String>) {
        /**
         *Caring for a plant can be hard work, but since you tend to it regularly, you have a plant that grows consistently. Each day, its height increases by a fixed amount represented by the integer upSpeed. But due to lack of sunlight,
         *the plant decreases in height every night, by an amount represented by downSpeed.
         */
        println("38.Result:${growingPlant(100,10,910)}")
        /**
         *You found two items in a treasure chest! The first item weighs weight1 and is worth value1, and the second item weighs weight2 and is worth value2. What is the total maximum value of the items you can take with you,
         *  assuming that your max weight capacity is maxW and you can't come back for the items later?
         */
        println("39.Result:${knapsackLight(10,5,6,4,8)}")
        /**
         *Let's define digit degree of some positive integer as the number of times we need to
         * replace this number with the sum of its digits until we get to a one digit number.
         */
        println("41.Result:${digitDegree(100)}")
        /**
         *Given the positions of a white bishop and a black pawn on the standard chess board, determine whether
         *  the bishop can capture the pawn in one move.
         */
        println("42.Result:${bishopAndPawn("a1","c3")}")
    }
    private fun bishopAndPawn(bishop: String, pawn: String):Boolean{
        return ((bishop[0].toInt()+bishop[1].toInt())==(pawn[0].toInt()+ pawn[1].toInt()))||(Math.abs(bishop[0].toInt()-bishop[1].toInt())== (Math.abs(pawn[0].toInt()-pawn[1].toInt())))
    }
    private fun digitDegree(n: Int): Int {
        var s = n.toString()
        var sum = 0
        var count = 0
        print(s.length)
        while(s.length>1){
            for(i in 0 .. s.length-1){
                sum = sum + (s[i].toInt()- ACI)
            }
            s = sum.toString()
            count++
            sum = 0
        }
        print(count)
        return count
    }
    private fun knapsackLight(value1: Int, weight1: Int, value2: Int, weight2: Int, maxW: Int)=
            when {
                weight1 + weight2 <= maxW -> value1 + value2
                (value1 > value2 || weight2 > maxW) && weight1 <= maxW  -> value1
                weight2 <= maxW -> value2
                else -> 0
            }
    private fun growingPlant(upSpeed: Int, downSpeed: Int, desiredHeight: Int): Int {
        var up = upSpeed
        var day =1
        var night = upSpeed - downSpeed
        while(up < desiredHeight){
            up = up +night
            day++
        }
        return day
    }
}