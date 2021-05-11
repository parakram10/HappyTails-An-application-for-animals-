package com.example.projectone

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AdoptionFragment : Fragment() {

    lateinit var adapter: AdoptionAdapter

    private val userRef : DatabaseReference = FirebaseDatabase.getInstance().reference
    private var mPreferences: SharedPreferences? = null
    private val sharedPrefFile = "com.example.android.happyTails"
    var count : Int? = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_adoption, container, false)

        val recyclerView : RecyclerView = view.findViewById(R.id.adoptionRecyclerView)

        mPreferences = this.activity?.getSharedPreferences(sharedPrefFile, AppCompatActivity.MODE_PRIVATE)

        count = mPreferences?.getInt("adInt", 0)

        val options : FirebaseRecyclerOptions<adoptionModel> = FirebaseRecyclerOptions.Builder<adoptionModel>()
            .setQuery(userRef.child("adoption"), adoptionModel::class.java)
            .build()

        adapter = AdoptionAdapter(this.requireContext(), options)

        recyclerView.layoutManager = GridLayoutManager(this.requireContext(), 2)
        recyclerView.adapter =adapter

        val fab : FloatingActionButton = view.findViewById(R.id.Adoption)
        fab.setOnClickListener(){
            val intent : Intent = Intent(this.requireContext(), AdoptionForm::class.java)
            startActivityForResult(intent, 4000)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 4000 && resultCode == Activity.RESULT_OK){
            val a : String? = data?.getStringExtra("name")
            val b : String? = data?.getStringExtra("age")
            val c : String? = data?.getStringExtra("gender")
            val d : String? = data?.getStringExtra("location")
            val e : String? = data?.getStringExtra("pnumber")
            val f : String? = data?.getStringExtra("image")

            Log.d("datasfssfdfsdsdfsdfsfsd", "name $a  age $b  gender   $c")

            val adoptionModel : adoptionModel = adoptionModel(f,a,b,c,d,e)
            userRef.child("adoption").child("username$count").setValue(adoptionModel)
            count = count?.plus(1)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onPause() {
        super.onPause()
        val preferenceEditor : SharedPreferences.Editor? = mPreferences?.edit()

        count?.let { preferenceEditor?.putInt("adInt", it+1) }
        preferenceEditor?.apply()
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}