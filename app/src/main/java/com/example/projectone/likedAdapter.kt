package com.example.projectone

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class likedAdapter(options: FirebaseRecyclerOptions<feedModel>, context : Context) : FirebaseRecyclerAdapter<feedModel, likedAdapter.ViewHolder>(options) {

    val context = context
    lateinit var feedModel : feedModel

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var person : TextView = itemView.findViewById(R.id.personName)
        private var desc : TextView = itemView.findViewById(R.id.feedDescription)
        lateinit var context: Context

        fun setData(feedModel: feedModel, context: Context){
            this.context = context

            person.text = feedModel.getName()
            desc.text = feedModel.getDescription()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.like_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: feedModel) {
        holder.setData(model, context)
        this.feedModel = model
    }

    fun getItemAtPosition(position: Int) : feedModel{
        return feedModel
    }

}