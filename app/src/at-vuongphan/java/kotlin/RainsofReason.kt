object RainsofReason {
    const val ZERO = 0
    const val TOW = 2
    const val Z = 'Z'
    const val CHARACTERA = 'a'
    const val CHARACTERZ = 'z'
    const val AUPPER = 'A'
    @JvmStatic
    fun main(args: Array<String>) {
        /**
         * Given an array of integers, replace all the occurrences of elemToReplace with substitutionElem.
         */
        println("25.Result:${arrayReplace(mutableListOf(1,2,1),1,3)}")
        /**
         * Check if all digits of the given integer are even
         */
        println("26.Result:${evenDigitsOnly(248622)}")
        /**
         * Correct variable names consist only of English letters, digits and underscores and they can't start with a digit.
         * Check if the given string is a correct variable name.
         */
        println("27.Result:${variableName("qq-q")}")
        /**
         *Given a string, your task is to replace each of its characters by the next one in the
         * English alphabet; i.e. replace a with b, replace b with c, etc (z would be replaced by a).
         */
        println("28.Result:${alphabeticShift("crazy")}")
        /**
         * Given two cells on the standard chess board, determine whether they have the same color or not.
         */
        println("29.Result:${chessBoardCellColor("A1","C3")}")
    }
    private fun chessBoardCellColor(cell1: String, cell2: String)
            = (cell1[0].toInt()+cell1[1].toInt()) % TOW == (cell2[0].toInt()+cell2[1].toInt()) % TOW
    private fun alphabeticShift(inputString: String): String {
        var string : String =""
        for(i in 0 until inputString.length){
            if(inputString.get(i) == CHARACTERZ) {
                string += CHARACTERA
            } else if(inputString.get(i) == Z){
                string += AUPPER
            }else{
                string = string +(inputString.get(i)+1).toChar()
            }
        }
        return string
    }
    private fun variableName(name: String): Boolean {
        val regex = """[a-zA-Z_][0-9a-zA-Z_]*${'$'}""".toRegex()
        if (name.matches(regex)) {
            return true;
        }
        return false;
    }
    private fun evenDigitsOnly(n: Int): Boolean {
        var string = n .toString()
        for(i in 0 until string.length){
            if(!(string[i].toInt() % TOW == ZERO)){
                return false
            }
        }
        return true
    }
    private fun arrayReplace(inputArray: MutableList<Int>, elemToReplace: Int, substitutionElem: Int): MutableList<Int> {
        for(i in ZERO until inputArray.size){
            if(inputArray[i] == elemToReplace){
                inputArray[i] = substitutionElem
            }
        }
        return inputArray
    }
}
