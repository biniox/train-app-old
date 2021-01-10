package com.example.trainapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_nowy_trening.*

class NowyTrening : AppCompatActivity() {
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
        setContentView(R.layout.activity_nowy_trening)

        dalejTreningButton.setOnClickListener {
            if(nazwaTreningEditText.length()!=0) {
                val myIntent = Intent(applicationContext, NowyTrening2::class.java)
                myIntent.putExtra("name", nazwaTreningEditText.text.toString());
                startActivity(myIntent)
            } else {
                Toast.makeText(applicationContext, "Wypełnij wszystkie pola!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
