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


class trainingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

class trainingAdapter(private val exampleList : List<trainingItem>) : RecyclerView.Adapter<trainingAdapter.trainingViewHolder>() {

    class trainingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // tutaj wszystkie pola do wywpełnienia
        val data: TextView = itemView.cardioRowButton;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): trainingViewHolder {
        val  itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardio_row,
            parent, false)

        return trainingViewHolder(itemView)
    }

    override fun getItemCount() = exampleList.size

    override fun onBindViewHolder(holder: trainingViewHolder, position: Int) {
        val currentItem = exampleList[position]

        val con = holder.itemView.cardioRowButton.context;
        holder.itemView.cardioRowButton.setText(currentItem.name);

        holder.data.setOnClickListener{
            val IntentMy = Intent(con, PokazTrening::class.java);
            IntentMy.putExtra("name", currentItem.name);
            IntentMy.putExtra("id", currentItem.id);
            con.startActivity(IntentMy);

        }


    }
}

