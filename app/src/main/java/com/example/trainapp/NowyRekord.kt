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
import kotlinx.android.synthetic.main.activity_nowy_rekord.*
import kotlinx.android.synthetic.main.activity_pokaz_trening.*
import java.util.*

class NowyRekord : AppCompatActivity() {
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
        setContentView(R.layout.activity_nowy_rekord);
        init();

        var nameEx: String = intent.getStringExtra("name");
        var idEx: String = intent.getStringExtra("id");
        var idTrain: String = intent.getStringExtra("idTrain");
        titleRekcord.setText(nameEx);

        //recordEditText.text.toString();

        newRecordButton.setOnClickListener{

            if(recordEditText.length()!=0) {

                var toAdd = hashMapOf(
                    "Date" to Date(),
                    "Value" to recordEditText.text.toString()
                )

                Firebase.firestore.collection("users").document(userData.uid)
                    .collection("training").document(idTrain).collection("exercise").
                        document(idEx).collection("records").add(toAdd).addOnSuccessListener {
                        Toast.makeText(applicationContext, "Dodano Rekord", Toast.LENGTH_SHORT).show();



                        startActivity(Intent(applicationContext, treningi::class.java));
                    }.addOnCanceledListener {
                        Toast.makeText(applicationContext, "Nie udało się ${idTrain} ${idEx}", Toast.LENGTH_SHORT).show();
                    }
            }
        }
    }
}
