package com.example.kiosk

import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kiosk.databinding.PaymentBinding
import com.example.kiosk.databinding.PaymentDialogBinding

class PaymentActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dialogBinding = PaymentDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogBinding.root)
        builder.setCancelable(false)
        val dialog = builder.create()

        android.os.Handler(Looper.getMainLooper()).postDelayed({
            if(!this.isFinishing) {
                dialog.show()
            }
        }, 5000)

        android.os.Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
        }, 10000)

        binding.paymentCancel.setOnClickListener {
            finish()
            Toast.makeText(this, "결제 취소하셨습니다", Toast.LENGTH_SHORT).show()
        }
    }
}