package com.example.trainapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_dodaj_cwiczenie.*

class DodajCwiczenie : AppCompatActivity() {
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
        setContentView(R.layout.activity_dodaj_cwiczenie)
        init();

        var nameTrain = intent.getStringExtra("name");
        dodajButton.setOnClickListener {
            if(nazwaCwiczeniaEditText.length()!=0) {
                val newExercise = hashMapOf(
                    "Name" to nazwaCwiczeniaEditText.text.toString()
                )
                Firebase.firestore.collection("users").document(userData.uid).collection("exercise")
                    .add(newExercise).addOnSuccessListener {
                        Toast.makeText(applicationContext, "Dodano ćwiczenie", Toast.LENGTH_SHORT).show();

                        //val tmp: String = c;
                        val MyIntent = Intent(applicationContext, NowyTrening2::class.java);
                        if(intent.hasExtra("name")) {
                            MyIntent.putExtra("name", intent.getStringExtra("name"));
                        } else {

                        }
                        startActivity(MyIntent);
                    }

            }
        }
    }
}
