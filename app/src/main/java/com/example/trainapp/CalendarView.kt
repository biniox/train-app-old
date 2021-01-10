package com.example.trainapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_calendar.*
import kotlinx.android.synthetic.main.activity_calendar_view.*
import kotlinx.android.synthetic.main.activity_cardio.*
import java.sql.Date
import java.sql.Timestamp
import java.util.*

class CalendarView : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item?.itemId == R.id.calendarMenu) {
            startActivity(Intent(applicationContext, CalendarView::class.java))
        } else if(item?.itemId == R.id.logoutMenu) {
            signOut();
            startActivity(Intent(applicationContext, MainActivity::class.java))
        } else if(item?.itemId == R.id.dayMenu) {
            startActivity(Intent(applicationContext, ShowDays::class.java))
        } else if(item?.itemId == R.id.Home) {
            startActivity(Intent(applicationContext, Home::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_view)

        init();

        planButton.setOnClickListener{
            startActivity(Intent(applicationContext, addCalendar::class.java));
        }


//        val listSpinner = ArrayList<String>();
//        var list = ArrayList<calendarItem>()
//        Firebase.firestore.collection("users").document(userData.uid)
//            .collection("calendar").get()
//            .addOnSuccessListener { documents ->
//                for (document in documents) {
//                    val item = calendarItem(document.data("date").toString(),);
//                    listTraining.add(item);
//
//                }
//
//                    }

        val dayString = arrayOf("Niedziela","Poniedziałek", "Wtorek", "Sroda", "Czwartek", "Piątek", "Sobota");
        var list = ArrayList<calendarItem>()
        val c = Calendar.getInstance();
        val year = c.get(Calendar.YEAR);
        val month = c.get(Calendar.MONTH);
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        var stringDate = "";

        if(day<10)
            stringDate += "0"+ day + "-";
        else
            stringDate += ""+ day + "-";

        if(month<10)
            stringDate += "0" + (month+1) + "-" + year + "";
        else
            stringDate += "" + (month+1) + "-" + year + "";
        var i = 0;


        while(i<7) {
            var item = calendarItem(stringDate, dayString[(dayOfWeek+i)%7]);

            list.add(item)
            i = i+1;
            var tmp = convertStringToDate(stringDate).time
            stringDate = convertLongTimetoString(tmp/1000+24*3600);
        }
        calendarRecycleView.adapter = calendarAdapter(list)
        calendarRecycleView.layoutManager = LinearLayoutManager(this)
        calendarRecycleView.setHasFixedSize(true)




            }

    }

