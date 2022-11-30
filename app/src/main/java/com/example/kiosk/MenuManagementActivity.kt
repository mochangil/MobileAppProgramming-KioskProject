package com.example.kiosk

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuManagementActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_management)

        val btnReturn = findViewById<Button>(R.id.menu_management_back_button)
        btnReturn.setOnClickListener {
            finish()
        }
    }
}