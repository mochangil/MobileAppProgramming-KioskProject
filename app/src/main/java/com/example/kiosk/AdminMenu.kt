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
        adminBinding.menu.setOnClickListener {
            val menuIntent = Intent(this, MenuManagementActivity::class.java)
            startActivity(menuIntent)
        }
        adminBinding.sold.setOnClickListener {

        }
        adminBinding.adminBackButton.setOnClickListener {
            finish()
        }

        adminBinding.paymentButton.setOnClickListener {
            val pay = Intent(this, PaymentActivity::class.java)
            startActivity(pay)
        }
    }
}