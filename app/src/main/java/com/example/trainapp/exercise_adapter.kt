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


class exerciseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

class exerciseAdapter(private val exampleList : List<exerciseItem>) : RecyclerView.Adapter<exerciseAdapter.exerciseViewHolder>() {

    class exerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // tutaj wszystkie pola do wywpełnienia
        val nameCheck: TextView = itemView.nameCheckBox;
        val seriesEdit: TextView = itemView.seriesEditText;
        val howMuchEdit: TextView = itemView.howMuchEditText;

        val isCheckedName: Boolean = itemView.nameCheckBox.isChecked;

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): exerciseViewHolder {
        val  itemView = LayoutInflater.from(parent.context).inflate(R.layout.exercise_row,
            parent, false)

        return exerciseViewHolder(itemView)
    }

    override fun getItemCount() = exampleList.size

    override fun onBindViewHolder(holder: exerciseViewHolder, position: Int) {
        val currentItem = exampleList[position]
        holder.nameCheck.setOnClickListener {
            val con = holder.itemView.context;

                Toast.makeText(con, "Spoko", Toast.LENGTH_SHORT).show();
                exampleList[position].changeChecked();



        }

        holder.seriesEdit.addTextChangedListener (object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                exampleList[position].setseries(holder.seriesEdit.text.toString());
            }

        })

        holder.howMuchEdit.addTextChangedListener (object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                exampleList[position].sethowMuch(holder.howMuchEdit.text.toString());
            }

        })

        holder.nameCheck.setText(currentItem.name);
        holder.seriesEdit.setText(currentItem.series);
        holder.howMuchEdit.setText(currentItem.howMuch);




    }
}

