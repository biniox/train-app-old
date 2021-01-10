package com.example.trainapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class Home : AppCompatActivity() {
    fun getExtendedData() {
        Firebase.firestore.collection("users").document(userData.uid)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    //failed
                }

                if (snapshot != null && snapshot.exists()) {
                    userData.name = snapshot.getString("Name").toString();

                    // Po pobraniu danych
                    titleText.setText("Witaj Ponownie ${userData.name}");

                    ////////////////////////////
                } else {
                    // null data
                }
            }

    }

    ////////////////////////////////////////////////////////////////////
//    fun getTraining() {
//        Firebase.firestore.collection("users").document(userData.uid).collection("train").
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //start
        if(!checkAuth()) startActivity(Intent(applicationContext, login::class.java));
        init();
        getExtendedData(); // pobiera dane usera rozszerzone i dodaje je do modelu i widoku

        pomiaryButton.setOnClickListener {
            startActivity(Intent(applicationContext, pomiary::class.java));
        }

        cardioButton.setOnClickListener {
            startActivity(Intent(applicationContext, Cardio::class.java));
        }

        maksyButton.setOnClickListener{
            startActivity(Intent(applicationContext, treningi::class.java));
        }

        val c = Calendar.getInstance();
        val year = c.get(Calendar.YEAR);
        val month = c.get(Calendar.MONTH);
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        val hour = c.get(Calendar.HOUR);
        var stringDate = "";

        if(day<10)
            stringDate += "0"+ day + "-";
        else
            stringDate += ""+ day + "-";

        if(month<10)
            stringDate += "0" + (month+1) + "-" + year + "";
        else
            stringDate += "" + (month+1) + "-" + year + "";


        Firebase.firestore.collection("users").document(userData.uid).collection("days")
            .whereEqualTo("date", convertStringToDate(stringDate)).get().addOnSuccessListener { documents ->
                if(documents.isEmpty()) {
                    if(hour>0)
                        //Toast.makeText(applicationContext, hour.toString(), )
                    startActivity(Intent(applicationContext, NewDay::class.java));
                }
            }

        var listOfItem: ArrayList<calendarTrainItem> = ArrayList<calendarTrainItem>();
        Firebase.firestore.collection("users").document(userData.uid)
            .collection("calendar").whereEqualTo("date", convertStringToDate(stringDate)).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var card = document.get("cardio") as Boolean;
                    listOfItem.add(calendarTrainItem(document.get("name").toString(), document.get("id").toString(), card));

                }
                homeRecycler.adapter = calendarTrainAdapter(listOfItem)
                homeRecycler.layoutManager = LinearLayoutManager(this)
                homeRecycler.setHasFixedSize(true)

            }

    }


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
}
