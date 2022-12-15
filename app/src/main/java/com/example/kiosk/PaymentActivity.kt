package com.example.kiosk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.kiosk.databinding.PaymentBinding

class PaymentActivity: AppCompatActivity() {
    private lateinit var transaction: FragmentTransaction
    private lateinit var fragmentManager : FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val totalBill = intent.getIntExtra("totalBill", 0)
        binding.paymentNumber.text = totalBill.toString()

        val prePayment = PrePaymentFragment()
        val bundle = Bundle()
        bundle.putInt("totalBill", totalBill)
        prePayment.arguments = bundle

        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()
        transaction.replace(binding.fragmentContainer.id, prePayment).commit()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}