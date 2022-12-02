package com.example.kiosk

import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.recyclerview.widget.RecyclerView
import com.example.kiosk.R
import com.example.kiosk.databinding.ActivityMainpageBinding
import com.example.kiosk.databinding.DialogCheeseBinding
import com.example.kiosk.databinding.DialogPattyBinding
import com.example.kiosk.databinding.DialogSauceBinding
import com.example.kiosk.databinding.DialogVegetableBinding
import android.content.Intent
import com.example.kiosk.databinding.ActivityMainBinding


public class MainActivity : AppCompatActivity() {

    lateinit var mainPatty: Image
    lateinit var drawable : Drawable
    var pattyName : String = " "
    var sauceName : String = " "
    var cheeseName : String = " "
    var vegetableName : String = " "


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
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
