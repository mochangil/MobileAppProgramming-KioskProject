package com.example.kiosk

class Order {
    var lists = listOf<Product>()
    var price : Int = 0

    constructor()

    constructor(lists : List<Product>, price : Int) {
        this.lists = lists
        this.price = price
    }
}