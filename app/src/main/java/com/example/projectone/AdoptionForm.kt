package com.example.projectone

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class AdoptionForm : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var name : EditText
    lateinit var age : EditText
    lateinit var gender : EditText
    lateinit var location : EditText
    lateinit var pnumber : EditText

    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adoption_form)

        imageView = findViewById(R.id.petImage)
        name = findViewById(R.id.namePet)
        age = findViewById(R.id.age)
        gender = findViewById(R.id.gender)
        location = findViewById(R.id.landMark)
        pnumber = findViewById(R.id.phoneNumber)

        val btn : Button = findViewById(R.id.submitCase)

        val a : String = name.getText().toString()
        val b : String = age.getText().toString()
        val c : String = gender.getText().toString()
        val d : String = location.getText().toString()
        val e : String = pnumber.getText().toString()


        imageView.setOnClickListener(){
            val intent : Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.setType("image/*")
            startActivityForResult(intent, 10)
        }

        btn.setOnClickListener(){
            if(a!=null && b!=null && c!=null && d!=null && e!=null && uri!=null){


                intent.putExtra("name", name.text.toString())
                intent.putExtra("age", age.text.toString())
                intent.putExtra("gender", gender.text.toString())
                intent.putExtra("location", location.text.toString())
                intent.putExtra("pnumber", pnumber.text.toString())
                intent.putExtra("image", uri.toString())

                setResult(Activity.RESULT_OK, intent)
                finish()

            }else{
                Toast.makeText(this, "Please provide all the details !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, d: Intent?) {
        super.onActivityResult(requestCode, resultCode, d)

        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            uri = d?.data
            imageView.setImageURI(uri)
        }
    }
}