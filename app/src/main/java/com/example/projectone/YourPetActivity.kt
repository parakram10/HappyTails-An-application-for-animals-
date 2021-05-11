package com.example.projectone

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class YourPetActivity : AppCompatActivity() {

    lateinit var imageView : ImageView
    lateinit var petName : EditText
    lateinit var petAge : EditText
    lateinit var petGender : EditText
    var uri : Uri? = null
    var s1 : String = ""
    lateinit var s2 : String
    lateinit var s3 : String

    private val firebaseUser : FirebaseUser = FirebaseAuth.getInstance().currentUser
    private val str : String? = firebaseUser.email
    var result: String? = null

    private val userRef : DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_pet)

        imageView = findViewById(R.id.yourPetImage)
        petName = findViewById(R.id.petName)
        petAge = findViewById(R.id.petAge)
        petGender = findViewById(R.id.petGender)

        if(str?.contains("@") == true){
            result = str?.substring(0,str.indexOf("."))
        }
        if(result?.contains(".") == true){
            result = result?.substring(0, result!!.indexOf("."))
        }


        val reference : DatabaseReference? = result?.let { userRef.child(it) }

        reference?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val yourPetModel : yourPetModel? = dataSnapshot.getValue(yourPetModel::class.java)
                Glide.with(this@YourPetActivity).load(yourPetModel?.getImage()).placeholder(R.drawable.ic_baseline_image_not_supported_24).into(imageView)
                petName.setText(yourPetModel?.getName())
                petAge.setText(yourPetModel?.getAge())
                petGender.setText(yourPetModel?.getGender())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("FragmentActivity.TAG", "onCancelled", databaseError.toException())
            }
        })

        s1 = petName.text.toString()
        s2 = petAge.text.toString()
        s3 = petGender.text.toString()

        imageView.setOnClickListener(){
            val intent : Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.setType("image/*")
            startActivityForResult(intent, 10)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, d: Intent?) {
        super.onActivityResult(requestCode, resultCode, d)

        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            uri = d?.data
            Glide.with(this).load(uri).into(imageView)
        }
    }

    fun saveDetail(view: View) {
        val yourPetModel : yourPetModel = yourPetModel(uri.toString(), petName.text.toString(), petAge.text.toString(), petGender.text.toString())
        if (result != null) {
            userRef.child(result!!).setValue(yourPetModel)
        }
        finish()
    }


    fun resetDetail(view: View) {

    }
}