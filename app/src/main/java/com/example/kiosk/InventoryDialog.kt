package com.example.kiosk

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kiosk.databinding.InventoryDialogBinding

class InventoryDialog : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ivdBinding = InventoryDialogBinding.inflate(layoutInflater)
        setContentView(ivdBinding.root)

        ivdBinding.itemName.text = intent.getStringExtra("name")

        ivdBinding.back.setOnClickListener {
            finish()
        }
        ivdBinding.ok.setOnClickListener {
            // TODO("데이터 베이스에 재고 수정하는 기능 추가")
            var name : String = ivdBinding.itemName.text.toString()
            var number : Int = ivdBinding.invNum.text.toString().toInt()
            var product : Product = Product(name, number)
        }
    }
}