package com.k.ariahelper.models

import com.k.ariahelper.R
import kotlin.random.Random

class Cards(_cards: ArrayList<Int> = arrayListOf()) {
    private val deckSize: Int = 52
    private val colorSize: Int = 13
    private var cardsColors = arrayOf(
        R.string.cardsColorSpades,
        R.string.cardsColorDiamonds,
        R.string.cardsColorClubs,
        R.string.cardsColorHearts,
        R.string.cardsColorJoker
    )

    var cards = _cards

    init {
        if(cards.size == 0)
        for (value in 1..deckSize)
            cards.add(value)
    }

    fun getDeckSize(): Int {
        return cards.size
    }

    fun draw(): Int {
        val randomIndex = Random.Default.nextInt(cards.size)
        val cardValue = cards[randomIndex]

        cards.removeAt(randomIndex)
        return cardValue
    }

    fun addCard(newCard: Int) {
        cards.add(newCard)
        cards.sort()
    }

    fun getCardValue(id: Int): Array<Int> {
        val cardTypeIndex: Int = id / colorSize
        val cardValue: Int = id - colorSize * cardTypeIndex
        return arrayOf(cardValue, cardsColors[cardTypeIndex])
    }

    fun removeCard(value: Int) {
        cards.remove(value)
    }

    fun resetDeck() {
        cards = arrayListOf()
        for (value in 1..deckSize) cards.add(value)
    }
}