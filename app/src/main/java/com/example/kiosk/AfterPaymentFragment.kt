package com.example.kiosk

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AfterPaymentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AfterPaymentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var database: DatabaseReference
    lateinit var v: View

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
    ): View {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_after_payment, container, false)
        database = Firebase.database.reference
        database.addListenerForSingleValueEvent(postListener)

        android.os.Handler(Looper.getMainLooper()).postDelayed({
            val mainIntent = Intent(activity, MainActivity::class.java)
            startActivity(mainIntent)
        }, 5000)

        return v
    }

    private val postListener = object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            var orderNumber = snapshot.child("order_number").value.toString().toInt()
            orderNumber = orderNumber % 100 + 1
            database.child("order_number").setValue(orderNumber)
            v.findViewById<TextView>(R.id.order_number).append(orderNumber.toString())
        }
        override fun onCancelled(error: DatabaseError) {
            // Failed to read value
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AfterPaymentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AfterPaymentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}