package com.example.kiosk

class Product {
    var name : String = ""
    var num : Int = 0

    constructor()

    constructor(name : String, num : Int) {
        this.name = name
        this.num = num
    }

    override fun toString() : String {
        val str = "Name : " + this.name + " num : " + this.num.toString()
        return str
    }
}