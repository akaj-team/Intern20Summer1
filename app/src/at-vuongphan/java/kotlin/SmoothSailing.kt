object SmoothSailing {
    @JvmStatic
    fun main(args: Array<String>) {
        /**
         *Given an array of strings, return another array containing all of its longest strings.
         */
        val input9 = mutableListOf("aba","aa","ad","vcd","aba")
        println("9.Result: ${allLongestStrings(input9)}")
        /**
         * Given two strings, find the number of common characters between them.
         */
        println("10.Result: ${commonCharacterCount("aabcc","adcaa")}")
        /**
         * Ticket numbers usually consist of an even number of digits.
         * A ticket number is considered lucky if the sum of the first half of the digits is equal to the sum of the second half.
         */
        println("11.Result:${isLucky(1230)}")
        /**
         * Some people are standing in a row in a park. There are trees between them which cannot be moved. Your task is to rearrange
         * the people by their heights in a non-descending order without moving the trees. People can be very tall!
         */
        var input12 = mutableListOf(-1,120,110,-1,100,90)
        println("12.Result:${sortByHeight(input12)}")
        /**
         * Write a function that reverses characters in (possibly nested) parentheses in the input string.
         */
        println("13.Result:${reverseInParentheses("(bar)")}")
    }

    private fun reverseInParentheses(inputString: String): String {
        var result = ""
        result = Regex("""\([a-z]*\)""").replace(inputString) {
            it.value.reversed().replace(")","").replace("(","")
        }
        return if (result.contains("(")){
            reverseInParentheses(result)
        } else {
            if (result.isEmpty()) result = ""
            result
        }
    }

    private fun sortByHeight(a: MutableList<Int>): MutableList<Int> {
        var tmp : Int =0
        for(i in a.indices){
            for(j in a.indices){
                if(a[i] == -1){
                    continue;
                }else{
                    if(a[j] == -1){
                        continue;
                    }else{
                        if(a[i]<a[j]){
                            tmp = a[i]
                            a[i] = a[j]
                            a[j] = tmp
                        }
                    }
                }
            }
        }
        return a
    }

    private fun isLucky(n: Int): Boolean {
        val s = n.toString()
        val m = s.length /2
        var sum1 = 0
        var sum2 = 0
        for(i in 0 until m){
            sum1 = sum1 + s[i].toInt()
        }
        for(i in m until s.length){
            sum2 = sum2 + s[i].toInt()
        }
        return sum1 == sum2
    }

    private fun commonCharacterCount(stringOne: String, stringTwo: String): Int {
        var count = 0
        var string = stringTwo
        for (c in stringOne.toCharArray())
        {
            for (i in 0 until string.length)
            {
                if (c == string[i])
                {
                    ++count
                    string = string.removeRange(i, i+1)
                    break
                }
            }
        }
        return count
    }

    private fun allLongestStrings(inputArray: MutableList<String>): MutableList<String> {
        var maxLength = 1
        var arrayList = mutableListOf<String>()
        for(i in inputArray.indices){
            if(inputArray[i].length>maxLength){
                maxLength = inputArray[i].length
            }
        }
        for( i in inputArray.indices){
            if(inputArray[i].length == maxLength){
                arrayList.add(inputArray[i])
            }
        }
        return arrayList
    }
}
