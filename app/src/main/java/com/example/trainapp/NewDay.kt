package com.example.trainapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_new_day.*
import java.util.*

class NewDay : AppCompatActivity() {
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
        setContentView(R.layout.activity_new_day)
        init();

        saveButton.setOnClickListener {
            if(sleepEditText.length()!=0 && feelEditText.length()!=0 && (noRadio.isChecked || yesRadio.isChecked || someRadio.isChecked)) {
                var diet: String;
                if(noRadio.isChecked) diet = "Nie trzymana";
                else if(yesRadio.isChecked) diet = "Trzymana";
                else diet = "Trochę tak trochę nie";
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

                var toAdd = hashMapOf(
                    "date" to convertStringToDate(stringDate),
                    "sleep" to sleepEditText.text.toString(),
                    "feel" to feelEditText.text.toString(),
                    "diet" to diet
                )
                Firebase.firestore.collection("users").document(userData.uid).collection("days").add(toAdd).addOnSuccessListener {
                    Toast.makeText(applicationContext, "Uzupełniono", Toast.LENGTH_SHORT).show();
                    startActivity(Intent(applicationContext, Home::class.java));
                }
            }
        }

    }
}
