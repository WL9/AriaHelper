package com.k.ariahelper.models

import java.util.*

data class DiceModel (
    val label: String,
    val logo: Int,
    val value: Int
    ) {
        private fun roll(): Int {
            return Random().nextInt(value) + 1
        }
}