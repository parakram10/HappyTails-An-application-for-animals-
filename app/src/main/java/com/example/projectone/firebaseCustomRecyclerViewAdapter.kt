package com.example.projectone

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class firebaseCustomRecyclerViewAdapter(options: FirebaseRecyclerOptions<feedModel>, context: Context) : FirebaseRecyclerAdapter<feedModel, firebaseCustomRecyclerViewAdapter.ViewHolder>(options){

    private val context = context

    private val userRef : DatabaseReference = FirebaseDatabase.getInstance().reference


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val nameText : TextView = itemView.findViewById(R.id.personName)
        private val desc : TextView = itemView.findViewById(R.id.feedDescription)
        val imageView : ImageView = itemView.findViewById(R.id.personImage)
        private val favouriteFeed : ImageButton = itemView.findViewById(R.id.favouriteFeed)
        private val d : TextView = itemView.findViewById(R.id.helpDescription)
        private val animalImage : ImageView = itemView.findViewById(R.id.HelpAnimalImage)
        private val loc : TextView = itemView.findViewById(R.id.laaandMark)
        private val helpBtn : TextView = itemView.findViewById(R.id.helpBtn)

        private val c1 : ConstraintLayout = itemView.findViewById(R.id.layout1)
        private val c2 : ConstraintLayout = itemView.findViewById(R.id.layout2)
        lateinit var data: feedModel
        lateinit var context: Context
        lateinit var user : DatabaseReference

        init{
            favouriteFeed.setOnClickListener(this)
            helpBtn.setOnClickListener(this)
        }

        fun setValues(data : feedModel,position: Int, context: Context, userRef : DatabaseReference){

            Log.d("dataAda", data.toString())
            this.context = context
            if(data.getName() == "0"){
                c2.visibility = View.VISIBLE
                c1.visibility = View.GONE
                Glide.with(context).load(data.getImage()).placeholder(R.mipmap.dogcustom).into(animalImage)
                d.text = data.getDescription1()
            }
            else {
                c1.visibility = View.VISIBLE
                c2.visibility = View.GONE
                nameText.text = data.getName()
                desc.text = data.getDescription()
            }
            this.data = data
            this.user = userRef
        }

        override fun onClick(v: View?) {
            if (v?.id == R.id.favouriteFeed) {
                favouriteFeed.setImageResource(R.drawable.heart4)
                val feedModel : feedModel = data
                user.child("liked").child("username$adapterPosition").setValue(feedModel)
            }

            else if(v?.id == R.id.helpBtn){
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "0987654321"))
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.feed_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: feedModel) {
        Log.d("data", model.getLanndmark().toString()+"  "+model.getDescription1())
        holder.setValues(model, position, context, userRef)
    }

}