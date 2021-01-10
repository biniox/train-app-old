package com.example.trainapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.measure_row.view.*
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.cardio_row.view.*
import kotlinx.android.synthetic.main.exercise_row.view.*
import kotlinx.android.synthetic.main.training_show_row.view.*


class trainingShowViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

class trainingShowAdapter(private val exampleList : List<trainingShowItem>) : RecyclerView.Adapter<trainingShowAdapter.trainingShowViewHolder>() {

    class trainingShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // tutaj wszystkie pola do wywpełnienia
        val nameCheckBox: CheckBox = itemView.nameCheckBoxTrainingShow;
        val paramTextView: TextView = itemView.paramTextView;
        val nowyRekordButton: Button = itemView.nowyRekordButton;
        val pokazRekordButton: Button = itemView.pokazRekordButton;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): trainingShowViewHolder {
        val  itemView = LayoutInflater.from(parent.context).inflate(R.layout.training_show_row,
            parent, false)

        return trainingShowViewHolder(itemView)
    }

    override fun getItemCount() = exampleList.size

    override fun onBindViewHolder(holder: trainingShowViewHolder, position: Int) {
        val currentItem = exampleList[position]

        holder.nameCheckBox.setText(currentItem.name);
        val tmp: String = currentItem.series + "x" + currentItem.howMuch;
        holder.paramTextView.setText(tmp);

        holder.nowyRekordButton.setOnClickListener {
            val con = holder.itemView.nowyRekordButton.context;
            val myIntent = Intent(con, NowyRekord::class.java);
            myIntent.putExtra("id", currentItem.id);
            myIntent.putExtra("name", currentItem.name);
            myIntent.putExtra("idTrain", currentItem.idTrain);
            con.startActivity(myIntent);
        }

        holder.pokazRekordButton.setOnClickListener {
            val con = holder.itemView.nowyRekordButton.context;
            val myIntent = Intent(con, pokazRekordy::class.java);
            myIntent.putExtra("id", currentItem.id);
            myIntent.putExtra("name", currentItem.name);
            myIntent.putExtra("idTrain", currentItem.idTrain);
            con.startActivity(myIntent);
        }




    }
}

