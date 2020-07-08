package com.asiantech.intern20summer1.kotlin

object SmoothSailing {
    private const val TEN = 10

    fun allLongestStrings(inputArray: Array<String>): Array<String> {
        var longestLength: Int = 0
        var longestStrings: MutableList<String> = mutableListOf();
        for (i in inputArray.indices) {
            if (inputArray[i].length > longestLength) {
                longestLength = inputArray[i].length
            }
        }

        for (i in inputArray.indices) {
            if (inputArray[i].length == longestLength) {
                longestStrings.add(inputArray[i])
            }
        }
        return longestStrings.toTypedArray()
    }

    fun commonCharacterCount(s1: String, s2: String): Int {
        var count: Int = 0;
        var charsArray1: CharArray = s1.toCharArray();
        var charsArray2: CharArray = s2.toCharArray();
        var listChar2: MutableList<Char> = mutableListOf();
        for (element in charsArray2) {
            listChar2.add(element);
        }

        for (element in charsArray1) {
            for (j in 0 until listChar2.size) {
                if (element == listChar2.get(j)) {
                    count++;
                    listChar2.removeAt(j);
                    break;
                }
            }
        }

        return count;
    }

    fun isLucky(n: Int): Boolean {
        var check: Boolean = true
        var number: Int = n
        var numbers: MutableList<Int> = mutableListOf()
        var sum1: Int = 0
        var sum2: Int = 0

        while (number > 0) {
            numbers.add(number % TEN)
            number /= TEN
        }

        if ((numbers.size % 2) != 0) {
            check = false
        }

        for (i in 0 until (numbers.size / 2)) {
            sum1 += numbers[i]
            sum2 += numbers[numbers.size - i - 1]
        }

        if (sum1 != sum2) {
            check = false
        }

        return check
    }

    fun sortByHeight(a: Array<Int>): Array<Int> {
        var numbers: MutableList<Int> = mutableListOf()

        for (i in a.indices) {
            if (a[i] != -1) {
                numbers.add(a[i])
            }
        }

        numbers.sort()

        for (i in a.indices) {
            if (a[i] != -1) {
                a[i] = numbers.get(0)
                numbers.removeAt(0)
            }
        }

        return a
    }

    fun reverseInParentheses(string: String): String {
        var s: String = string
        var tmpCh: String = ""
        var tmpChRe: String = ""
        var tmp: String = ""
        var l: Int = s.length
        var n: Int = 0
        var j: Int = 0

        for (i in 0 until l) {
            if (s[i] == '(') {
                n++;
            }
        }

        var T: IntArray = IntArray(n)

        for (i in 0 until l) {
            if (s[i] == '(') {
                T[j] = i;
                j++;
            }
        }

        j = 0;

        while (n > 0) {
            j = T[n - 1] + 1;
            while (s[j] != ')') {
                tmpCh += s[j];
                j++;
            }
            for (q in (tmpCh.length - 1) downTo 0)
                tmpChRe += tmpCh[q];
            tmp = s.substring(0, T[n - 1]) + tmpChRe + s.substring(T[n - 1] + tmpChRe.length + 2);
            s = tmp;
            n--;
            tmp = "";
            tmpCh = "";
            tmpChRe = "";
        }

        return s;
    }

}
