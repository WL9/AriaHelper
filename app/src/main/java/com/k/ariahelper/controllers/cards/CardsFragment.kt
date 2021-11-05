package com.k.ariahelper.controllers.cards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.k.ariahelper.R
import com.k.ariahelper.models.Cards

class CardsFragment : Fragment() {
    private var cards = Cards()

    private lateinit var cardResultDisplay: TextView
    private lateinit var addCardButton: Button
    private lateinit var remainingCardsButton: Button
    private lateinit var resetDeckButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardResultDisplay = view.findViewById(R.id.testingCard)
        addCardButton = view.findViewById(R.id.addCard)
        remainingCardsButton = view.findViewById(R.id.remainingCards)
        resetDeckButton = view.findViewById(R.id.resetDeck)

        setClickListeners()
    }

    private fun setClickListeners() {
        //TODO : rework à faire : définir les fonctions plutot qu'une fonction de set globale
        cardResultDisplay.setOnClickListener {
            if (cards.getDeckSize() > 0) {
                drawCard()
            } else {
                triggerEmptyDeck()
            }
        }

        addCardButton.setOnClickListener {
            chooseCardColor()
        }

        remainingCardsButton.setOnClickListener {
            removeRemainingCards()
        }

        resetDeckButton.setOnClickListener {
            resetDeck()
        }
    }

    private fun drawCard() {
        val cardResult = cards.draw()
        val cardValue = cards.getCardValue(cardResult)
        val cardText = cardValue[0].toString() + " " + getString(cardValue[1])
        displayNewCard(cardText)
    }

    private fun displayNewCard(card: String) {
        //TODO : cette fonction sera à modifier pour adapter l'affichage des cartes
        //et non plus un simple texte
        cardResultDisplay.text = card
    }

    private fun triggerEmptyDeck() {
        Toast.makeText(
            context,
            R.string.emptyDeck,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun chooseCardColor() {
        val colorsList = arrayOf(
            resources.getString(R.string.cardsColorSpades),
            resources.getString(R.string.cardsColorDiamonds),
            resources.getString(R.string.cardsColorClubs),
            resources.getString(R.string.cardsColorHearts),
            resources.getString(R.string.cardsColorJoker)
        )
        var checkedItem = 0

        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(R.string.newCardColor)
                .setNeutralButton(R.string.cancel) { _, _ ->
                }
                .setPositiveButton(R.string.select) { _, _ ->
                    val isJoker = checkedItem == colorsList.size - 1
                    if(isJoker)
                        addNewCard(checkedItem)
                    else
                        chooseCardValue(checkedItem)
                }
                .setSingleChoiceItems(colorsList, checkedItem) { _, which ->
                    checkedItem = which
                }
                .show()
        }
    }

    private fun chooseCardValue(color: Int) {
        val valuesList = arrayOf("2","3","4","5","6","7","8","9","10", "valet", "dame", "roi", "as")
        var checkedItem = 0

        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(R.string.newCardValue)
                .setNeutralButton(R.string.cancel) { _, _ ->
                }
                .setPositiveButton(R.string.addCard) { _, _ ->
                    addNewCard(color, checkedItem)
                }
                .setSingleChoiceItems(valuesList, checkedItem) { _, which ->
                    checkedItem = which
                }
                .show()
        }
    }

    private fun addNewCard(color: Int = 0, value: Int = 0) {
        val newCard = color * 13 + value
        cards.addCard(newCard)
    }

    private fun resetDeck() {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setMessage(R.string.confirmResetDeck)
                .setNegativeButton(R.string.cancel) { _, _ ->
                }
                .setPositiveButton(R.string.resetDeck) { _, _ ->
                    cards.resetDeck()
                }
                .show()
        }
    }

    private fun removeRemainingCards() {
        val remainingCards:Array<CharSequence> = getRemainingCards()
        val selectedList = BooleanArray(cards.getDeckSize()) {false}
        val cardsToRemove = ArrayList<Int>()

        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(R.string.remainingCards)
                .setNeutralButton(R.string.cancel) { _, _ ->
                }
                .setPositiveButton(R.string.removeCards) { _, _ ->
                    for(card in cardsToRemove)
                        cards.removeCard(card)
                }
                .setMultiChoiceItems(remainingCards, selectedList) { _, which, isChecked ->
                    when{
                        isChecked -> cardsToRemove.add(cards.cards[which])
                        cardsToRemove.contains(which) -> cardsToRemove.remove(which)
                        else -> false
                    }
                }
                .show()
        }
    }

    private fun getRemainingCards(): Array<CharSequence> {
        val cardsList = ArrayList<CharSequence>()
        for(card in cards.cards) {
            val cardValue = cards.getCardValue(card)
            val cardText = cardValue[0].toString() + " " + getString(cardValue[1])
            cardsList.add(cardText)
        }
        return cardsList.toTypedArray()
    }
}