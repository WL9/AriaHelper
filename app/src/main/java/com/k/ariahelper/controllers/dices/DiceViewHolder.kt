package com.k.ariahelper.controllers.dices

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.k.ariahelper.R
import com.k.ariahelper.models.DiceModel

class DiceViewHolder(diceView: View)
    : RecyclerView.ViewHolder(diceView) {

    private var diceTypeLogo : ImageView? = null
    private var diceTypeLabel : TextView? = null

    init {
        diceTypeLogo = itemView.findViewById(R.id.diceLogo)
        diceTypeLabel = itemView.findViewById(R.id.diceLabel)
    }

    fun bind(dice: DiceModel) {
        diceTypeLabel?.text = dice.label
        diceTypeLogo?.setImageResource(dice.logo)
    }
}