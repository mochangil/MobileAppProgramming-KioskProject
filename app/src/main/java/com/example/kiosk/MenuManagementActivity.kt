package com.example.kiosk

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kiosk.databinding.MenuManagementBinding
import com.example.kiosk.databinding.PattyBinding
import com.example.kiosk.databinding.VegetableBinding

class MenuManagementActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = MenuManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pattyButton.setOnClickListener {
            val dialogBinding = PattyBinding.inflate(layoutInflater)
            AlertDialog.Builder(this).run {
                setTitle("패티")
                setView(dialogBinding.root)
                setPositiveButton("닫기", null)
                show()
            }
        }

        binding.vegetableButton.setOnClickListener {
            val dialogBinding = VegetableBinding.inflate(layoutInflater)
            AlertDialog.Builder(this).run {
                setTitle("채소")
                setView(dialogBinding.root)
                setPositiveButton("닫기", null)
                show()
            }
        }

        val btnReturn = findViewById<Button>(R.id.menu_management_back_button)
        btnReturn.setOnClickListener {
            finish()
        }
    }
}