package com.example.trainapp

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.measure_row.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.cardio_row.view.*
import kotlinx.android.synthetic.main.cardio_szczegoly_row.view.*
import kotlinx.android.synthetic.main.exercise_row.view.*
import kotlinx.android.synthetic.main.rekord_row.view.*


class pokazRekordViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

class pokazRekordAdapter(private val exampleList : List<pokazRekordItem>) : RecyclerView.Adapter<pokazRekordAdapter.pokazRekordViewHolder>() {

    class pokazRekordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // tutaj wszystkie pola do wywpełnienia
        val dataText: TextView = itemView.dataPokazRekord;
        val ileText: TextView = itemView.ilePokazRekord;

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): pokazRekordViewHolder {
        val  itemView = LayoutInflater.from(parent.context).inflate(R.layout.rekord_row,
            parent, false)

        return pokazRekordViewHolder(itemView)
    }

    override fun getItemCount() = exampleList.size

    override fun onBindViewHolder(holder: pokazRekordViewHolder, position: Int) {
        val currentItem = exampleList[position]


        holder.ileText.setText(currentItem.ile);
        holder.dataText.setText(currentItem.date);




    }
}

