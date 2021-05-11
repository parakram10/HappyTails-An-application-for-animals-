package com.example.projectone

import java.io.Serializable

class adoptionModel : Serializable{

    private var image : String? = null
    private var name : String? = null
    private var age : String? = null
    private var gender : String? = null
    private var location : String? = null
    private var pnumber : String? = null

    constructor()

    constructor(image : String?, name : String?, age : String?, gender : String?, location : String?, pnumber : String?){
        this.image = image
        this.name = name
        this.age = age
        this.gender = gender
        this.location = location
        this.pnumber = pnumber
    }


    fun getImage() : String? {
        return image
    }

    fun getName() : String? {
        return name
    }

    fun getAge() : String? {
        return age
    }

    fun getGender() : String? {
        return gender
    }

    fun getLocation() : String? {
        return location
    }

    fun getPhone() : String? {
        return pnumber
    }
}
