package com.example.kiosk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
            Toast.makeText(activity, "결제 취소하셨습니다", Toast.LENGTH_SHORT).show()
            activity?.finish()
        }

        val confirmButton = v.findViewById<Button>(R.id.pre_payment_confirm)
        val rg1 = v.findViewById<RadioGroup>(R.id.meal_location_radio)
        val rg2 = v.findViewById<RadioGroup>(R.id.payment_method_radio)

        rg1.setOnCheckedChangeListener { _, _ ->
            if(rg2.checkedRadioButtonId != -1) {
                confirmButton.isEnabled = true
            }
        }
        rg2.setOnCheckedChangeListener { _, _ ->
            if(rg1.checkedRadioButtonId != -1) {
                confirmButton.isEnabled = true
            }
        }

        confirmButton.setOnClickListener {
            if(rg2.checkedRadioButtonId == R.id.cash)
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, CashPaymentFragment())?.commit()
            else
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, CardPaymentFragment())?.commit()
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