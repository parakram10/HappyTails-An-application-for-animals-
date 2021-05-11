package com.example.projectone

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class FeedFragment : Fragment() {

    private var mPreferences: SharedPreferences? = null
    private val sharedPrefFile = "com.example.android.happyTails"
    var count : Int? = 0

    lateinit var adaptor : firebaseCustomRecyclerViewAdapter

    private val userRef : DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_feed, container, false)

        val recyclerView : RecyclerView = view.findViewById(R.id.feedRecyclerView)

        mPreferences = this.activity?.getSharedPreferences(sharedPrefFile, AppCompatActivity.MODE_PRIVATE)

        count = mPreferences?.getInt("feedInt", 0)

        val options : FirebaseRecyclerOptions<feedModel> = FirebaseRecyclerOptions.Builder<feedModel>()
            .setQuery(userRef.child("User"), feedModel::class.java)
            .build()

        adaptor = firebaseCustomRecyclerViewAdapter(options, this.requireContext())

        val linearLayoutManager : LinearLayoutManager = LinearLayoutManager(this.requireContext())
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adaptor

        val fab : FloatingActionButton = view.findViewById(R.id.feedFab)
        fab.setOnClickListener(){
            val intent : Intent = Intent(this.requireContext(), newFeedForm::class.java)
            startActivityForResult(intent, 1000)
        }

        val fab2 : FloatingActionButton = view.findViewById(R.id.helpFeed)
        fab2.setOnClickListener(){
            val intent : Intent = Intent(this.requireContext(), helpForm::class.java)
            startActivityForResult(intent, 3000)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1000 ){
            if(resultCode == Activity.RESULT_OK){
                val str: String = data?.getStringExtra("detail")!!
                val name: String = data.getStringExtra("name")!!

               val feedModel : feedModel = feedModel(name, R.drawable.ic_round_person_24, str, false, "0", "0", "0", "0")

                userRef.child("User").child("username$count").setValue(feedModel)
                count = count?.plus(1)
                adaptor.notifyDataSetChanged()
            }
            else if(resultCode == 200){
                Toast.makeText(this.requireContext(), "No data updated", Toast.LENGTH_SHORT).show()
            }
        }

        if(requestCode == 3000 && resultCode == Activity.RESULT_OK){
                val issue : String? = data?.getStringExtra("issue")
                val land : String? = data?.getStringExtra("land")
                val num : String? = data?.getStringExtra("num")
                val img : String? = data?.getStringExtra("img")

                Log.d("dataHElp", "issue   ${issue}\nloc   ${land}\nnum    ${num}")

                val feedModel : feedModel = feedModel("0", 0, "0", false, img, issue, land, num)
                userRef.child("User").child("username$count").setValue(feedModel)
                count = count?.plus(1)
                adaptor.notifyDataSetChanged()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onPause() {
        super.onPause()
        val preferenceEditor : SharedPreferences.Editor? = mPreferences?.edit()

        count?.let { preferenceEditor?.putInt("feedInt", it+1) }
        preferenceEditor?.apply()
    }

    override fun onStart() {
        super.onStart()
        adaptor.startListening()
    }

    override fun onStop() {
        super.onStop()
        adaptor.stopListening()
    }

}