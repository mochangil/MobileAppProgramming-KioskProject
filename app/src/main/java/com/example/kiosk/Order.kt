package com.example.kiosk

class Order {
    var lists = mutableListOf<Product>()
    var price : Int = 0

    constructor()

    constructor(lists : MutableList<Product>, price : Int) {
        this.lists = lists
        this.price = price
    }
}