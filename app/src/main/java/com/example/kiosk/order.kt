package com.example.kiosk

class order {
    var lists = listOf<product>()
    var price : Int = 0

    constructor()

    constructor(lists : List<product>, price : Int) {
        this.lists = lists
        this.price = price
    }
}