package com.example.trainapp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_nowe_cardio.*
import kotlinx.android.synthetic.main.activity_register.*
import java.sql.Timestamp
import java.util.*

class NoweCardio : AppCompatActivity() {
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
        setContentView(R.layout.activity_nowe_cardio)
        init();

        dodajCardioButton.setOnClickListener{
           if(NameCardioEditText.length()>0) {
               val newCardio = hashMapOf(
                    "Name" to NameCardioEditText.text.toString()
                )
               Firebase.firestore.collection("users").document(userData.uid)
                    .collection("cardio").add(newCardio)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(
                            baseContext, " Dodano Cardio",
                            Toast.LENGTH_SHORT
                        ).show();
                        startActivity(Intent(applicationContext, Cardio::class.java));
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            baseContext, " Nie udało się dodać Cardio",
                            Toast.LENGTH_SHORT
                        ).show();
                    }
           }
        }

        // create data
//        val c = Calendar.getInstance();
//        val year = c.get(Calendar.YEAR);
//        val month = c.get(Calendar.MONTH);
//        val day = c.get(Calendar.DAY_OF_MONTH)
//        DataEditText.setText(""+ day + "-" + (month+1) + "-" + year + "")
//
//        DataEditText.setOnClickListener {
//            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
//                DataEditText.setText(""+ mDay + "-" + (mMonth+1) + "-" + mYear + "");
//
//            }, year, month, day);
//            dpd.show();
//        }
//
//        DodajButton.setOnClickListener {
//            if(DataEditText.length()!=0 && brzuchEditText.length()!=0 && klatkaEditText.length()!=0 && udaEditText.length()!=0 &&
//                wagaEditText.length()!=0 && bicepsEditText.length()!=0) {
//
////                if(DataEditText.) {
//                val date1 = convertStringToDate(DataEditText.text.toString());
//                val newMeasure = hashMapOf(
//                    "Data" to Timestamp(date1.time),
//                    "Brzuch" to brzuchEditText.text.toString(),
//                    "Klatka" to klatkaEditText.text.toString(),
//                    "Uda" to udaEditText.text.toString(),
//                    "Waga" to wagaEditText.text.toString(),
//                    "Biceps" to bicepsEditText.text.toString()
//                )
//
//                Firebase.firestore.collection("users").document(userData.uid)
//                    .collection("measurement").add(newMeasure)
//                    .addOnSuccessListener { documentReference ->
//                        Toast.makeText(
//                            baseContext, " Dodano Pomiar",
//                            Toast.LENGTH_SHORT
//                        ).show();
//                        startActivity(Intent(applicationContext, pomiary::class.java));
//                    }
//                    .addOnFailureListener { e ->
//                        Toast.makeText(
//                            baseContext, " Nie udało się dodać pomiaru",
//                            Toast.LENGTH_SHORT
//                        ).show();
//                    }
//
//            } else {
//                Toast.makeText(baseContext, "Wypełnij wszystkie pola formularza", Toast.LENGTH_SHORT).show();
//            }
//        }
    }
}

