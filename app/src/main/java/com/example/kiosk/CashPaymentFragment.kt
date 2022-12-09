package com.example.kiosk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CashPaymentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CashPaymentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

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
        val v = inflater.inflate(R.layout.fragment_cash_payment, container, false)
        var i = 0
        var money = 7500 // 결제금액 임의로 설정, 19500을 실제 결제 금액 변수로 대체하면 될듯합니다..
        var restMoney = money
        var insertMoney = 0
        val restPrice = v.findViewById<TextView>(R.id.payment_rest_number)
        val insertPrice = v.findViewById<TextView>(R.id.payment_insert_number)

        insertPrice.text = insertMoney.toString()
        restPrice.text = restMoney.toString()

        coroutineScope.launch {
            i = money / 10000
            money %= 10000
            for (j in 1..i) {
                delay(1000)
                insertMoney += 10000
                restMoney -= 10000
                insertPrice.text = insertMoney.toString()
                restPrice.text = restMoney.toString()
            }

            i = money / 5000
            money %= 5000
            for (j in 1..i) {
                delay(1000)
                insertMoney += 5000
                restMoney -= 5000
                insertPrice.text = insertMoney.toString()
                restPrice.text = restMoney.toString()
            }

            i = money / 1000
            money %= 1000
            for (j in 1..i) {
                delay(1000)
                insertMoney += 1000
                restMoney -= 1000
                insertPrice.text = insertMoney.toString()
                restPrice.text = restMoney.toString()
            }

            i = money / 500
            money %= 500
            for (j in 1..i) {
                delay(1000)
                insertMoney += 500
                restMoney -= 500
                insertPrice.text = insertMoney.toString()
                restPrice.text = restMoney.toString()
            }

            delay(3000)
            if(activity is PaymentActivity) {
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, AfterPaymentFragment())?.commit()
            }
        }

        v.findViewById<Button>(R.id.payment_cancel).setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, PrePaymentFragment())?.commit()
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
         * @return A new instance of fragment CashPaymentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CashPaymentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}