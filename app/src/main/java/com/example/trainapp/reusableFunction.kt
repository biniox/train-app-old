package com.example.trainapp

import android.text.BoringLayout
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_dodaj_pomiar.*
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*


//Obiekty i klasy z danymi uzytkownika
object userData {
    var userEmail : String = "email";
    var uid : String = "uid";
    var name : String = "name";
}

data class pomiaryItem(val data: String, val waga: String, val uda: String, val brzuch: String, val klatka: String, val biceps: String ) {}
data class cardioItem(val name: String, val id: String ) {}
data class daysItem(val date: String, val feel: String, val sleep: String, val diet: String ) {}
data class calendarItem(val date: String, val day: String ) {}
data class calendarTrainItem(val name: String, val id: String, val cardio: Boolean ) {}
data class pokazRekordItem(val date: String, val ile: String ) {}
data class trainingItem(val name: String, val id: String ) {}
data class CardioAndTrainingItem(val name: String, val id: String, val cardio: Boolean ) {}
data class cardioSzczegolyItem(val date: String, val distance: String, val hour: String, val min: String, val speed: String) {}
data class exerciseItem(val name: String, val id: String, var checked: Boolean, var series: String, var howMuch: String) {
     fun changeChecked() { checked = !checked};
    fun setseries(p1: String) { series = p1;}
    fun sethowMuch(p1: String) { howMuch = p1;}

}data class trainingShowItem(val name: String, val id: String, val idTrain: String, var series: String, var howMuch: String, var record: String) {
    fun setseries(p1: String) { series = p1;}
    fun sethowMuch(p1: String) { howMuch = p1;}

}



//Pobieranie podstawowych danych
fun init() {
    if(checkAuth()) {
      getUserData();
    }
}

fun getUserData() {
    val user = FirebaseAuth.getInstance().currentUser
    if (user != null) {
        userData.name  = user.displayName.toString();
        userData.uid = user.uid;
        userData.userEmail = user.email.toString();
    } else {

    }

}
// wylogowanie
fun signOut() {
    FirebaseAuth.getInstance().signOut();
}

// checkAuth sprawdza czy user zalogowany
fun checkAuth() : Boolean {

    val user = FirebaseAuth.getInstance().currentUser
    if (user != null) {
        return true;
    } else {
        return false
    }
}

// pobieranie daty w formacie dd-mm-year z long
fun convertLongTimetoString(tConvert: Long) : String {
//    val tmp = Date(tConvert);
//    val tmp2 = Timestamp(tConvert);
//    val tmp3 = Calendar.getInstance();
//    val tmp4 = tmp2.valueOf
    var toConvert = tConvert;
    val year: String;
    val month: String;
    val day: String;
    val oneYearSecond: Long = 365*24*3600;
    var tmp: String;
    var i = 1970
    while(toConvert>oneYearSecond || ((i+2)%4).equals(0) && toConvert>(oneYearSecond+(24*3600))) {
        if((i%4).equals(0)) toConvert -= 366*24*3600
        else toConvert -= oneYearSecond;
        i++;
    }
    year = i.toString();


    val monthSecond: Array<Long> = arrayOf(31*24*3600,28*24*3600,31*24*3600,30*24*3600,31*24*3600,30*24*3600,31*24*3600,31*24*3600,30*24*3600,31*24*3600,30*24*3600,31*24*3600 )
    if(((i+2)%4).equals(0)) monthSecond[1] = 29*24*3600;
    i = 1;
    while(toConvert>monthSecond[i-1]) {
        toConvert -= monthSecond[i-1];
        i++;
    }

    if(i<10)month = "0" + i;
    else month = i.toString();
    i=1;
    while(toConvert>24*3600) {
        i++;
        toConvert -= 24*3600;
    }
    if(i<10) day = "0" + i + "";
    else day = i.toString();
    tmp = day + "-" + month + "-" + year;
    return tmp


}

fun convertStringToDate(toConvert: String) : Date {
    return SimpleDateFormat("dd-MM-yyyy").parse(toConvert);
}