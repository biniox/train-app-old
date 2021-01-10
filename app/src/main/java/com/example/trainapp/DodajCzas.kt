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
import kotlinx.android.synthetic.main.activity_dodaj_czas.*
import kotlinx.android.synthetic.main.activity_dodaj_pomiar.*
import java.util.*

class DodajCzas : AppCompatActivity() {
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
        setContentView(R.layout.activity_dodaj_czas)
        val c = Calendar.getInstance();
        val year = c.get(Calendar.YEAR);
        val month = c.get(Calendar.MONTH);
        val day = c.get(Calendar.DAY_OF_MONTH)
        dataEditText.setText(""+ day + "-" + (month+1) + "-" + year + "")

        dataEditText.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                dataEditText.setText(""+ mDay + "-" + (mMonth+1) + "-" + mYear + "");

            }, year, month, day);
            dpd.show();
        }
        dodajCzasButton.setOnClickListener {
            val godz = czasGodzEditText.text.toString();
            val min = czasMinEditText4.text.toString();
            val dystans = dystansEditText.text.toString();
            val idCardio: String = intent.getStringExtra("id");
            val nameCardio: String = intent.getStringExtra("name");



            if(czasMinEditText4.length()!=0 && dystansEditText.length()!=0 && czasGodzEditText.length()!=0 && dataEditText.length()!=0) {
                val newCzas = hashMapOf(
                    "Data" to convertStringToDate(dataEditText.text.toString()),
                    "godz" to godz,
                    "Min" to min,
                    "Dystans" to dystans

                )
                Firebase.firestore.collection("users").document(userData.uid)
                    .collection("cardio").document(idCardio).collection("times").add(newCzas)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(
                            baseContext, " Dodano Nowy Czas ",
                            Toast.LENGTH_SHORT
                        ).show();
                        val intencja = Intent(applicationContext, PokazCardio::class.java);
                        if(idCardio != null && nameCardio != null) {

                                intencja.putExtra("id", idCardio);
                                intencja.putExtra("name", nameCardio);

                        }
                        startActivity(intencja);
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            baseContext, " Nie udało się dodać Czasu",
                            Toast.LENGTH_SHORT
                        ).show();
                    }
            }
        }
    }
}
