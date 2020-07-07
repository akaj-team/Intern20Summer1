object LandofLogic {
    @JvmStatic
    fun main(args: Array<String>) {
        /**
         * Define a word as a sequence of consecutive English letters. Find the longest word from the given string.
         */
        println("52.Result:${longestWord("Ready[[[, steady, go!")}")
        /**
         * Check if the given string is a correct time representation of the 24-hour clock.
         */
        println("53.Result:${validTime("13:58")}")
        /**
         * CodeMaster has just returned from shopping. He scanned the check of the items he bought and gave the resulting string to Ratiorg to figure out the total number of purchased items. Since Ratiorg is a bot he is definitely
         * going to automate it, so he needs a program that sums up all the numbers which appear in the given input
         */
        println("54.Result:${sumUpNumbers("2 apples, 12 oranges")}")
        /**
         * Given a rectangular matrix containing only digits, calculate the number of different 2 × 2 squares in it.
         */
        println("55.Result:${differentSquares(mutableListOf(mutableListOf(1,2,1), mutableListOf(2,2,2), mutableListOf(2,2,2), mutableListOf(1,2,3), mutableListOf(2,2,1)))}")
        /**
         * Given an integer product, find the smallest positive (i.e. greater than 0) integer the product of whose digits is equal to product. If there is no such integer, return -1 instead.
         */
        println("56.Result:${digitsProduct(12)}")
        /**
         *You are given an array of desired filenames in the order of their creation. Since two files cannot have equal names, the one which comes later will have an addition to its name in a form of (k),
         * where k is the smallest positive integer such that the obtained name is not used yet.
         */
        println("57.Result:${fileNaming(mutableListOf("doc","doc","image","doc(1)"))}")
        /**
         * You are taking part in an Escape Room challenge designed specifically for programmers. In your efforts to find a clue, you've found a binary code written on the wall behind a vase, and realized that it must be an encrypted message. After some thought, your first guess is that
         * each consecutive 8 bits of the code stand for the character with the corresponding extended ASCII code.
         */
        println("58.Result:${messageFromBinaryCode("010010000110010101101100011011000110111100100001")}")
        /**
         *Construct a square matrix with a size N × N containing integers from 1 to N * N in a spiral order, starting from top-left and in clockwise direction.
         */
        println("59.Result:${spiralNumbers(4)}")
    }
    private fun spiralNumbers(n: Int):  MutableList<MutableList<Int>>  {
        val numbers = MutableList(n) { MutableList(n) { 0 } }
        var count = 1
        var index = 0

        while (count  <= n*n) {
            for (subIndex in index until n - index) {
                numbers[index][subIndex] = count
                count++
            }
            for (subIndex in index + 1 until n - index) {
                numbers[subIndex][n - 1 - index] = count
                count++
            }
            for (subIndex in n - 2 - index downTo index) {
                numbers[n - 1 - index][subIndex] = count
                count++
            }
            for (subIndex in n - 2 - index downTo index + 1) {
                numbers[subIndex][index] = count
                count++
            }
            index++
        }
        return numbers
    }
    private fun messageFromBinaryCode(code: String): String {
        var codes = StringBuilder()
        for(i in 0 until code.length step 8){
            val sub = code.substring(i,i+8)
            val num = sub.toInt(2)
            val character = num.toChar()
            codes.append(character)
        }
        return codes.toString()
    }
    private fun fileNaming(names: MutableList<String>): MutableList<String> {
        var n = mutableListOf<String>()
        for(i in 0 until names.size){
            var count =0
            var s = names[i]
            while (n.contains(s)){
                count++
                s=names[i]+"(" +count.toString() +")"
            }
            n.add(s)
        }
        return n
    }
    private fun digitsProduct(product: Int): Int {
        var product = product
        if (product == 0) {
            return 10
        }
        var b = ""
        for (i in 9 downTo 2) {
            while (product % i == 0) {
                product = product / i
                b = i.toString() + b
            }
        }
        return if (product > 1) {
            -1
        } else b.toInt()
    }
    private fun differentSquares(matrix: MutableList<MutableList<Int>>): Int {
        var s = mutableSetOf<String>()
        var row =matrix.size
        var col = matrix[0].size
        if( row ==1 || col == 1){
            return 0
        }
        for(i in 0 until row-1){
            for(j in 0 until col-1){
                var sb =StringBuilder()
                sb.append(matrix[i][j]).append(matrix[i][j+1])
                        .append(matrix[i+1][j]).append(matrix[i+1][j+1])
                s.add(sb.toString())
            }
        }
        return s.size
    }
    private fun sumUpNumbers(inputString: String): Int {
        val result = inputString.replace("[^0-9 ]".toRegex(), " ")
        val numberString = result.split(" ".toRegex()).toTypedArray()
        var sum = 0
        for (i in numberString.indices) {
            if (numberString[i].isEmpty()) {
                continue
            }
            sum = sum + numberString[i].toInt()
        }
        return sum
    }
    private fun validTime(time: String): Boolean{
        return time.matches("""([01]?[0-9]|2[0-3]):[0-5][0-9]""".toRegex())
    }
    private fun longestWord(text: String): String? {
        val input = text.replace("[^a-zA-Z]".toRegex(), " ")
        val arrResult = input.split(" ".toRegex()).toTypedArray()
        var maxLength = 0
        var result = ""
        for (i in arrResult.indices) {
            if (arrResult[i].length > maxLength) {
                maxLength = arrResult[i].length
                result = arrResult[i]
            }
        }
        return result
    }
}