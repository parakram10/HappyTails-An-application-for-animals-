package com.example.projectone

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.snackbar.Snackbar

class AdoptionAdapter(context: Context, options: FirebaseRecyclerOptions<adoptionModel>) : FirebaseRecyclerAdapter<adoptionModel, AdoptionAdapter.Viewholder>(options) {

    val context = context


    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val imageView : ImageView = itemView.findViewById(R.id.petImage)
        val petName : TextView = itemView.findViewById(R.id.petName)
        val petAge : TextView = itemView.findViewById(R.id.petAge)
        val petGender : TextView = itemView.findViewById(R.id.petGender)
        val location : TextView = itemView.findViewById(R.id.location)
        val adoptNow : TextView = itemView.findViewById(R.id.adoptNow)
        lateinit var context: Context
        lateinit var data : adoptionModel

        fun setData(curr : adoptionModel,position: Int, context: Context){
            this.context = context
            this.data = curr

            Glide.with(context).load(data.getImage()).placeholder(R.drawable.ic_baseline_image_not_supported_24).into(imageView)
            petAge.text = ("${curr.getAge()} yrs")
            petName.text = curr.getName()
            petGender.text = curr.getGender()
            location.text = curr.getLocation()
        }

        init{
            adoptNow.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "0987654321"))
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.adoption_grid_layout, parent, false)
        return Viewholder(view)
    }


    override fun onBindViewHolder(holder: Viewholder, position: Int, model: adoptionModel) {
        holder.setData(model, position, context)
    }

}