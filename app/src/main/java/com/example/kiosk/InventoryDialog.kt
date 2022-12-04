package com.example.kiosk

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kiosk.databinding.InventoryDialogBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class InventoryDialog : AppCompatActivity() {
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database.reference

        val ivdBinding = InventoryDialogBinding.inflate(layoutInflater)
        setContentView(ivdBinding.root)

        ivdBinding.itemName.text = intent.getStringExtra("name")
        var table : String = intent.getStringExtra("table").toString()
        val key : String = intent.getStringExtra("key").toString()

        ivdBinding.back.setOnClickListener {
            finish()
        }
        ivdBinding.ok.setOnClickListener {
            // TODO("데이터 베이스에 재고 수정하는 기능 추가")
            var name : String = ivdBinding.itemName.text.toString()
            var number : Int = ivdBinding.invNum.text.toString().toInt()
            var product : Product = Product(name, number)

            database.child("Product").child(table).child(key).setValue(product)
            finish()
        }
    }
}