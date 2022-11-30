package com.example.kiosk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin)

        val btnNewActivity = findViewById<Button>(R.id.admin_button2)
        btnNewActivity.setOnClickListener {
            val intent = Intent(this, MenuManagementActivity::class.java)
            startActivity(intent)
        }
    }
}