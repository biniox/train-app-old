package com.example.trainapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_show_days.*

class ShowDays : AppCompatActivity() {
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
        setContentView(R.layout.activity_show_days)

        var list: ArrayList<daysItem> = ArrayList<daysItem>();
        Firebase.firestore.collection("users").document(userData.uid).collection("days")
            .get().addOnSuccessListener { documents ->
                for(document in documents) {
                    var tmp: Timestamp = document.data.get("date") as Timestamp;
                    val item = daysItem(
                        convertLongTimetoString(tmp.seconds), document.data.get("feel").toString(), document.data.get("sleep").toString()
                        , document.data.get("diet").toString())   ;
                    list.add(item);
                }
                daysRecyclerView.adapter = daysAdapter(list)
                daysRecyclerView.layoutManager = LinearLayoutManager(this)
                daysRecyclerView.setHasFixedSize(true);
                }
    }
}
