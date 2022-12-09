package com.example.kiosk

import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CardPaymentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardPaymentFragment : Fragment() {
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

        val v = inflater.inflate(R.layout.fragment_card_payment, container, false)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(R.layout.payment_dialog)
        builder.setCancelable(false)
        val dialog = builder.create()

        android.os.Handler(Looper.getMainLooper()).postDelayed({
            if(activity is PaymentActivity) {
                dialog.show()
            }
        }, 5000)

        android.os.Handler(Looper.getMainLooper()).postDelayed({
            if(activity is PaymentActivity) {
                dialog.dismiss()
            }
        }, 10000)

        dialog.setOnDismissListener() {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, AfterPaymentFragment())?.commit()
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
         * @return A new instance of fragment before_payment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CardPaymentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}