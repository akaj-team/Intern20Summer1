package com.asiantech.intern20summer1.kotlin

object SmoothSailing {
    fun allLongestStrings(inputArray: Array<String>): Array<String> {
        var longestLength: Int = 0
        var longestStrings: MutableList<String> = mutableListOf();
        for (i in 0 until inputArray.size) {
            if (inputArray[i].length > longestLength) {
                longestLength = inputArray[i].length
            }
        }

        for (i in 0 until inputArray.size) {
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
        for (i in 0 until charsArray2.size) {
            listChar2.add(charsArray2[i]);
        }

        for (i in 0 until charsArray1.size) {
            for (j in 0 until listChar2.size) {
                if (charsArray1[i] == listChar2.get(j)) {
                    count++;
                    listChar2.removeAt(j);
                    break;
                }
            }
        }

        return count;
    }

    fun isLucky(n: Int): Boolean {
        var number: Int = n
        var numbers: MutableList<Int> = mutableListOf()
        var sum1: Int = 0
        var sum2: Int = 0
        while (number > 0) {
            numbers.add(number % 10)
            number /= 10
        }

        if ((numbers.size % 2) != 0) {
            return false
        }

        for (i in 0 until (numbers.size / 2)) {
            sum1 += numbers.get(i)
            sum2 += numbers.get(numbers.size - i - 1)
        }

        if (sum1 != sum2) {
            return false
        }

        return true
    }

    fun sortByHeight(a: Array<Int>): Array<Int> {
        var numbers: MutableList<Int> = mutableListOf()

        for (i in 0 until a.size) {
            if (a[i] != -1) {
                numbers.add(a[i])
            }
        }

        numbers.sort()

        for (i in 0 until a.size) {
            if (a[i] != -1) {
                a[i] = numbers.get(0);
                numbers.removeAt(0);
            }
        }

        return a;
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
            if (s[i] == '(')
                n++;
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
                tmpCh = tmpCh + s[j];
                j++;
            }
            for (q in (tmpCh.length - 1) downTo 0)
                tmpChRe = tmpChRe + tmpCh[q];
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
