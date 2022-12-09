package com.example.kiosk

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.kiosk.databinding.InventoryDialogBinding
import com.example.kiosk.databinding.OrderLogActivityBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.DecimalFormat
import java.util.Objects

class OrderLogActivity : AppCompatActivity() {
    lateinit var database: DatabaseReference
    lateinit var OLBinding : OrderLogActivityBinding
    lateinit var otb : TableLayout
    lateinit var usedFont : Typeface

    lateinit var year_month_key : String
    lateinit var day_key : String
    lateinit var num_key : String

    val postListener = object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val data_items = snapshot.child("Order").child(year_month_key).child(day_key).child(num_key)
            var order = data_items.getValue(Order::class.java)
            val dec = DecimalFormat("#,###")

            for (product in order!!.lists) {
                // Table row 생성
                var newRow = TableRow(this@OrderLogActivity)
                var tableLayoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
                var nameText = TextView(this@OrderLogActivity)
                nameText.text = product.name
                nameText.typeface = usedFont
                nameText.setTextColor(Color.parseColor("#000000"))
                nameText.textSize = 20.toFloat()
                nameText.gravity = Gravity.CENTER

                var numText = TextView(this@OrderLogActivity)
                numText.text = product.num.toString()
                numText.typeface = usedFont
                numText.setTextColor(Color.parseColor("#000000"))
                numText.textSize = 20.toFloat()
                numText.gravity = Gravity.CENTER

                var priceText = TextView(this@OrderLogActivity)
                priceText.text = dec.format(product.price.toString().toInt()) + "원"
                priceText.typeface = usedFont
                priceText.setTextColor(Color.parseColor("#000000"))
                priceText.textSize = 20.toFloat()
                priceText.gravity = Gravity.CENTER

                newRow.addView(nameText, tableLayoutParams)
                newRow.addView(numText, tableLayoutParams)
                newRow.addView(priceText, tableLayoutParams)
                newRow.setPadding(0, 25, 0, 0)

                otb.addView(newRow)
            }

            var item_price = dec.format(order.price)
            OLBinding.price.text = item_price + "원"
        }
        override fun onCancelled(error: DatabaseError) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database.reference
        database.addValueEventListener(postListener)

        OLBinding = OrderLogActivityBinding.inflate(layoutInflater)
        setContentView(OLBinding.root)

        otb = OLBinding.mainTable
        usedFont = ResourcesCompat.getFont(this, R.font.cafe24ssurround)!!

        var key_str = intent.getStringExtra("key").toString()
        num_key = intent.getStringExtra("num").toString()
        if (key_str.length == 8) {
            year_month_key = (key_str.toInt() / 100).toString()
            day_key = (key_str.toInt() % 100).toString()
        } else if (key_str.length == 7) {
            year_month_key = (key_str.toInt() / 10).toString()
            day_key = (key_str.toInt() % 10).toString()
        }

        var mainText = "주문 번호 " + num_key
        OLBinding.itemName.text = mainText

        OLBinding.back.setOnClickListener {
            finish()
        }
    }
}