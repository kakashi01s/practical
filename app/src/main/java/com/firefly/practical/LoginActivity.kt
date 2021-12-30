package com.firefly.practical

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.net.InterfaceAddress

class LoginActivity : AppCompatActivity() {
    var email : TextView? = null
    var password : TextView? = null
    var buttonreg : Button? =null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initviews()
        auth = Firebase.auth

        buttonreg!!.setOnClickListener {
            var eml = email!!.text.toString()
            var pass = password!!.text.toString()
            auth.signInWithEmailAndPassword(eml, pass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Login", "signInWithEmail:success")
                        val user = auth.currentUser
                        var intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Login", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }


    }

    private fun initviews() {
        email = findViewById(R.id.LoginEmail)
        password = findViewById(R.id.LoginPassword)
        buttonreg = findViewById(R.id.LoginButton)

    }
}