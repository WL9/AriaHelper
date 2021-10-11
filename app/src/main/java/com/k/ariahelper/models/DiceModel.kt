package com.k.ariahelper.models

import java.util.*
import kotlin.collections.ArrayList

class DiceModel(val label: String, val logo: Int, val value: Int) {

    private fun roll(): Int {
        return Random().nextInt(value) + 1
    }

    fun roll(dicesNumber: Int): ArrayList<Int> {
        val results = arrayListOf<Int>()

        for (dice in 1..dicesNumber)
            results.add(roll())
        return results
    }
}