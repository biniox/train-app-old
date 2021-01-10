package com.example.trainapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_cardio.*
import kotlinx.android.synthetic.main.activity_cardio.pomiaryButton
import kotlinx.android.synthetic.main.activity_pomiary.*
import kotlinx.android.synthetic.main.cardio_row.view.*

class Cardio : AppCompatActivity() {
        private fun generateList(size: Int): ArrayList<String> {
        val list = ArrayList<String>()

        for(i in 0 until size) {
            val item = " " + i.toString() + " ";
            list += item
        }
        return list
    }

    private fun getData() {
        var list = ArrayList<cardioItem>()
        Firebase.firestore.collection("users").document(userData.uid)
            .collection("cardio").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val item = cardioItem(document.data.get("Name").toString(), document.id.toString());
                    list.add(item);
                }
                cardioRecycleView.adapter = cardioAdapter(list)
                cardioRecycleView.layoutManager = LinearLayoutManager(this)
                cardioRecycleView.setHasFixedSize(true)


            }
            .addOnFailureListener { exception ->
                //Log.w(TAG, "Error getting documents: ", exception)
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cardio)
        init();
        getData();





        pomiaryButton.setOnClickListener {
            startActivity(Intent(applicationContext, NoweCardio::class.java));


        }
    }
}
