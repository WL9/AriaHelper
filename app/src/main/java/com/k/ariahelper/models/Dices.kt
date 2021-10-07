package com.k.ariahelper.models

import java.util.*
import kotlin.collections.ArrayList

class Dices(type: Int, number: Int) {
    constructor(type: Int) : this(type, 1)
    constructor(): this(6,1)

    private var diceType: Int = type
    private var diceNumber: Int = number

    fun setDiceType(type: Int) {diceType = type}
    fun getDiceType(): Int {return diceNumber}
    fun setDiceNumber(number: Int) {diceNumber = number}
    fun getDiceNumber(): Int {return diceNumber}

    public fun rollDices(): ArrayList<Int> {
        val results = arrayListOf<Int>()
        for (dice in 1..diceNumber)
            results.add(getRandomValue(diceType))
        return results
    }

    private fun getRandomValue(range: Int): Int {
        return Random().nextInt(range) + 1
    }
}