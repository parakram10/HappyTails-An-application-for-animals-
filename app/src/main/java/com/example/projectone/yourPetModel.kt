package com.example.projectone

class yourPetModel {

    private var image : String? = null
    private var name : String? = null
    private var age : String? = null
    private var gender : String? = null

    constructor()
    constructor(image : String?, name : String?, age : String?, gender : String?){
        this.image = image
        this.age = age
        this.gender = gender
        this.name = name
    }

    fun getName() : String? {
        return name
    }

    fun getAge() : String? {
        return age
    }

    fun getImage() : String? {
        return image
    }

    fun getGender() : String? {
        return gender
    }
}