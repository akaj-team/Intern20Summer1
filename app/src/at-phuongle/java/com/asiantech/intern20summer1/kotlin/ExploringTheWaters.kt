package com.asiantech.intern20summer1.kotlin

object ExploringTheWaters {
    fun alternatingSums(a : IntArray) : IntArray {
        var aSums : IntArray = IntArray(2)

        for (i in 0 until a.size) {
            if (i % 2 == 0) {
                aSums[0] += a[i];
            } else {
                aSums[1] += a[i];
            }
        }
        return aSums;
    }

    fun addBorder(picture : Array<String>) : Array<String?> {
        var borderRow : Int = picture.size + 2
        var borderCol : Int = picture[0].length + 2
        var newPicture : Array<String?> = arrayOfNulls<String>(borderRow)
        var tmp : String = ""

        for (i in 0 until newPicture.size) {
            if (i == 0 || i == borderRow - 1) {
                for (j in 0 until borderCol) {
                    tmp += "*"
                }
                newPicture[i] = tmp
                tmp = ""
            } else {
                tmp = "*"
                tmp += picture[i - 1]
                tmp += "*"
                newPicture[i] = tmp
                tmp = ""
            }
        }
        return newPicture;
    }

    class Position{
        public var position : Int
        public var aElement : Int
        public var bElement : Int

        constructor(pos : Int, a : Int, b : Int) {
            this.position = pos;
            this.aElement = a;
            this.bElement = b;
        }
    }

    fun areSimilar(A : Array<Int>, B : Array<Int>) : Boolean {
        var mismatchedPosition : MutableList<Position> = mutableListOf()

        for(i in 0 until A.size){
            var a : Int = A[i]
            var b : Int = B[i]

            if(a != b){
                var p = Position(i, a, b);
                mismatchedPosition.add(p);
            }
        }

        if(mismatchedPosition.size == 0)
            return true;
        else if(mismatchedPosition.size == 2){
            var pos1 : Position = mismatchedPosition.get(0);
            var pos2 : Position = mismatchedPosition.get(1);

            var aPos1 : Int = pos1.aElement;
            var bPos1 : Int = pos1.bElement;

            var aPos2 : Int = pos2.aElement;
            var bPos2 : Int = pos2.bElement;

            if(aPos1 == bPos2 && bPos1 == aPos2)
                return true;
            else
                return false;
        }
        else
            return false;
    }

    fun arrayChange(inputArray : Array<Int>) : Int {
        var result : Int = 0

        for (i in 1 until inputArray.size) {
            if (inputArray[i - 1] >= inputArray[i]) {
                var difference : Int = Math.abs(inputArray[i - 1] - inputArray[i]) + 1
                inputArray[i] += difference
                result += difference
            }
        }

        return result
    }

    fun palindromeRearranging(inputString : String) : Boolean {
        var list : MutableList<Char> = mutableListOf()

        for (i in 0 until inputString.length) {
            if (list.contains(inputString[i])) {
                list.remove(inputString[i]);
            } else {
                list.add(inputString[i]);
            }
        }

        if (list.size == 0 || list.size == 1) {
            return true;
        } else {
            return false;
        }
    }

}
