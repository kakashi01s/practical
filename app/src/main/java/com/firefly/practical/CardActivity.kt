package com.firePfly.practical

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.firefly.practical.MainActivity
import com.firefly.practical.Posts
import com.firefly.practical.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class CardActivity : AppCompatActivity() {
    var CardImage : ImageButton? = null
    var Addbutton : Button? = null
    var CardName : TextView? = null
    var IMAGE_PICK_CODE = 1000
    var db = FirebaseDatabase.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser!!.uid
    var progress : ProgressBar? = null
    private var filePath: Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        initViews()
        CardImage!!.setOnClickListener {
            pickImageFromGallery()
        }

    }

    private fun initViews() {
        CardImage = findViewById(R.id.Post_Item)
        Addbutton = findViewById(R.id.PostButton)
        CardName = findViewById(R.id.post_caption)
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            // I'M GETTING THE URI OF THE IMAGE AS DATA AND SETTING IT TO THE IMAGEVIEW
            CardImage!!.layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            CardImage!!.layoutParams.width = ConstraintLayout.LayoutParams.WRAP_CONTENT
            CardImage!!.setImageURI(data?.data)
            filePath = data?.data
            Addbutton!!.setOnClickListener {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading New Feed....")
                progressDialog.show()
                val StorageRef = FirebaseStorage.getInstance().reference
                    .child("Cards/${FirebaseAuth.getInstance().currentUser?.uid}"+ System.currentTimeMillis().toString())
                val uploadpostimg = StorageRef.putFile(filePath!!)
                // Register observers to listen for when the download is done or if it fails
                uploadpostimg.addOnFailureListener {
                    Log.d("uploadpost", "Failed to upload")
                    // Handle unsuccessful uploads
                }.addOnSuccessListener { taskSnapshot ->
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                    // ...
                    val urlTask = uploadpostimg.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        StorageRef.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUri = task.result
                            val postid = "${UUID.randomUUID()}"
                            val post = Posts(uid, "$postid",
                                downloadUri.toString(),
                                CardName!!.text.toString(),0,0)
                            db.getReference("Cards").child("$uid").setValue(post).addOnCompleteListener {
                                Toast.makeText(this, "Card Uploaded", Toast.LENGTH_SHORT).show()
                                Log.d("uploadpost", downloadUri.toString())
                                var intent = Intent(this , MainActivity::class.java)
                                startActivity(intent)
                                progressDialog.dismiss()
                            }
                        } else {
                            // Handle failures
                            // ...
                            Log.d("uploadpost", "Img download failed")
                        }
                    }
                }
            }
        }
    }
}