object ThroughtheFog {
    private const val ZERO = 0
    private const val TWO = 2
    private const val DIV = 100
    private const val ONE = 1
    private const val THREE = 3
    @JvmStatic
    fun main(args: Array<String>) {
        /**
         * Consider integer numbers from 0 to n - 1 written down along the circle in such a way that the distance between
         * any two neighboring numbers is equal (note that 0 and n - 1 are neighboring, too).
         */
        println("30.Result:${circleOfNumbers(10,2)}")
        /**
         *You have deposited a specific amount of money into your bank account. Each year your balance increases at the same growth rate. With the assumption that you don't
         * make any additional deposits, find out how long it would take for your balance to pass a specific threshold.
         */
        println("31.Result:${depositProfit(100,20,170)}")
        /**
         * Given a sorted array of integers a, your task is to determine which element of a is closest to all other values of a.
         * In other words, find the element x in a, which minimizes the following sum:
         */
        println("32.Result:${absoluteValuesSumMinimization(mutableListOf(2,4,7))}")
        /**
         * Given an array of equal-length strings, you'd like to know if it's possible to rearrange the order of the elements in such a way that
         * each consecutive pair of strings differ by exactly one character. Return true if it's possible, and false if not.
         */
        println("33.Result:${stringsRearrangement(mutableListOf("aba","bbb","bab"))}")

    }
    private fun stringsRearrangement(inputArray: MutableList<String>): Boolean {
        val permutations = permutations(inputArray)
        val twoStringSeparatedByOneChar = fun(s1: String, s2: String): Boolean {
            return s1.zip(s2).count { it.first != it.second } == 1
        }
        return permutations.any {
            (0 until it.size -1).all { i -> twoStringSeparatedByOneChar(it[i], it[i+1]) }
        }
    }

    fun <T> permutations(lst: List<T>): List<List<T>>{
        if(lst.size < TWO) return listOf(lst)
        var perms = mutableListOf<MutableList<T>>()
        val fElem = lst[0]
        val remPerms = permutations(lst.drop(1))
        for (permutation in remPerms) {
            for (i in 0 .. permutation.size) {
                val mutableList = permutation.toMutableList()
                mutableList.add(i, fElem)
                perms.add(mutableList)
            }
        }
        return perms
    }
    private fun absoluteValuesSumMinimization(a: MutableList<Int>) : Int
    {
        return a[(a.size-1) / TWO]
    }
    private fun depositProfit(deposit: Int, rate: Int, threshold: Int): Int {
        var year = 0;
        var sum = deposit.toDouble();
        while (sum < threshold) {
            sum = sum + (sum * rate / DIV);
            year++;
        }
        return year;
    }
    private fun circleOfNumbers(n: Int, firstNumber: Int) :Int{
        return (firstNumber + n/ TWO) % n
    }
}
