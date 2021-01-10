package com.example.trainapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_nowy_trening2.*
import kotlinx.android.synthetic.main.activity_pokaz_trening.*

class PokazTrening : AppCompatActivity() {
    private var list: ArrayList<trainingShowItem> = ArrayList<trainingShowItem>();

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
    private fun getData(idTrain: String) {

        Firebase.firestore.collection("users").document(userData.uid)
            .collection("training").document(idTrain).collection("exercise").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {

                    val item = trainingShowItem(document.data.get("Name").toString(), document.id, idTrain, document.data.get("Series").toString(),
                        document.data.get("HowMuch").toString(), "todo");
                    list.add(item);
                }
                trainRecyclerView.adapter = trainingShowAdapter(list)
                trainRecyclerView.layoutManager = LinearLayoutManager(this)
                trainRecyclerView.setHasFixedSize(true)


            }
            .addOnFailureListener { exception ->
                //Log.w(TAG, "Error getting documents: ", exception)
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokaz_trening)
        init();
if(intent.hasExtra("id")) {
    val trainName = intent.getStringExtra("name");
    val trainId = intent.getStringExtra("id");
    getData(trainId);
    trainTitleTextView.setText(trainName);
} else {
    startActivity(Intent(applicationContext, Home::class.java));
}

    }
}
