package com.example.trainapp

import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.measure_row.view.*
import android.widget.TextView


class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

class pomiaryAdapter(private val exampleList : List<pomiaryItem>) : RecyclerView.Adapter<pomiaryAdapter.pomiaryViewHolder>() {

    class pomiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // tutaj wszystkie pola do wywpełnienia
        val data: TextView = itemView.dateTextView
        val waga : TextView = itemView.wagaTextView
        val uda : TextView = itemView.udaTextView
        val brzuch : TextView = itemView.brzuchTextView
        val klatka : TextView = itemView.klatkaTextView
        val biceps : TextView = itemView.bicepsTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): pomiaryViewHolder {
        val  itemView = LayoutInflater.from(parent.context).inflate(R.layout.measure_row,
            parent, false)

        return pomiaryViewHolder(itemView)
    }

    override fun getItemCount() = exampleList.size

    override fun onBindViewHolder(holder: pomiaryViewHolder, position: Int) {
        val currentItem = exampleList[position]

        holder.data.setText(currentItem.data);
        holder.waga.setText(currentItem.waga);
        holder.uda.setText(currentItem.uda);
        holder.brzuch.setText(currentItem.brzuch);
        holder.klatka.setText(currentItem.klatka);
        holder.biceps.setText(currentItem.biceps);

    }
}

