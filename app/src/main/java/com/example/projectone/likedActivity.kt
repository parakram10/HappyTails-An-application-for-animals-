package com.example.projectone

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*


class likedActivity : AppCompatActivity() {

    lateinit var adaptor : likedAdapter
    lateinit var recyclerView : RecyclerView
    private val userRef : DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liked)

        recyclerView = findViewById(R.id.likedRecyclerView)

        val options : FirebaseRecyclerOptions<feedModel> = FirebaseRecyclerOptions.Builder<feedModel>()
            .setQuery(userRef.child("liked"), feedModel::class.java)
            .build()

        adaptor = likedAdapter(options, this)

        val linearLayoutManager : LinearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adaptor

        val ref = FirebaseDatabase.getInstance().reference
        val applesQuery: Query = ref.child("liked")

        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {

                applesQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val feedModel : feedModel = adaptor.getItemAtPosition(viewHolder.adapterPosition)
                        for(i in dataSnapshot.children){
                            if(i.child("name").value == feedModel.getName().toString()){
                                i.ref.removeValue()
                            }

                    }

                }
                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })

                adaptor.notifyItemRemoved(viewHolder.layoutPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

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