package com.example.trainapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_pokaz_cardio.*
import kotlinx.android.synthetic.main.activity_pokaz_rekordy.*
import java.math.RoundingMode

class pokazRekordy : AppCompatActivity() {
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
        setContentView(R.layout.activity_pokaz_rekordy)

        var list: ArrayList<pokazRekordItem> = ArrayList<pokazRekordItem>();
        var nameEx: String = intent.getStringExtra("name");
        var idEx: String = intent.getStringExtra("id");
        var idTrain: String = intent.getStringExtra("idTrain");
        titlePokazRekord.setText(nameEx);

        Firebase.firestore.collection("users").document(userData.uid)
            .collection("training").document(idTrain).collection("exercise").
                document(idEx).collection("records").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val d: Timestamp = document.data.get("Date") as Timestamp;

                val item = pokazRekordItem(convertLongTimetoString(d.seconds), document.data.get("Value").toString());
                list.add(item);
            }
            rekordyRecyclerView.adapter = pokazRekordAdapter(list)
                rekordyRecyclerView.layoutManager = LinearLayoutManager(this)
                rekordyRecyclerView.setHasFixedSize(true);
        }
            .addOnFailureListener { exception ->
                //Log.w(TAG, "Error getting documents: ", exception)
            }
    }
}
