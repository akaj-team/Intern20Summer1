package com.asiantech.intern20summer1.theCoreKotlin

class AttheCrossroads {
    /** 9 **
     * You are playing an RPG game. Currently your experience points (XP) total is equal to experience.
     * To reach the next level your XP should be at least at threshold.
     * If you kill the monster in front of you, you will gain more experience points in the amount of the reward.
     *Given values experience, threshold and reward, check if you reach the next level after killing the monster.
     */
    fun reachNextLevel(experience: Int, threshold: Int, reward: Int): Boolean {
        return experience + reward >= threshold
    }

    /** 10 **
     *You found two items in a treasure chest! The first item weighs weight1 and is worth value1,
     * and the second item weighs weight2 and is worth value2. What is the total maximum value
     * of the items you can take with you, assuming that your max weight capacity is maxW
     * and you can't come back for the items later?
     * Note that there are only two items and you can't bring more than one item of each type,
     * i.e. you can't take two first items or two second items.
     */
    fun knapsackLight(value1: Int, weight1: Int, value2: Int, weight2: Int, maxW: Int): Int {
        if(maxW >= weight1+weight2){
            return value1 + value2
        } else{
            return if(maxW >= weight2 && maxW >= weight1){
                if (value1 > value2) {
                    value1
                } else {
                    value2
                }
            }else if(maxW >= weight1){
                value1
            }else if(maxW >= weight2){
                value2
            }else{
                0;
            }
        }
    }

    /** 11 **
     *You're given three integers, a, b and c.
     * It is guaranteed that two of these integers are equal to each other.
     * What is the value of the third integer?
     */
    fun extraNumber(a: Int, b: Int, c: Int): Int {
        return when (a) {
            b -> {
                c
            }
            c -> {
                b
            }
            else -> {
                a
            }
        }
    }

    /** 12 **
     *Given integers a and b, determine whether the following pseudocode results in an infinite loop
     * while a is not equal to b do
            - increase a by 1
            - decrease b by 1
     *Assume that the program is executed on a virtual machine which
     *can store arbitrary long numbers and execute forever.
     */
    fun isInfiniteProcess(a: Int, b: Int): Boolean {
        return when {
            a>b -> {
                true
            }
            a == b -> {
                false
            }
            else -> {
                (b-a-1)%2 == 0
            }
        }
    }

    /** 13 **
     *Consider an arithmetic expression of the form a#b=c.
     * Check whether it is possible to replace # with one of the four signs: +, -, * or / to obtain a correct expression.
     */
    fun arithmeticExpression(a: Int, b: Int, c: Int): Boolean {
        return (a/b==c && a%b ==0) || a+b==c || a*b == c || a-b == c
    }

    /** 14 **
     *In tennis, the winner of a set is based on how many games each player wins.
     * The first player to win 6 games is declared the winner unless their opponent had already won 5 games,
     * in which case the set continues until one of the players has won 7 games.
     * Given two integers score1 and score2, your task is to determine if it is possible
     * for a tennis set to be finished with a final score of score1 : score2.
     */
    fun tennisSet(score1: Int, score2: Int): Boolean {
        return if((score1 == 6 && score2 <=4) ||(score2 == 6 && score1 <=4) ){
            true;
        }else (score1 == 7 || score2 == 7)&&(score1!=score2)&& score1<8 && score1>4 &&score2<8 && score2>4
    }

    /** 15 **
     * Mary believes that a person is loved if and only if he/she is both young and beautiful
     * Knowing whether a person is young, beautiful and loved, find out if they contradict Mary's belief.
     * A person contradicts Mary's belief if one of the following statements is true:
     *  - they are young and beautiful but not loved;
     *  - they are loved but not young or not beautiful.
     */
    fun willYou(young: Boolean, beautiful: Boolean, loved: Boolean): Boolean {
        return (young&&beautiful&&!loved)||((!young||!beautiful)&&loved)
    }

    /** 16 **
     *
     */
    fun metroCard(lastNumberOfDays: Int): MutableList<Int> {
        when(lastNumberOfDays){
            28 -> {
                return mutableListOf(31)
            }
            30 -> {
                return mutableListOf(31)
            }
            31 ->{
                return mutableListOf(28,30,31)
            }
        }
        return mutableListOf(0)
    }
}