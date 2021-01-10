package com.example.trainapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.measure_row.view.*
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.cardio_row.view.*
import kotlinx.android.synthetic.main.cardio_szczegoly_row.view.*



class cardioSzczegolyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

class cardioSzczegolyAdapter(private val exampleList : List<cardioSzczegolyItem>) : RecyclerView.Adapter<cardioSzczegolyAdapter.cardioSzczegolyViewHolder>() {

    class cardioSzczegolyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // tutaj wszystkie pola do wywpełnienia
        val data: TextView = itemView.dataTextView1;
        val czas: TextView = itemView.czasTextView;
        val dystans: TextView = itemView.dystansTextView;
        val predkosc: TextView = itemView.predkoscTextView;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardioSzczegolyViewHolder {
        val  itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardio_szczegoly_row,
            parent, false)

        return cardioSzczegolyViewHolder(itemView)
    }

    override fun getItemCount() = exampleList.size

    override fun onBindViewHolder(holder: cardioSzczegolyViewHolder, position: Int) {
        val currentItem = exampleList[position]

        //holder.itemView.czas.setText(currentItem.);
        holder.czas.setText(currentItem.hour + ":" + currentItem.min);
        holder.data.setText(currentItem.date);
        holder.dystans.setText(currentItem.distance + "km");
        holder.predkosc.setText(currentItem.speed + "km/h");




    }
}

