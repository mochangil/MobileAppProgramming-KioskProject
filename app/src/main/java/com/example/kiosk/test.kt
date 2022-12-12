package com.example.kiosk

class test {
    var lists = listOf<Product>()
    var price : Int = 0

    fun main() {
        var a1 = arrayListOf<Product>()
        a1.add(Product("aa", 1))
        lists = a1
    }
}