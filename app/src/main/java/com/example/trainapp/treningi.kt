package com.example.trainapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_cardio.*
import kotlinx.android.synthetic.main.activity_treningi.*

class treningi : AppCompatActivity() {
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
        var list = ArrayList<trainingItem>()
        Firebase.firestore.collection("users").document(userData.uid)
            .collection("training").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val item = trainingItem(document.data.get("Name").toString(), document.id.toString());
                    list.add(item);
                }
                trainingRecycleView.adapter = trainingAdapter(list)
                trainingRecycleView.layoutManager = LinearLayoutManager(this)
                trainingRecycleView.setHasFixedSize(true)


            }
            .addOnFailureListener { exception ->
                //Log.w(TAG, "Error getting documents: ", exception)
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treningi)
        init();
        getData();

        nowyTreningButton.setOnClickListener{
            startActivity(Intent(applicationContext, NowyTrening::class.java));
        }
    }
}
