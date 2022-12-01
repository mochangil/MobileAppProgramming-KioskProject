package com.example.kiosk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kiosk.databinding.InventoryItemBinding
import com.example.kiosk.databinding.InventoryTableBinding

class InventoryTable : AppCompatActivity() {
    fun getTable() {
        // TODO("데이터베이스 생성 시 구현")
    }

    class MyViewHolder(val binding: InventoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    class MyAdapter(val dataSet : MutableList<MutableList<String>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        // TODO("더미 데이터 형식에 맞게 수정")

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return MyViewHolder(InventoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val binding = (holder as MyViewHolder).binding
            binding.itemName.text = dataSet[position][0]
            binding.itemNumber.text = dataSet[position][1]

            binding.itemRoot.setOnClickListener {
                // TODO("액티비티로 수정 페이지 제작, 그 화면에서 재고 개수, 이미지 설정")
            }
        }
        override fun getItemCount(): Int {
            return dataSet.size
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ivMenuBinding = InventoryTableBinding.inflate(layoutInflater)
        setContentView(ivMenuBinding.root)

        val datas = mutableListOf<String>()
        val dataSet = mutableListOf<MutableList<String>>()
        datas.add("shrimp")
        datas.add("100")
        dataSet.add(ArrayList(datas))
        datas.clear()

        datas.add("cow")
        datas.add("100")
        dataSet.add(ArrayList(datas))
        datas.clear()

        datas.add("tomato")
        datas.add("100")
        dataSet.add(ArrayList(datas))
        datas.clear()

        datas.add("lettuce")
        datas.add("100")
        dataSet.add(ArrayList(datas))
        datas.clear()

        ivMenuBinding.invRecyclerView.layoutManager = LinearLayoutManager(this)
        ivMenuBinding.invRecyclerView.adapter = MyAdapter(dataSet)
        ivMenuBinding.invRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        datas.add("pickle")
        datas.add("100")
        dataSet.add(ArrayList(datas))
        datas.clear()

        // 동적으로 추가할 예정이기에 이 코드 유지
        (ivMenuBinding.invRecyclerView.adapter as MyAdapter).notifyDataSetChanged()
    }
}