package com.example.kiosk

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

        val prePayment = PrePaymentFragment()

        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()
        transaction.replace(binding.fragmentContainer.id, prePayment).commit()
    }
}