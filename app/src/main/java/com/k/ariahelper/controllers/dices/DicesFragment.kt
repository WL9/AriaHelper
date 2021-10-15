package com.k.ariahelper.controllers.dices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.k.ariahelper.R
import com.k.ariahelper.models.DiceModel

class DicesFragment : Fragment(), DiceClickListener {
    private val availableDices: ArrayList<DiceModel> = arrayListOf(
        DiceModel("Coin", R.drawable.d10, 2),
        DiceModel("D4", R.drawable.d10, 4),
        DiceModel("D6", R.drawable.d10, 6),
        DiceModel("D8", R.drawable.d10, 8),
        DiceModel("D10", R.drawable.d10, 10),
        DiceModel("D12", R.drawable.d10, 12),
        DiceModel("D20", R.drawable.d10, 20),
        DiceModel("D100", R.drawable.d10, 100)
    )
    private var numberOfDices: Int = 1

    private lateinit var recycler: RecyclerView
    private lateinit var resultDisplay: TextView
    private lateinit var resultDetailsDisplay: TextView
    private lateinit var diceTypeDisplay: TextView
    private lateinit var increaseDices: Button
    private lateinit var decreaseDices: Button
    private lateinit var dicesNumberSelection: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dices, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.diceRecyclerView)
        resultDisplay = view.findViewById(R.id.resultDisplay)
        resultDetailsDisplay = view.findViewById(R.id.resultDetailsDisplay)
        diceTypeDisplay = view.findViewById(R.id.diceTypeDisplay)
        increaseDices = view.findViewById(R.id.numberOfDicesIncrease)
        decreaseDices = view.findViewById(R.id.numberOfDicesDecrease)
        dicesNumberSelection = view.findViewById(R.id.numberOfDices)

        setDiceNumberPicker()

        recycler.apply {
            layoutManager = androidx.recyclerview.widget.GridLayoutManager(activity,3)
            adapter = DiceAdapter(availableDices, this@DicesFragment)
        }
    }

    override fun onDiceClickListener(diceModel: DiceModel) {
        val dicesResult = diceModel.roll(numberOfDices)
        val resultText: String = dicesResult.toString()

        diceTypeDisplay.text = diceModel.label
        resultDetailsDisplay.text = resultText
        resultDisplay.text = dicesResult.sum().toString()
    }

    private fun setDiceNumberPicker() {
        updateNumberOfDicesDisplay()

        increaseDices.setOnClickListener {
            increaseDiceNumber()
        }
        decreaseDices.setOnClickListener {
            decreaseDicesNumber()
        }
    }

    private fun increaseDiceNumber() {
        if(numberOfDices < 10)
            numberOfDices += 1
        updateNumberOfDicesDisplay()
    }

    private fun decreaseDicesNumber() {
        if(numberOfDices > 1)
            numberOfDices -= 1
        updateNumberOfDicesDisplay()
    }

    private fun updateNumberOfDicesDisplay() {
        dicesNumberSelection.text = numberOfDices.toString()
    }
}