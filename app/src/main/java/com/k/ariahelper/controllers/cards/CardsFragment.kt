package com.k.ariahelper.controllers.cards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.k.ariahelper.R
import com.k.ariahelper.models.Cards

class CardsFragment : Fragment() {
    private var cards = Cards()

    private lateinit var cardResultDisplay: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardResultDisplay = view.findViewById(R.id.testingCard)

        cardResultDisplay.setOnClickListener {
            if (cards.getDeckSize() > 1) {
                drawCard()
            } else {
                triggerEmptyDeck()
            }
        }
    }

    private fun drawCard() {
        val cardResult = cards.draw()
        val cardValue = cards.getCardValue(cardResult)
        cardResultDisplay.text = cardValue
    }

    private fun triggerEmptyDeck() {
        Toast.makeText(
            context,
            R.string.emptyDeck,
            Toast.LENGTH_SHORT
        ).show()
    }
}