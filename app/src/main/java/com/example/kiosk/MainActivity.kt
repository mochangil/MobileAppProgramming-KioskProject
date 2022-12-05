package com.example.kiosk

import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.kiosk.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


public class MainActivity : AppCompatActivity() {

    lateinit var mainPatty: Image
    lateinit var binding: ActivityMainBinding
    lateinit var drawable : Drawable
    lateinit var database: DatabaseReference
    var pattyName : String = " "
    var sauceName : String = " "
    var cheeseName : String = " "
    var vegetableName : String = " "


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.main.setOnClickListener {
            val orderPassIntent = Intent(this, OrderPage::class.java)
            startActivity(orderPassIntent)
        }
        binding.admin.setOnClickListener {
            val adminPassIntent = Intent(this, AdminPassword::class.java)
            startActivity(adminPassIntent)
        }
   }
}
