package com.k.ariahelper.controllers.dices

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.k.ariahelper.R
import com.k.ariahelper.models.DiceModel

class DiceAdapter(private val diceList: ArrayList<DiceModel>,
                  private val diceClickListener: DiceClickListener)
    : RecyclerView.Adapter<DiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val diceView = inflater.inflate(R.layout.item_dice, parent, false)
        return DiceViewHolder(diceView)
    }

    override fun onBindViewHolder(holder: DiceViewHolder, position: Int) {
        val dice: DiceModel = diceList[position]
        holder.bind(dice)
        holder.itemView.setOnClickListener {
            diceClickListener.onDiceClickListener(diceList[position])
        }
    }

    override fun getItemCount(): Int = diceList.size
}