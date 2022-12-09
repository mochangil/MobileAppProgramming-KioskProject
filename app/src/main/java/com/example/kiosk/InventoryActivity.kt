package com.example.kiosk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kiosk.databinding.InventoryDialogBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.DecimalFormat

class InventoryActivity : AppCompatActivity() {
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database.reference

        val ivdBinding = InventoryDialogBinding.inflate(layoutInflater)
        setContentView(ivdBinding.root)

        var org_name = intent.getStringExtra("name")
        ivdBinding.itemName.text = org_name
        var table : String = intent.getStringExtra("table").toString()
        val key : String = intent.getStringExtra("key").toString()
        var num : Int = intent.getStringExtra("num").toString().toInt()
        var price : Int = intent.getStringExtra("price").toString().toInt()

        val dec = DecimalFormat("#,###")
        ivdBinding.invNum.hint = "남은 수량 : " + num.toString() + "개"
        ivdBinding.invPrice.hint = "가격 : " + dec.format(price) + " 원"

        ivdBinding.back.setOnClickListener {
            finish()
        }
        ivdBinding.ok.setOnClickListener {
            var name : String = ivdBinding.itemName.text.toString()
            var ch_number : Int
            try {
                ch_number = ivdBinding.invNum.text.toString().toInt()
            } catch (e : Exception) {
                ch_number = num
            }
            var ch_price : Int
            try {
                ch_price = ivdBinding.invPrice.text.toString().toInt()
            } catch (e : Exception) {
                ch_price = price
            }
            var product : Product = Product(name, ch_number, ch_price)

            database.child("Product").child(table).child(key).setValue(product)
            finish()
        }
        ivdBinding.delete.setOnClickListener {
            database.child("Product").child(table).child(key).setValue(null)
            finish()
        }
    }
}