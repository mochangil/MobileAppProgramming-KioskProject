package com.example.kiosk

import java.text.DecimalFormat

class Product {
    var name : String = ""
    var num : Int = 0
    var price : Int = 0

    constructor()

    constructor(name : String, num : Int) {
        this.name = name
        this.num = num
    }

    constructor(name: String, num: Int, price : Int) {
        this.name = name
        this.num = num
        this.price = price
    }

    override fun toString() : String {
        val str = "Name : " + this.name + " num : " + this.num.toString() + " price : " + this.price.toString()
        return str
    }
}