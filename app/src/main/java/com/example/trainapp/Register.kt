package com.example.trainapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.*


class Register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;

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

    fun createAccount(email : String, password : String) {
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if(task.isSuccessful) {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    val uid = user.uid;


                    Toast.makeText(
                        baseContext, "Zarejestrowano pomyślnie",
                        Toast.LENGTH_SHORT
                    ).show();
                    val data1 = hashMapOf(
                        "Name" to nameTextArea.text.toString()
                    )
                    val db = Firebase.firestore
                    Firebase.firestore.collection("users").document(uid).set(data1)
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(
                                baseContext, " udało się dodać do bazy",
                                Toast.LENGTH_SHORT
                            ).show();

                            startActivity(Intent(applicationContext, login::class.java));
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                baseContext, " Nie udało się dodać do bazy",
                                Toast.LENGTH_SHORT
                            ).show();
                        }
                }
                GlobalScope.launch {
                    delay(3000L)
                    var reload: Intent = Intent(applicationContext, login::class.java);
                    //startActivity(reload);
                }

            } else {
                Toast.makeText(baseContext, "Nie udało się , ",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        loginButton.setOnClickListener {
            if(passwordTextArea.length()!=0 && password2.length()!=0 && emailTextArea.length()!=0 && nameTextArea.length()!=0 &&
                emailMsg.visibility==TextView.INVISIBLE && firstPasswordMsg.visibility==TextView.INVISIBLE && secondPasswordMsg.visibility==TextView.INVISIBLE)
            createAccount(emailTextArea.text.toString(), passwordTextArea.text.toString());
        }
        passwordTextArea.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(passwordTextArea.length() == 0) firstPasswordMsg.visibility = TextView.INVISIBLE;
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(passwordTextArea.length() < 8) {
                    firstPasswordMsg.visibility = TextView.VISIBLE;
                }

                var countBigLetter : Int = 0;
                var countNumber : Int = 0;

                for(i:Char in passwordTextArea.text) {
                    if(i.toInt()>64 && i.toInt()<91) countBigLetter++;
                    if(i.toInt()>47 && i.toInt()<58) countNumber++;
                }

                if(countBigLetter>0 && countNumber>0 && passwordTextArea.length()>7) firstPasswordMsg.visibility = TextView.INVISIBLE; /// Duża litera i  znak specjalny
            }



        });

        password2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(password2.length() == 0) secondPasswordMsg.visibility = TextView.INVISIBLE;
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(passwordTextArea.text.toString().equals(password2.text.toString())) secondPasswordMsg.visibility = TextView.INVISIBLE;
                else secondPasswordMsg.visibility = TextView.VISIBLE;

            }



        });


        emailTextArea.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(emailTextArea.length() == 0) emailMsg.visibility = TextView.INVISIBLE;
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                emailMsg.visibility = TextView.VISIBLE;
                var fmalpa = false;
                var fkropka = false
                for(i:Char in emailTextArea.text) {
                    if(i == '@') {
                        fmalpa = true;

                    }

                    if(i == '.') {
                        fkropka = true;

                    }

                    if(fmalpa && fkropka) {
                        emailMsg.visibility = TextView.INVISIBLE;
                    }
                }
            }
        })



    }
}
