package com.example.trainapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;


    fun signIn(email: String, pass: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Log.d(TAG, "Zalogowano")
                    Toast.makeText(baseContext, "Zalogowano",
                        Toast.LENGTH_SHORT).show()
                    GlobalScope.launch {
                        delay(1000L)
                        var reload: Intent = Intent(applicationContext, com.example.trainapp.login::class.java);
                        startActivity(reload);
                    }

                } else {

                    //Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Nieprawidłowy login lub hasło",
                        Toast.LENGTH_SHORT).show()

                }


            }
    }
    override fun onStart() {
        super.onStart();
        if(checkAuth()) startActivity(Intent(applicationContext, Home::class.java));
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener {
            if(emailTextArea.length()!=0 && passwordTextArea.length()!=0) {
                signIn(emailTextArea.text.toString(), passwordTextArea.text.toString());
            }
        }
        logButton.setOnClickListener {
            startActivity(Intent(applicationContext, Register::class.java));

        }
    }
}
