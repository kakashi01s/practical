package com.firefly.practical

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.firePfly.practical.CardActivity

class HomeActivity : AppCompatActivity() {
    var addcrd : Button? =null
    var viewcrrd : Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initViews()
        addcrd!!.setOnClickListener {
            var intent1 = Intent(this, CardActivity::class.java)
            startActivity(intent1)
        }
        viewcrrd!!.setOnClickListener {
            var int2 = Intent(this, ViewCardActivity::class.java)
            startActivity(int2)
        }
    }

    private fun initViews() {
        addcrd = findViewById(R.id.addcard)
        viewcrrd = findViewById(R.id.ViewCard)
    }
}