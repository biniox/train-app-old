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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_calendar_view.*
import kotlinx.android.synthetic.main.calendar_row.view.*
import kotlinx.android.synthetic.main.cardio_row.view.*


class calendarViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

class calendarAdapter(private val exampleList : List<calendarItem>) : RecyclerView.Adapter<calendarAdapter.calendarViewHolder>() {

    class calendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // tutaj wszystkie pola do wywpełnienia
        val dataTextView: TextView = itemView.dataCalendarTextView;
        val dayCalendarTextView: TextView = itemView.dayCalendarTextView;
        val addCalendarButton: TextView = itemView.addCalendarButton;
        val recycler = itemView.calendarTrainRecyclerView;
        // no i jeszcze recycle view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): calendarViewHolder {
        val  itemView = LayoutInflater.from(parent.context).inflate(R.layout.calendar_row,
            parent, false)

        return calendarViewHolder(itemView)
    }

    override fun getItemCount() = exampleList.size

    override fun onBindViewHolder(holder: calendarViewHolder, position: Int) {
        val currentItem = exampleList[position]

        holder.dataTextView.setText(currentItem.date);
        holder.dayCalendarTextView.setText(currentItem.day);
        val con = holder.itemView.addCalendarButton.context;
        holder.addCalendarButton.setOnClickListener {
            val IntentMy = Intent(con, addCalendar::class.java);
            IntentMy.putExtra("date", currentItem.date);
            con.startActivity(IntentMy);
        }

        var listOfItem: ArrayList<calendarTrainItem> = ArrayList<calendarTrainItem>();


        init();
        Firebase.firestore.collection("users").document(userData.uid)
            .collection("calendar").whereEqualTo("date", convertStringToDate(currentItem.date)).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var card = document.get("cardio") as Boolean;
                    listOfItem.add(calendarTrainItem(document.get("name").toString(), document.get("id").toString(), card));

                }
                holder.recycler.adapter = calendarTrainAdapter(listOfItem)
                holder.recycler.layoutManager = LinearLayoutManager(holder.recycler.context)
                holder.recycler.setHasFixedSize(true)

            }


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

