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
import kotlinx.android.synthetic.main.activity_new_day.view.*
import kotlinx.android.synthetic.main.cardio_row.view.*
import kotlinx.android.synthetic.main.days_row.view.*
import kotlinx.android.synthetic.main.measure_row.view.dateTextView


class daysViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

class daysAdapter(private val exampleList : List<daysItem>) : RecyclerView.Adapter<daysAdapter.daysViewHolder>() {

    class daysViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // tutaj wszystkie pola do wywpełnienia
       val date: TextView = itemView.dateTextView;
       val feel: TextView = itemView.feelTextView;
       val sleep: TextView = itemView.sleepTextView;
       val diet: TextView = itemView.dietTextView;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): daysViewHolder {
        val  itemView = LayoutInflater.from(parent.context).inflate(R.layout.days_row,
            parent, false)

        return daysViewHolder(itemView)
    }

    override fun getItemCount() = exampleList.size

    override fun onBindViewHolder(holder: daysViewHolder, position: Int) {
        val currentItem = exampleList[position]

        holder.date.setText(currentItem.date);
        holder.feel.setText("Samopoczucie: " + currentItem.feel);
        holder.sleep.setText("Sen: " + currentItem.sleep);
        holder.diet.setText(currentItem.diet);



    }
}

