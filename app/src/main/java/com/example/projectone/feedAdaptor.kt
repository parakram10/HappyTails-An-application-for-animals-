package com.example.projectone

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class feedAdaptor(context: Context, arr : ArrayList<feedModel>) : RecyclerView.Adapter<feedAdaptor.ViewHolder>() {

    private val context = context
    private val arr = arr

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val nameText : TextView = itemView.findViewById(R.id.personName)
        val desc : TextView = itemView.findViewById(R.id.feedDescription)
        val imageView : ImageView = itemView.findViewById(R.id.personImage)
        val favouriteFeed : ImageButton = itemView.findViewById(R.id.favouriteFeed)

        val animalImage : ImageView = itemView.findViewById(R.id.HelpAnimalImage)
        val location : TextView = itemView.findViewById(R.id.landMark)
        val helpBtn : TextView = itemView.findViewById(R.id.helpBtn)

        val c1 : ConstraintLayout = itemView.findViewById(R.id.layout1)
        val c2 : ConstraintLayout = itemView.findViewById(R.id.layout2)

        lateinit var cxt : Context
        lateinit var arr : ArrayList<feedModel>
        var pos: Int = 0
        lateinit var data: feedModel

        init{
            favouriteFeed.setOnClickListener(this)
            helpBtn.setOnClickListener(this)
        }

        fun setValues(data : feedModel, context: Context, arr: ArrayList<feedModel>,  position: Int){
            this.cxt = context
            if(data.getName() == "0"){
                c1.visibility = View.GONE
//                val uri = Uri.parse(data.getImage())
//                if(uri != null){
//                    val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
//                    animalImage.setImageBitmap(bitmap)
//                }
//                else{
                    animalImage.setImageResource(R.drawable.heart4)
//                }
                location.text = data.getLanndmark()
            }
            else {
                c2.visibility = View.GONE
                nameText.text = data.getName()
                desc.text = data.getDescription()
                data.getProfilePic()?.let { imageView.setImageResource(it) }
            }
            this.arr = arr
            this.pos = position
            this.data = data
        }

        override fun onClick(v: View?) {
            if(v?.id == R.id.favouriteFeed){
                if (favouriteFeed.contentDescription == "like") {
                    arr[pos].setLiked(true)
                    favouriteFeed.setImageResource(R.drawable.heart4)
                    Toast.makeText(cxt, "You liked this feed", Toast.LENGTH_SHORT).show()
                    favouriteFeed.contentDescription = "unlike"
                } else {
                    arr[pos].setLiked(false)
                    favouriteFeed.setImageResource(R.drawable.like1)
                    Toast.makeText(cxt, "Like Removed!", Toast.LENGTH_SHORT).show()
                    favouriteFeed.contentDescription = "like"
                }
            }

            else if(v?.id == R.id.helpBtn){
//                val intent : Intent = Intent(cxt, HelpDetail::class.java)
//                val bundle : Bundle = Bundle()
//                bundle.putSerializable("data", data)
//                cxt.startActivity(intent, bundle)

//                val alertDialog : AlertDialog.Builder = AlertDialog.Builder(cxt)
//                val viewGroup : ViewGroup = itemView.findViewById(android.R.id.content)
//                val v : View = LayoutInflater.from(cxt).inflate(R.layout.help_detail, viewGroup, false)
//
//                val iv : ImageView = v.findViewById(R.id.imageOfAnimal)
//                val tv1 : TextView = v.findViewById(R.id.descritpion)
//                val tv2 : TextView = v.findViewById(R.id.address)
//                val tv3 : TextView = v.findViewById(R.id.phoneNumber)
//
//                iv.setImageBitmap(data.getImage())
//                tv1.text = data.getDescription1()
//                tv2.text = ("Address : ${data.getLanndmark()}")
//                tv3.text = "Phone number : ${data.getPhoneNumber()}"
//
//                alertDialog.setView(v)
//                alertDialog.setCancelable(true)
//
//                val builder: AlertDialog = alertDialog.create()
//                builder.show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.feed_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return (arr.size)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val curr : feedModel = arr[position]
        holder.setValues(curr, context, arr, position)
    }

}