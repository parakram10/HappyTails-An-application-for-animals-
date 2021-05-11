package com.example.projectone

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class newFeedForm : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_feed_form)

        val editText : EditText = findViewById(R.id.writeFeed)
        val btn : Button = findViewById(R.id.saveFeed)
        val et : EditText = findViewById(R.id.writeName)

        auth = FirebaseAuth.getInstance()
        var name : String = ""

        if(auth.currentUser != null){
            name = auth.currentUser.email
        }else{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        btn.setOnClickListener(){
            val str : String = editText.text.toString()

            if(str != "" && name != ""){

                val feedModel : feedModel = feedModel(name, R.drawable.ic_round_person_24, str, false, "0", "0", "0", "0")

                val alertDialog : AlertDialog.Builder = AlertDialog.Builder(this)

                alertDialog.setTitle("Are you sur you want to save the feed?")
                alertDialog.setPositiveButton("Yes", DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->



                    intent.putExtra("name", name)
                    intent.putExtra("detail", str)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                })

                alertDialog.setNegativeButton("No", DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->
                    setResult(2000, intent)
                    finish()
                })

                alertDialog.show()
            }

            else{
                Toast.makeText(this, "Please fill bith the details!",Toast.LENGTH_SHORT).show()
            }

        }
    }

}