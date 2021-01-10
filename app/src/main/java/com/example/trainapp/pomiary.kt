package com.example.trainapp

import android.content.Intent
import android.os.Bundle
import android.os.DropBoxManager
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_dodaj_pomiar.*
import kotlinx.android.synthetic.main.activity_pomiary.*
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.util.*
import kotlin.collections.ArrayList


class pomiary : AppCompatActivity() {

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
        val list = ArrayList<pomiaryItem>()
        Firebase.firestore.collection("users").document(userData.uid)
            .collection("measurement").orderBy("Data" , Query.Direction.DESCENDING).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                        val gettedDate = document.get("Data") as com.google.firebase.Timestamp;
                    val item = pomiaryItem(
                        convertLongTimetoString(gettedDate.seconds), document.data.get("Waga").toString(),
                        document.data.get("Uda").toString(), document.data.get("Brzuch").toString(),
                        document.data.get("Klatka").toString(), document.data.get("Biceps").toString());
                    list += item
                }
                RecyclerViewPomiary.adapter = pomiaryAdapter(list)
                RecyclerViewPomiary.layoutManager = LinearLayoutManager(this)
                RecyclerViewPomiary.setHasFixedSize(true)

                //charts

                //Waga
                var yValues: ArrayList<Entry> = ArrayList<Entry>();
                var i: Int = 0;
                for(item in list) {
                    yValues.add(Entry(i.toFloat(), item.waga.toFloat()));
                    i++;
                }
                var set1 = LineDataSet(yValues, "Waga");
                var dataSets: ArrayList<ILineDataSet> = ArrayList<ILineDataSet>();
                dataSets.add(set1);
                var data: LineData = LineData(dataSets);
                wagaChart.setData(data);



                //Uda
                var yValues1: ArrayList<Entry> = ArrayList<Entry>();
                var dataSets1: ArrayList<ILineDataSet> = ArrayList<ILineDataSet>();
                i= 0;
                for(item in list) {
                    yValues1.add(Entry(i.toFloat(), item.uda.toFloat()));
                    i++;
                }
                set1 = LineDataSet(yValues1, "Uda");
                dataSets1.add(set1);
                data= LineData(dataSets1);
                udaChart.setData(data);

                //brzuch
                var yValues2: ArrayList<Entry> = ArrayList<Entry>();
                var dataSets2: ArrayList<ILineDataSet> = ArrayList<ILineDataSet>();
                i= 0;
                for(item in list) {
                    yValues2.add(Entry(i.toFloat(), item.brzuch.toFloat()));
                    i++;
                }
                set1 = LineDataSet(yValues2, "Brzuch");
                dataSets2.add(set1);
                data= LineData(dataSets2);
                brzuchChart.setData(data);

                //Klatka
                var yValues3: ArrayList<Entry> = ArrayList<Entry>();
                var dataSets3: ArrayList<ILineDataSet> = ArrayList<ILineDataSet>();
                i= 0;
                for(item in list) {
                    yValues3.add(Entry(i.toFloat(), item.klatka.toFloat()));
                    i++;
                }
                set1 = LineDataSet(yValues3, "Klatka Piersiowa");
                dataSets3.add(set1);
                data= LineData(dataSets3);
                klatkaChart.setData(data);

                //brzuch
                var yValues4: ArrayList<Entry> = ArrayList<Entry>();
                var dataSets4: ArrayList<ILineDataSet> = ArrayList<ILineDataSet>();
                i= 0;
                for(item in list) {
                    yValues4.add(Entry(i.toFloat(), item.biceps.toFloat()));
                    i++;
                }
                set1 = LineDataSet(yValues4, "Biceps");
                dataSets4.add(set1);
                data= LineData(dataSets4);
                bicepsChart.setData(data);




            }
            .addOnFailureListener { exception ->
                //Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pomiary)
        if(!checkAuth()) startActivity(Intent(applicationContext, login::class.java));
        init();
        //tworze recyclerView
        //val exampleList = generateList(3)
        getData();
//        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.RecyclerViewPomiary)
//        recyclerView.adapter = RecyclerAdapter(this)
//        recyclerView.layoutManager = LinearLayoutManager(this)

        ////////////////////////
        pomiaryButton.setOnClickListener {
            startActivity(Intent(applicationContext, DodajPomiar::class.java));
        }
    }
}
