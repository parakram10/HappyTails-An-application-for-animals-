package com.example.projectone

import android.graphics.Bitmap
import java.io.Serializable

class feedModel : Serializable {

    private var name : String? = null
    private var profilePic : Int? = null
    private var description : String? = null
    private var liked : Boolean? = null
    private var image : String? = null
    private var description1 : String? = null
    private var landmark : String? = null
    private var phone : String? = null

    constructor()

    constructor(name: String?, profilePic: Int?, description: String?, liked: Boolean?, img: String?, desc: String?, landmark: String?, pNo: String?){
        this.name = name
        this.profilePic = profilePic
        this.description = description
        this.description1 = desc
        this.landmark = landmark
        this.liked = liked
        this.image = img
        this.phone = pNo
    }

    fun getName() : String? {
        return name
    }

    fun getProfilePic() : Int? {
        return profilePic
    }

    fun getDescription() : String? {
        return description
    }

    fun getLiked() : Boolean? {
        return liked
    }

    fun setLiked(l : Boolean){
        this.liked = l
    }

    fun getImage() : String? {
        return image
    }

    fun getDescription1() : String? {
        return description1
    }

    fun getLanndmark() : String? {
        return landmark
    }

    fun getPhoneNumber() : String? {
        return phone
    }
}