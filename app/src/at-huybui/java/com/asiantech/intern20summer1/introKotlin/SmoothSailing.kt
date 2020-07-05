package com.asiantech.intern20summer1.introKotlin

class SmoothSailing {

    /** 9 **
     * Given an array of strings, return another array containing all of its longest strings.
     */
    fun allLongestStrings(inputArray: MutableList<String>): MutableList<String> {
        var lengMax = 0;
        inputArray.forEach {
            when {
                it.length > lengMax -> lengMax = it.length
            }
        }
        return inputArray.filter { it.length == lengMax } as MutableList<String>
    }

    /**
     * 10
     */
    fun commonCharacterCount(a: String, b: String): Int {
        return ('a'..'b').map { char -> //chạy ký tự từ a->z và dùng map quet các ký tự đó
            // dùng count để đếm số ký tự char ở trong chuỗi đầu vào
            // ta được 2 giá trị, đem đi so sánh 2 giá trị này thì giá trị này
            // giá trị nhỏ hơn chính là số ký tự trùng của ký tự char ở trong chuỗi
            // ta gán giá trị này vào map
            Math.min(a.count { it == char }, b.count { it == char })
        }.sum() // lấy tổng giá trị trong map ta được ký tự trùng
    }

    /**
     * 11
     */
    fun isLucky(n: Int): Boolean {
        val mStNumber = n.toString() // chuyển chuỗi về String
        /** cắt đôi chuỗi lấy được thành 2 chuỗi
         * sau đó dùng map quet từng ký tự của mỗi chuỗi rồi chuyển về Int sau đó cộng các số lại với nhau
         * so sánh tổng cắt được và trả về
         */
        return mStNumber.substring(0, mStNumber.length / 2).map { c -> c.toInt() }.sum() ==
                mStNumber.substring(mStNumber.length / 2).map { c -> c.toInt() }.sum()
    }

    /** 12
     * Some people are standing in a row in a park. There are trees between them which cannot be moved.
     * Your task is to rearrange the people by their heights in a non-descending order
     * without moving the trees. People can be very tall!
     */
    fun sortByHeight(a: MutableList<Int>): MutableList<Int> {
        val mArrayPeople =
            a.filter { it != -1 }.sorted().toMutableList()  // lọc mảng người ra khỏi a rồi sorted
        return a.map {// dùng map quyets mảng nếu = -1 thì đưa -1 vào map, nếu khác -1 thì đưa giá trị của mArrayPeople vào map
            when {
                it == -1 -> -1
                else -> mArrayPeople.removeAt(0) // việc này trả về giá trị đầu tiên của mArrayPeople và đổng thời xóa luôn nó
            }
        }.toMutableList()
    }

    /** 13 **
     *Write a function that reverses characters in (possibly nested) parentheses in the input string.
     * Input strings will always be well-formed with matching ()s.
     */
    fun reverseInParentheses(inputString: String): String {
        var mOutPut = inputString          // tạo biến đầu ra
        var Stack = mutableListOf<Int>() // tạo bộ nhớ stack lưu vị trí của ký tự '('
        inputString.forEachIndexed { index, c ->   // quét forEach với index
            when (c) {
                '(' -> {   // nếu ký tự là ')' thì lưu vị trí của nó vào stack
                    Stack.add(index)
                }
                ')' -> {  // Nếu ký tự là ')' thì lấy vị trí mới lưu và vị trí hiện tại để căt và lật chuỗi rỗi ghép lại
                    val indexS =
                        Stack.removeAt(Stack.size - 1)  // lấy vị trí đồng thời xóa vị trí đó
                    mOutPut = mOutPut.substring(0, indexS) + mOutPut.substring(indexS, index)
                        .reversed() + mOutPut.substring(index)
                }
            }
        }
        return mOutPut.filter { it != '(' && it != ')' }  // trả về chuỗi đã lọc hết ()
    }

}