package com.example.kiosk

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PrePaymentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PrePaymentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_pre_payment, container, false)
        v.findViewById<Button>(R.id.pre_payment_cancel).setOnClickListener {
            activity?.finish()
        }

        val confirmButton = v.findViewById<Button>(R.id.pre_payment_confirm)
        val rg1 = v.findViewById<RadioGroup>(R.id.meal_location_radio)
        val rg2 = v.findViewById<RadioGroup>(R.id.payment_method_radio)

        val totalBill = arguments?.getInt("totalBill") ?: 0
        val cashPayment = CashPaymentFragment()
        val cardPayment = CardPaymentFragment()

        rg1.setOnCheckedChangeListener { _, checkedId ->
            if(rg2.checkedRadioButtonId != -1) {
                confirmButton.isEnabled = true
            }
            if(checkedId == R.id.eat_in) {
                v.findViewById<RadioButton>(R.id.eat_in).setCompoundDrawablesWithIntrinsicBounds(
                    0, R.drawable.in_eat_check_resize, 0, 0)
                v.findViewById<RadioButton>(R.id.eat_out).setCompoundDrawablesWithIntrinsicBounds(
                    0, R.drawable.out_eat_resize, 0, 0)
            } else {
                v.findViewById<RadioButton>(R.id.eat_in).setCompoundDrawablesWithIntrinsicBounds(
                    0, R.drawable.in_eat_resize, 0, 0)
                v.findViewById<RadioButton>(R.id.eat_out).setCompoundDrawablesWithIntrinsicBounds(
                    0, R.drawable.out_eat_check_resize, 0, 0)
            }
        }

        rg2.setOnCheckedChangeListener { _, checkedId ->
            if(rg1.checkedRadioButtonId != -1) {
                confirmButton.isEnabled = true
            }
            if(checkedId == R.id.cash) {
                v.findViewById<RadioButton>(R.id.cash).setCompoundDrawablesWithIntrinsicBounds(
                    0, R.drawable.cash_check_resize, 0, 0)
                v.findViewById<RadioButton>(R.id.card).setCompoundDrawablesWithIntrinsicBounds(
                    0, R.drawable.card_resize, 0, 0)
            } else {
                v.findViewById<RadioButton>(R.id.cash).setCompoundDrawablesWithIntrinsicBounds(
                    0, R.drawable.cash_resize, 0, 0)
                v.findViewById<RadioButton>(R.id.card).setCompoundDrawablesWithIntrinsicBounds(
                    0, R.drawable.card_check_resize, 0, 0)
            }
        }

        confirmButton.setOnClickListener {
            if(rg2.checkedRadioButtonId == R.id.cash) {
                val bundle = Bundle()
                bundle.putInt("totalBill", totalBill)
                cashPayment.arguments = bundle
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, cashPayment)?.commit()
            }
            else {
                val bundle = Bundle()
                bundle.putInt("totalBill", totalBill)
                cardPayment.arguments = bundle
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, cardPayment)?.commit()
            }
        }

        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PrePaymentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PrePaymentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}