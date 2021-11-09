package com.k.ariahelper.controllers.cards

import android.content.Context
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
import kotlin.collections.ArrayList

class CardsFragment : Fragment() {
    private lateinit var cards: Cards
    private lateinit var cardResultDisplay: TextView
    private lateinit var addCardButton: Button
    private lateinit var remainingCardsButton: Button
    private lateinit var resetDeckButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val previousDeck = readDeckState()
        cards = if(previousDeck.size > 0)
            Cards(previousDeck)
        else
            Cards()
    }

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
        displayNewCard(cardResult)
        writeDeckState()
    }

    private fun displayNewCard(card: Int) {
        //TODO : cette fonction sera à modifier pour adapter l'affichage des images de cartes et non plus un simple texte
        val cardValue = cards.getCardValue(card)
        val cardText = getString(cardValue[0]) + " " + getString(cardValue[1])
        cardResultDisplay.text = cardText
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
        val valuesList = arrayOf(
            resources.getString(R.string.cardValue0),
            resources.getString(R.string.cardValue1),
            resources.getString(R.string.cardValue2),
            resources.getString(R.string.cardValue3),
            resources.getString(R.string.cardValue4),
            resources.getString(R.string.cardValue5),
            resources.getString(R.string.cardValue6),
            resources.getString(R.string.cardValue7),
            resources.getString(R.string.cardValue8),
            resources.getString(R.string.cardValue9),
            resources.getString(R.string.cardValue10),
            resources.getString(R.string.cardValue11),
            resources.getString(R.string.cardValue12)
        )
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
        writeDeckState()
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
                    writeDeckState()
                }
                .setMultiChoiceItems(remainingCards, selectedList) { _, which, isChecked ->
                    val card = cards.getCardAtPosition(which)
                    when{
                        isChecked -> cardsToRemove.add(card)
                        cardsToRemove.contains(which) -> cardsToRemove.remove(which)
                    }
                }
                .show()
        }
    }

    private fun getRemainingCards(): Array<CharSequence> {
        val remainingCardsArray = cards.getRemainingCards()
        val remainingCardsText = ArrayList<CharSequence>()
        for(card in remainingCardsArray) {
            if(card[1] == R.string.cardsColorJoker)
                remainingCardsText.add(getString(card[1]))
            else
                remainingCardsText.add(getString(card[0]) + " " + getString(card[1]))
        }
        return remainingCardsText.toTypedArray()
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
            writeDeckState()
        }
    }

    private fun writeDeckState() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val deckState: String = cards.getDeckToSave()

        with (sharedPref.edit()) {
            putString(getString(R.string.deckSaveKey), deckState)
            apply()
        }
    }

    private fun readDeckState(): ArrayList<Int> {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val dataRead: String? = sharedPref?.getString(getString(R.string.deckSaveKey), "")
        val dataTokens = dataRead!!.split(",")
        var deck: ArrayList<Int> = arrayListOf()

        try {
            for (token in dataTokens)
                deck.add(Integer.parseInt(token))
        }
        catch(exception: NumberFormatException) {
            deck = arrayListOf()
        }

        return deck
    }
}