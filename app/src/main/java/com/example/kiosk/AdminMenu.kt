package com.example.kiosk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kiosk.databinding.AdminMenuBinding

class AdminMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adminBinding = AdminMenuBinding.inflate(layoutInflater)
        setContentView(adminBinding.root)

        adminBinding.inventory.setOnClickListener {
            val ivMenuIntent = Intent(this, InventoryTable::class.java)
            startActivity(ivMenuIntent)
        }

        adminBinding.sold.setOnClickListener {
            val orders = Intent(this, OrderMonthTable::class.java)
            startActivity(orders)
        }
        adminBinding.adminBackButton.setOnClickListener {
            val mainIntent = Intent(this, OrderPage::class.java)
            startActivity(mainIntent)
            finish()
        }
    }
}