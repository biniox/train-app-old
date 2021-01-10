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
import kotlinx.android.synthetic.main.activity_cardio.*
import kotlinx.android.synthetic.main.activity_nowy_trening2.*

class NowyTrening2 : AppCompatActivity() {
    private val list: ArrayList<exerciseItem> = ArrayList<exerciseItem>();

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

        Firebase.firestore.collection("users").document(userData.uid)
            .collection("exercise").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {

                    val item = exerciseItem(document.data.get("Name").toString(), document.id, false, "0", "0");
                    list.add(item);
                }
                exerciseRecyclerView.adapter = exerciseAdapter(list)
                exerciseRecyclerView.layoutManager = LinearLayoutManager(this)
                exerciseRecyclerView.setHasFixedSize(true)


            }
            .addOnFailureListener { exception ->
                //Log.w(TAG, "Error getting documents: ", exception)
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nowy_trening2)
        val treningName = intent.getStringExtra("name");
        init();
        getData();
        dodajCwiczenieButton.setOnClickListener {
            val MyIntent = Intent(applicationContext, DodajCwiczenie::class.java)
            MyIntent.putExtra("name", treningName);


            startActivity(MyIntent);
        }

        zapiszButtin.setOnClickListener {
            var newList = ArrayList<exerciseItem>();
            var fail = false;
            for(item in list) {
                if(item.checked) {
                    if(item.series!="0" && item.howMuch!="0") {
                        newList.add(item);
                    } else {
                        fail = true;
                        Toast.makeText(applicationContext, "Wypełnij wszystkie pola poprawnie", Toast.LENGTH_SHORT);

                    }
                }

            }

            if(!fail) {

                val toAdd = hashMapOf(
                    "Name" to treningName,
                    "exercise" to "newList"
                )
                Firebase.firestore.collection("users").document(userData.uid)
                    .collection("training").add(toAdd)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(
                            baseContext, " Dodano Trening",
                            Toast.LENGTH_SHORT
                        ).show();
                        setTraining(newList, documentReference.id);
                        startActivity(Intent(applicationContext, PokazTrening::class.java));
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            baseContext, " Nie udało się dodać Treningu",
                            Toast.LENGTH_SHORT
                        ).show();
                    }
            }
        }
    }

    private fun setTraining(toAddList: ArrayList<exerciseItem>, idTraining: String) {
        for(item in toAddList) {

            val toAdd = hashMapOf(
                "Name" to item.name,
                "Series" to item.series,
                "HowMuch" to item.howMuch
            )
            Firebase.firestore.collection("users").document(userData.uid)
                .collection("training").document(idTraining).collection("exercise").add(toAdd)
                .addOnSuccessListener { documentReference ->
//                    Toast.makeText(
//                        baseContext, " Dodano Trening",
//                        Toast.LENGTH_SHORT
//                    ).show();
                    //startActivity(Intent(applicationContext, Cardio::class.java));
                }
                .addOnFailureListener { e ->
//                    Toast.makeText(
//                        baseContext, " Nie udało się dodać Treningu",
//                        Toast.LENGTH_SHORT
//                    ).show();
                }
        }
    }
}
