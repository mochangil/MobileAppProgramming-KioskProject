package com.example.kiosk

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.kiosk.databinding.PaymentBinding
import com.example.kiosk.databinding.PaymentDialogBinding

class PaymentActivity: AppCompatActivity() {
    lateinit var transaction: FragmentTransaction
    lateinit var fragmentManager : FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val beforePayment = BeforePaymentFragment()
        val afterPayment = AfterPaymentFragment()

        fragmentManager = supportFragmentManager

        val dialogBinding = PaymentDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogBinding.root)
        builder.setCancelable(false)
        val dialog = builder.create()

        transaction = fragmentManager.beginTransaction()
        transaction.replace(binding.fragmentContainer.id, beforePayment).commit()

        android.os.Handler(Looper.getMainLooper()).postDelayed({
            if(!this.isFinishing) {
                dialog.show()
            }
        }, 5000)

        android.os.Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
        }, 10000)

        dialog.setOnDismissListener() {
            transaction = fragmentManager.beginTransaction()
            transaction.replace(binding.fragmentContainer.id, afterPayment).commit()

            android.os.Handler(Looper.getMainLooper()).postDelayed({
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
            }, 5000)
        }


        /*
        binding.paymentCancel.setOnClickListener {
            finish()
            Toast.makeText(this, "결제 취소하셨습니다", Toast.LENGTH_SHORT).show()
        }
         */

    }
}