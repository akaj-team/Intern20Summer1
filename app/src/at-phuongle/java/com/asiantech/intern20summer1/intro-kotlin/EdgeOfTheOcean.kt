package com.asiantech.intern20summer1.`intro-kotlin`

object EdgeOfTheOcean {
    fun adjacentElementsProduct(inputArray : Array<Int>) : Int {
        var size : Int = inputArray.size;
        var n : Int = inputArray.get(0) * inputArray.get(1);
        for (i in 1 until (size-1)) {
            var j : Int = i + 1;
            var temp : Int = inputArray.get(i) * inputArray.get(j)
            if (temp > n) {
                n = inputArray.get(i) * inputArray.get(j);
            }
        }
        return n;
    }

    fun shapeArea(n : Int) : Int {
        return n * n + (n - 1) * (n - 1);
    }

    fun sortASC(arr : Array<Int>) : Unit {
        var temp : Int = arr[0];
        for (i in 0 until (arr.size - 1)) {
            for (j in (i+1) until (arr.size)) {
                if (arr[i] > arr[j]) {
                    temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }
    }

    fun makeArrayConsecutive2(statues : Array<Int>) : Int {
        var n : Int = 0;

        sortASC(statues);

        for (i in 0 until (statues.size-1)) {
            n += (statues[i + 1] - statues[i] - 1);
        }

        return n;
    }

    fun almostIncreasingSequence(sequence : Array<Int>) : Boolean {
        var n : Int = 0;

        for(i in 0 until (sequence.size - 1)){

            if(sequence[i] - sequence[i+1] >= 0){
                n++;
                if(i - 1 >= 0 && i + 2 <= sequence.size - 1
                    && sequence[i] - sequence[i+2] >= 0
                    && sequence[i-1] - sequence[i+1] >= 0){
                    return false;
                }
            }
        }

        return n <= 1;
    }

}