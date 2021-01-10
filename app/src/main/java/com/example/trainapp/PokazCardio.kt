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
import kotlinx.android.synthetic.main.activity_cardio.*
import kotlinx.android.synthetic.main.activity_pokaz_cardio.*
import java.math.RoundingMode

class PokazCardio : AppCompatActivity() {
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
    private fun getData() {
        var list = ArrayList<cardioSzczegolyItem>()
        var maxSpeed: Double = 0.0;
        var maxDistance: Double = 0.0;
        Firebase.firestore.collection("users").document(userData.uid)
            .collection("cardio").document(intent.getStringExtra("id")).collection("times").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val d: Timestamp = document.data.get("Data") as Timestamp;
                    val fullTime = ((document.data.get("godz").toString().toDouble())*60 + (document.data.get("Min").toString().toDouble()))/60;
                    val spe = (document.data.get("Dystans").toString().toDouble())/fullTime;
                    val speed = spe.toBigDecimal().setScale(2, RoundingMode.UP)

                    val dist = document.data.get("Dystans").toString();

                    if(maxSpeed<speed.toDouble()) maxSpeed = speed.toDouble();
                    if(maxDistance<dist.toDouble()) maxDistance = dist.toDouble();
                    val item = cardioSzczegolyItem(convertLongTimetoString(d.seconds), document.data.get("Dystans").toString(), document.data.get("godz").toString(),
                        document.data.get("Min").toString(), speed.toString());
                    list.add(item);
                }
                cardioSzczegolyRecyclerView.adapter = cardioSzczegolyAdapter(list)
                cardioSzczegolyRecyclerView.layoutManager = LinearLayoutManager(this)
                cardioSzczegolyRecyclerView.setHasFixedSize(true);

                maxSpeedTextView.setText("Maksymalna Prędkość: " + maxSpeed.toString());
                maxDistanceTextView.setText("Maksymalny Dystans: " + maxDistance.toString());


            }
            .addOnFailureListener { exception ->
                //Log.w(TAG, "Error getting documents: ", exception)
            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokaz_cardio)
        init();
        val idCardio = intent.getStringExtra("id");
        val nameCardio = intent.getStringExtra("name");
        cardioTextView.setText(nameCardio);
        getData();
        cardioButton.setOnClickListener{
            val Intencja = Intent(applicationContext, DodajCzas::class.java);
            Intencja.putExtra("id", idCardio);
            Intencja.putExtra("name", nameCardio);
            startActivity(Intencja);
        }
    }
}
