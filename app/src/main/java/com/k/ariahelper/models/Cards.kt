package com.k.ariahelper.models

import com.k.ariahelper.R
import kotlin.random.Random

class Cards(_cards: ArrayList<Int> = arrayListOf()) {
    private val deckSize: Int = 52
    private val colorSize: Int = 13
    private val cardsColors = arrayOf(
        R.string.cardsColorSpades,
        R.string.cardsColorDiamonds,
        R.string.cardsColorClubs,
        R.string.cardsColorHearts,
        R.string.cardsColorJoker
    )
    private val cardsValues = arrayOf(
        R.string.cardValue0,
        R.string.cardValue1,
        R.string.cardValue2,
        R.string.cardValue3,
        R.string.cardValue4,
        R.string.cardValue5,
        R.string.cardValue6,
        R.string.cardValue7,
        R.string.cardValue8,
        R.string.cardValue9,
        R.string.cardValue10,
        R.string.cardValue11,
        R.string.cardValue12
    )

    private var cards = _cards

    init {
        if(cards.size == 0)
        for (value in 0..deckSize)
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
        return arrayOf(cardsValues[cardValue], cardsColors[cardTypeIndex])
    }

    fun removeCard(value: Int) {
        cards.remove(value)
    }

    fun getRemainingCards(): ArrayList<Array<Int>> {
        val cardsList = ArrayList<Array<Int>>()
        for(card in cards) {
            val cardValue = getCardValue(card)
            cardsList.add(cardValue)
        }
        return cardsList
    }

    fun resetDeck() {
        cards = arrayListOf()
        for (value in 1..deckSize) cards.add(value)
    }

    fun getCardAtPosition(position: Int): Int {
        return cards[position]
    }
}