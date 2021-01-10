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


class cardioViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

class cardioAdapter(private val exampleList : List<cardioItem>) : RecyclerView.Adapter<cardioAdapter.cardioViewHolder>() {

    class cardioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // tutaj wszystkie pola do wywpełnienia
        val data: TextView = itemView.cardioRowButton;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardioViewHolder {
        val  itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardio_row,
            parent, false)

        return cardioViewHolder(itemView)
    }

    override fun getItemCount() = exampleList.size

    override fun onBindViewHolder(holder: cardioViewHolder, position: Int) {
        val currentItem = exampleList[position]

        val con = holder.itemView.cardioRowButton.context;
        holder.itemView.cardioRowButton.setText(currentItem.name);

        holder.data.setOnClickListener{
            val IntentMy = Intent(con, PokazCardio::class.java);
            IntentMy.putExtra("name", currentItem.name);
            IntentMy.putExtra("id", currentItem.id);
            con.startActivity(IntentMy);

        }


    }
}

