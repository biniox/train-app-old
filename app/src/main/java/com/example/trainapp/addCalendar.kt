package com.example.trainapp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_calendar.*
import kotlinx.android.synthetic.main.activity_dodaj_pomiar.*
import kotlinx.android.synthetic.main.activity_treningi.*
import java.util.*
import kotlin.collections.ArrayList

class addCalendar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_calendar)

        // create data
        val c = Calendar.getInstance();
        val year = c.get(Calendar.YEAR);
        val month = c.get(Calendar.MONTH);
        val day = c.get(Calendar.DAY_OF_MONTH)

        if(intent.hasExtra("date")) {
            dataEditText.setText(intent.getStringExtra("date"));
        } else {

            dataEditText.setText(""+ day + "-" + (month+1) + "-" + year + "")
        }


        dataEditText.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                dataEditText.setText(""+ mDay + "-" + (mMonth+1) + "-" + mYear + "");

            }, year, month, day);
            dpd.show();
        }

        //firebase, get All Training and all cardio
        val listSpinner = ArrayList<String>();
        var listTraining = ArrayList<CardioAndTrainingItem>()
        Firebase.firestore.collection("users").document(userData.uid)
            .collection("training").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val item = CardioAndTrainingItem(document.data.get("Name").toString(), document.id.toString(), false);
                    listTraining.add(item);
                    listSpinner.add(item.name);

                }
                Firebase.firestore.collection("users").document(userData.uid)
                    .collection("cardio").get()
                    .addOnSuccessListener { documents1 ->
                        for (document in documents1) {
                            val item = CardioAndTrainingItem(document.data.get("Name").toString(), document.id.toString(), true);
                            listTraining.add(item);
                            listSpinner.add(item.name);

                        }


                        //spinner code
                        calendarSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listSpinner);
                    }


            }
        var actualPosition: Int = 0;
        calendarSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(applicationContext, listSpinner[position], Toast.LENGTH_SHORT).show();
                actualPosition = position;
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        //////////////////////////////

        addTrainCalendarButton.setOnClickListener {
            if(dataEditText.length()!=0) {
                val newAdd = hashMapOf(
                    "date" to convertStringToDate(dataEditText.text.toString()),
                    "id" to listTraining[actualPosition].id,
                    "name" to listTraining[actualPosition].name,
                    "cardio" to listTraining[actualPosition].cardio
                )

                Firebase.firestore.collection("users").document(userData.uid)
                    .collection("calendar").add(newAdd)
                    .addOnSuccessListener {
                        Toast.makeText(applicationContext, "Dodano do kalendarza", Toast.LENGTH_SHORT).show();
                        startActivity(Intent(applicationContext, CalendarView::class.java));
                    }
                    .addOnFailureListener { exception ->
                        //Log.w(TAG, "Error getting documents: ", exception)
                    }
            }
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
