package com.example.kiosk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kiosk.databinding.InventoryTableBinding
import com.example.kiosk.databinding.OrderItemBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class OrderMonthTable : AppCompatActivity() {
    lateinit var database: DatabaseReference
    lateinit var ivMenuBinding : InventoryTableBinding
    var ym_list = mutableListOf<String>()
    var org_ym_list = mutableListOf<String>()

    val postListener = object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val data_year_month = snapshot.child("Order")
            for (item in data_year_month.children) {
                var year_month : String = item.key.toString()
                var year : Int = year_month.toInt() / 100
                var month : Int = year_month.toInt() % 100
                var show_year_month : String = year.toString() + "년 " + month.toString() + "월"
                ym_list.add(show_year_month)
                org_ym_list.add(year_month)
            }

            (ivMenuBinding.invRecyclerView.adapter as MyAdapter).notifyDataSetChanged()
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
                val ivdIntent = Intent(context, OrderDayTable::class.java)
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

        ivMenuBinding = InventoryTableBinding.inflate(layoutInflater)
        setContentView(ivMenuBinding.root)

        ivMenuBinding.invRecyclerView.layoutManager = LinearLayoutManager(this)
        ivMenuBinding.invRecyclerView.adapter = MyAdapter(ym_list, org_ym_list)
        ivMenuBinding.invRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        // 동적으로 추가할 예정이기에 이 코드 유지
        (ivMenuBinding.invRecyclerView.adapter as MyAdapter).notifyDataSetChanged()
    }
}