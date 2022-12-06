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

class OrderDayTable : AppCompatActivity() {
    lateinit var database: DatabaseReference
    lateinit var OLTBinding : OrderLogTableBinding
    lateinit var key_str : String
    var ym_list = mutableListOf<String>()
    var org_ym_list = mutableListOf<String>()

    val postListener = object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val data_day = snapshot.child("Order").child(key_str)

            for (item in data_day.children) {
                var day : String = item.key.toString()
                var show_year_month: String = day + "일"
                ym_list.add(show_year_month)
                var year_month_day = key_str + day
                org_ym_list.add(year_month_day)
            }

            (OLTBinding.orderRecyclerView.adapter as MyAdapter).notifyDataSetChanged()
        }
        override fun onCancelled(error: DatabaseError) {

        }
    }
    class MyViewHolder(val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root)

    class MyAdapter(val dataSet : MutableList<String>, val orgDataSet : MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return MyViewHolder(OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val binding = (holder as MyViewHolder).binding
            binding.itemName.text = dataSet[position]

            var context : Context = binding.itemRoot.context

            binding.itemRoot.setOnClickListener {
                val ivdIntent = Intent(context, OrderLogTable::class.java)
                ivdIntent.putExtra("key", orgDataSet[position])
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

        OLTBinding.orderRecyclerView.layoutManager = LinearLayoutManager(this)
        OLTBinding.orderRecyclerView.adapter = MyAdapter(ym_list, org_ym_list)
        OLTBinding.orderRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        // 동적으로 추가할 예정이기에 이 코드 유지
        (OLTBinding.orderRecyclerView.adapter as MyAdapter).notifyDataSetChanged()
    }
}