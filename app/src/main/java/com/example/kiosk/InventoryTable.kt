package com.example.kiosk

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kiosk.databinding.InventoryAddDialogBinding
import com.example.kiosk.databinding.InventoryItemBinding
import com.example.kiosk.databinding.InventoryTableBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class InventoryTable : AppCompatActivity() {

    lateinit var database: DatabaseReference
    lateinit var ivMenuBinding : InventoryTableBinding
    val prod_dataSet = mutableListOf<MutableList<String>>()

    val postListener = object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val datas = mutableListOf<String>()

            val data_patty = snapshot.child("Product").child("patty")
            for (item in data_patty.children) {
                val product = item.getValue(Product::class.java)
                datas.add(product!!.name)
                datas.add(product!!.num.toString())
                datas.add("patty")
                datas.add(item.key.toString())
                prod_dataSet.add(ArrayList(datas))
                datas.clear()
            }

            val data_veg = snapshot.child("Product").child("vegetable")
            for (item in data_veg.children) {
                val product = item.getValue(Product::class.java)
                datas.add(product!!.name)
                datas.add(product!!.num.toString())
                datas.add("vegetable")
                datas.add(item.key.toString())
                prod_dataSet.add(ArrayList(datas))
                datas.clear()
            }

            val data_cheese = snapshot.child("Product").child("cheese")
            for (item in data_cheese.children) {
                val product = item.getValue(Product::class.java)
                datas.add(product!!.name)
                datas.add(product!!.num.toString())
                datas.add("cheese")
                datas.add(item.key.toString())
                prod_dataSet.add(ArrayList(datas))
                datas.clear()
            }
            (ivMenuBinding.invRecyclerView.adapter as MyAdapter).notifyDataSetChanged()
        }
        override fun onCancelled(error: DatabaseError) {

        }
    }
    class MyViewHolder(val binding: InventoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    class MyAdapter(val dataSet : MutableList<MutableList<String>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return MyViewHolder(InventoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val binding = (holder as MyViewHolder).binding
            binding.itemName.text = dataSet[position][0]
            binding.itemNumber.text = dataSet[position][1]

            var context : Context = binding.itemRoot.context

            binding.itemRoot.setOnClickListener {
                val ivdIntent = Intent(context, InventoryDialog::class.java)
                ivdIntent.putExtra("name", binding.itemName.text)
                ivdIntent.putExtra("table", dataSet[position][2])
                ivdIntent.putExtra("key", dataSet[position][3])
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
        ivMenuBinding.invRecyclerView.adapter = MyAdapter(prod_dataSet)
        ivMenuBinding.invRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        // 동적으로 추가할 예정이기에 이 코드 유지
        (ivMenuBinding.invRecyclerView.adapter as MyAdapter).notifyDataSetChanged()

        var addBinding = InventoryAddDialogBinding.inflate(layoutInflater)

        var is_patty : Boolean = false
        var is_veg : Boolean = false
        var is_cheese : Boolean = false

        val eventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (is_patty || is_cheese || is_veg) {
                    var name : String = addBinding.menu.text.toString()
                    var number : Int = addBinding.num.text.toString().toInt()
                    var product = Product(name, number)

                    if (is_patty) {
                        database.child("Product").child("patty").child(name).setValue(product)
                    } else if (is_veg) {
                        database.child("Product").child("vegetable").child(name).setValue(product)
                    } else if (is_cheese) {
                        database.child("Product").child("cheese").child(name).setValue(product)
                    }
                }

                is_patty = false
                is_veg = false
                is_cheese = false

                // 액티비티 새로 고침
                finish()
                overridePendingTransition(0, 0)
                val intent = intent
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
        }

        ivMenuBinding.add.setOnClickListener {
            addBinding = InventoryAddDialogBinding.inflate(layoutInflater)

            addBinding.addGroup.setOnCheckedChangeListener { group, checkID ->
                if (checkID == R.id.pattyB) {
                    is_patty = true
                }
                else if (checkID == R.id.vegB) {
                    is_veg = true
                }
                else if (checkID == R.id.cheeseB) {
                    is_cheese = true
                }
            }

            AlertDialog.Builder(this).run {
                setTitle("품목 추가하기")
                setView(addBinding.root)
                setPositiveButton("확인", eventHandler)
                setCancelable(true)
                show()
            }.setCanceledOnTouchOutside(true)
        }
    }
}