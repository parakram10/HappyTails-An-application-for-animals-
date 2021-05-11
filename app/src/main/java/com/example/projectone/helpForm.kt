package com.example.projectone

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class helpForm : AppCompatActivity() {

    lateinit var progressBar : ProgressBar
    lateinit var imageView : ImageView
    var uri: Uri? = null

    val storage : FirebaseStorage = FirebaseStorage.getInstance()
    val storageReference : StorageReference = storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_form)

        imageView = findViewById(R.id.petImage)
        val issueDesc : EditText = findViewById(R.id.issueDescription)
        val landMark : EditText = findViewById(R.id.landMark)
        val pNumber : EditText = findViewById(R.id.phoneNumber)
        val submit : Button = findViewById(R.id.submitCase)
        progressBar = findViewById(R.id.progressBar)

        imageView.setOnClickListener(){
            val intent : Intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.setType("image/*")
            startActivityForResult(intent, 10)
        }

        submit.setOnClickListener(){
            val iss : String = issueDesc.text.toString()
            val lm : String = landMark.text.toString()
            val num : String = pNumber.text.toString()


            if(uri != null && iss!=null && lm!=null && num!=null){

                intent.putExtra("issue", issueDesc.text.toString())
                intent.putExtra("land", landMark.text.toString())
                intent.putExtra("num", pNumber.text.toString())
                intent.putExtra("img", uri.toString())

                Log.d("dataHElp", "issue   ${issueDesc.text}\nloc   ${landMark.text}\nnum    ${pNumber.text}")
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            else{
                Toast.makeText(this, "Please fill all the details.", Toast.LENGTH_SHORT).show()
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