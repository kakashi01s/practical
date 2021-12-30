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

class MainActivity : AppCompatActivity() {
    var email : TextView? = null
    var password : TextView? = null
    var buttonreg : Button? =null
    var alred : TextView? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initviews()
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if(currentUser != null){
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        buttonreg!!.setOnClickListener {
            var eml = email!!.text.toString()
            var pass = password!!.text.toString()
            auth.createUserWithEmailAndPassword(eml, pass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("Reg", "createUserWithEmail:success")
                        val user = auth.currentUser
                        var int = Intent(this, HomeActivity::class.java)
                        startActivity(int)
                    } else {
                        Log.w("Reg", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

    private fun initviews() {
        email = findViewById(R.id.registerEmail)
        password = findViewById(R.id.registerPassword)
        buttonreg = findViewById(R.id.registerButton)
        alred = findViewById(R.id.alred)

    }
}