package com.firefly.practical

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.TintContextWrapper
import coil.load
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ViewCardActivity : AppCompatActivity() {
    var imgg : ImageView? = null
    var imgtext : TextView? = null
    var uid = FirebaseAuth.getInstance().uid.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_card)
        initViews()
        var databaseRef: DatabaseReference? = null
        databaseRef = FirebaseDatabase.getInstance().getReference("Cards")
        databaseRef.child(uid).get().addOnSuccessListener {
            if (it.exists())
            {
                val uimage = it.child("postImg").value
                val uname = it.child("postCaption").value
                imgg!!.load(uimage.toString())
                imgtext!!.text = uname.toString()


            }
        }
    }

    private fun initViews() {
        imgg = findViewById(R.id.imagefromdb)
        imgtext = findViewById(R.id.imgtext)
    }
}