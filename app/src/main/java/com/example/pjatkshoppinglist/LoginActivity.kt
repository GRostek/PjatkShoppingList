package com.example.pjatkshoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

private lateinit var auth: FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        button.setOnLongClickListener{
            auth.createUserWithEmailAndPassword(editTextLogin.text.toString(), editTextPassword.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this, getString(R.string.user_registered), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, getString(R.string.registration_failed), Toast.LENGTH_SHORT).show()
                    }
                }

            true
        }

        button.setOnClickListener{
            auth.signInWithEmailAndPassword(editTextLogin.text.toString(), editTextPassword.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this, getString(R.string.login_successful), Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        //auth.currentUser
                    } else {
                        Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
                    }
                }


        }
    }
}