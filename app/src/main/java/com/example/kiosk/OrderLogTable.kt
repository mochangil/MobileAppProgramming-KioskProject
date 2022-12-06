package com.example.kiosk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kiosk.databinding.InventoryTableBinding
import com.example.kiosk.databinding.OrderItemBinding
import com.example.kiosk.databinding.OrderLogTableBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class OrderLogTable : AppCompatActivity() {
    lateinit var database: DatabaseReference
    lateinit var OLTBinding : OrderLogTableBinding
    lateinit var key_str : String
    lateinit var year_month_key : String
    lateinit var day_key : String

    var ym_list = mutableListOf<String>()
    var org_ym_list = mutableListOf<String>()
    var num_list = mutableListOf<String>()

    val postListener = object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val data_items = snapshot.child("Order").child(year_month_key).child(day_key)
            Log.d("Fire", key_str)
            for (item in data_items.children) {
                var num : String = item.key.toString()
                var order_nums : String = "주문 번호 " + num
                ym_list.add(order_nums)
                var year_month_day_num = key_str
                org_ym_list.add(year_month_day_num)
                num_list.add(num)
            }

            (OLTBinding.orderRecyclerView.adapter as MyAdapter).notifyDataSetChanged()
        }
        override fun onCancelled(error: DatabaseError) {

        }
    }
    class MyViewHolder(val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root)

    class MyAdapter(val dataSet : MutableList<String>, val orgDataSet : MutableList<String>, val numDataSet : MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return MyViewHolder(OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val binding = (holder as MyViewHolder).binding
            binding.itemName.text = dataSet[position]

            var context : Context = binding.itemRoot.context

            binding.itemRoot.setOnClickListener {
                val ivdIntent = Intent(context, OrderLogActivity::class.java)
                ivdIntent.putExtra("key", orgDataSet[position])
                ivdIntent.putExtra("num", numDataSet[position])
                context.startActivity(ivdIntent)
                dataSet.clear()
            }
        }
        override fun getItemCount(): Int {
            return dataSet.size
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        database = Firebase.database.reference
        database.addValueEventListener(postListener)

        super.onCreate(savedInstanceState)

        OLTBinding = OrderLogTableBinding.inflate(layoutInflater)
        setContentView(OLTBinding.root)
        key_str = intent.getStringExtra("key").toString()
        if (key_str.length == 8) {
            year_month_key = (key_str.toInt() / 100).toString()
            day_key = (key_str.toInt() % 100).toString()
        } else if (key_str.length == 7) {
            year_month_key = (key_str.toInt() / 10).toString()
            day_key = (key_str.toInt() % 10).toString()
        }

        OLTBinding.orderRecyclerView.layoutManager = LinearLayoutManager(this)
        OLTBinding.orderRecyclerView.adapter = MyAdapter(ym_list, org_ym_list, num_list)
        OLTBinding.orderRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        // 동적으로 추가할 예정이기에 이 코드 유지
        (OLTBinding.orderRecyclerView.adapter as MyAdapter).notifyDataSetChanged()
    }
}