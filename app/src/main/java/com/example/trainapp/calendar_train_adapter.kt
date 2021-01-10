package com.example.trainapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.measure_row.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_calendar.*
import kotlinx.android.synthetic.main.calendar_row.view.*
import kotlinx.android.synthetic.main.calendar_train_row.view.*
import kotlinx.android.synthetic.main.cardio_row.view.*


class calendarTrainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

class calendarTrainAdapter(private val exampleList : List<calendarTrainItem>) : RecyclerView.Adapter<calendarTrainAdapter.calendarTrainViewHolder>() {

    class calendarTrainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // tutaj wszystkie pola do wywpełnienia
        val button : Button = itemView.calendarTrainButton;
        // no i jeszcze recycle view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): calendarTrainViewHolder {
        val  itemView = LayoutInflater.from(parent.context).inflate(R.layout.calendar_train_row,
            parent, false)

        return calendarTrainViewHolder(itemView)
    }

    override fun getItemCount() = exampleList.size

    override fun onBindViewHolder(holder: calendarTrainViewHolder, position: Int) {
        val currentItem = exampleList[position]
        holder.button.setText(currentItem.name);



        holder.button.setOnClickListener{
            var MyIntent: Intent;

            if(currentItem.cardio)
                MyIntent = Intent(holder.button.context, PokazCardio::class.java);
            else
                MyIntent = Intent(holder.button.context, PokazTrening::class.java);
            MyIntent.putExtra("id", currentItem.id);
            MyIntent.putExtra("name", currentItem.name);
            holder.button.context.startActivity(MyIntent)
        }
//        holder.dataTextView.setText(currentItem.date);
//        holder.dayCalendarTextView.setText(currentItem.day);
//        val con = holder.itemView.addCalendarButton.context;
//        holder.addCalendarButton.setOnClickListener {
//            val IntentMy = Intent(con, addCalendar::class.java);
//            IntentMy.putExtra("date", currentItem.date);
//            con.startActivity(IntentMy);
//        }
//        val con = holder.itemView.cardioRowButton.context;
//        holder.itemView.cardioRowButton.setText(currentItem.name);
//
//        holder.data.setOnClickListener{
//            val IntentMy = Intent(con, PokazCardio::class.java);
//            IntentMy.putExtra("name", currentItem.name);
//            IntentMy.putExtra("id", currentItem.id);
//            con.startActivity(IntentMy);
//
//        }


    }
}

