package com.example.kiosk

import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.recyclerview.widget.RecyclerView
import com.example.kiosk.R
import com.example.kiosk.databinding.ActivityMainpageBinding
import com.example.kiosk.databinding.DialogCheeseBinding
import com.example.kiosk.databinding.DialogPattyBinding
import com.example.kiosk.databinding.DialogSauceBinding
import com.example.kiosk.databinding.DialogVegetableBinding
import com.example.kiosk.databinding.LayoutMenuitemBinding


public class MainActivity : AppCompatActivity() {

    lateinit var mainPatty: Image
    lateinit var drawable : Drawable
    var pattyName : String = " "
    var sauceName : String = " "
    var cheeseName : String = " "
    var vegetableName : String = " "


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var num = 0
        //동적버튼 추가 (+스타일 지정방법 모름)
        binding.addBtn.setOnClickListener {
            var newStyle = ContextThemeWrapper(this, R.style.Button_Border)
            var btn = Button(newStyle)
            binding.addButtonView.addView(btn)
        }

        binding.btnMainPatty.setOnClickListener {
            val dialogBinding = DialogPattyBinding.inflate(layoutInflater)
            AlertDialog.Builder(this).run {
                setTitle("패티 결정")
                setView(dialogBinding.root)
                dialogBinding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
                    when (checkedId) {
                        R.id.pattyBbqIcon -> {
                            pattyName = "bbq"
                            drawable = resources.getDrawable(R.drawable.bbq)
                        }
                        R.id.pattyChickenIcon -> {
                            pattyName = "chicken"
                            drawable = resources.getDrawable(R.drawable.chicken)
                        }
                        R.id.pattyEggIcon -> {
                            pattyName = "eggsalad"
                            drawable = resources.getDrawable(R.drawable.eggsalad)
                        }
                        R.id.pattyShrimpIcon -> {
                            pattyName = "shrimp"
                            drawable = resources.getDrawable(R.drawable.shrimp)
                        }
                        R.id.pattySteakIcon -> {
                            pattyName = "steak"
                            drawable = resources.getDrawable(R.drawable.steak)
                        }
                    }
//                    binding.btnMainPatty.text = pattyName;
                    binding.btnMainPatty.background = drawable
                }
                //만약 닫기 클릭 후에 변경되기 하려면 setPositiveButton의 두번째 인자로 리스너 등록
                setPositiveButton("닫기", null)
                show()
            }
        }

        binding.btnMainSauce.setOnClickListener {
            val dialogBinding = DialogSauceBinding.inflate(layoutInflater)
            AlertDialog.Builder(this).run {
                setTitle("소스 결정")
                setView(dialogBinding.root)
                dialogBinding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
                    when (checkedId) {
                        R.id.sauceBbqIcon -> {
                            sauceName = "bbq"
                            drawable = resources.getDrawable(R.drawable.bbqsauce)
                        }
                        R.id.sauceGarlicIcon -> {
                            pattyName = "garlic"
                            drawable = resources.getDrawable(R.drawable.garlicsauce)
                        }
                        R.id.sauceHotChiliIcon -> {
                            pattyName = "hoyChili"
                            drawable = resources.getDrawable(R.drawable.hotchilisauce)
                        }
                        R.id.sauceSweetChiliIcon -> {
                            pattyName = "sweetChili"
                            drawable = resources.getDrawable(R.drawable.sweetchilisauce)
                        }
                        R.id.sauceTartarIcon -> {
                            pattyName = "tartar"
                            drawable = resources.getDrawable(R.drawable.tartarsauce)
                        }
                    }
//                    binding.btnMainSauce.text = sauceName;
                    binding.btnMainSauce.background = drawable
                }
                //만약 닫기 클릭 후에 변경되기 하려면 setPositiveButton의 두번째 인자로 리스너 등록
                setPositiveButton("닫기", null)
                show()
            }
        }

        binding.btnMainCheese.setOnClickListener {
            val dialogBinding = DialogCheeseBinding.inflate(layoutInflater)
            AlertDialog.Builder(this).run {
                setTitle("패티 결정")
                setView(dialogBinding.root)
                dialogBinding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
                    when (checkedId) {
                        R.id.mozzaCheeseIcon -> {
                            cheeseName = "mozza"
                            drawable = resources.getDrawable(R.drawable.mozzacheese)
                        }
                        R.id.cheddarCheeseIcon -> {
                            cheeseName = "cheddar"
                            drawable = resources.getDrawable(R.drawable.cheddarcheese)
                        }
                    }
//                    binding.btnMainCheese.text = cheeseName;
                    binding.btnMainCheese.background = drawable
                }
                //만약 닫기 클릭 후에 변경되기 하려면 setPositiveButton의 두번째 인자로 리스너 등록
                setPositiveButton("닫기", null)
                show()
            }
        }

        binding.btnMainVegetable.setOnClickListener {
            val dialogBinding = DialogVegetableBinding.inflate(layoutInflater)
            AlertDialog.Builder(this).run {
                setTitle("패티 결정")
                setView(dialogBinding.root)
                dialogBinding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
                    when (checkedId) {
                        R.id.lettuceIcon -> {
                            vegetableName = "lettuce"
                            drawable = resources.getDrawable(R.drawable.lettuce)
                        }
                        R.id.onionIcon -> {
                            vegetableName = "chicken"
                            drawable = resources.getDrawable(R.drawable.onion)
                        }
                        R.id.pickleIcon -> {
                            vegetableName = "eggsalad"
                            drawable = resources.getDrawable(R.drawable.pickle)
                        }
                        R.id.tomatoIcon -> {
                            vegetableName = "shrimp"
                            drawable = resources.getDrawable(R.drawable.tomato)
                        }
                    }
//                    binding.btnMainVegetable.text = vegetableName
                    binding.btnMainVegetable.background = drawable
                }
                //만약 닫기 클릭 후에 변경되기 하려면 setPositiveButton의 두번째 인자로 리스너 등록
                setPositiveButton("닫기", null)
                show()
            }
        }


    }
}
//class AddMenuViewHolder(val binding: LayoutMenuitemBinding): RecyclerView.ViewHolder(binding.root)
//
//class MyAdapter(val dataSet: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        /*
//         * inflate(int resource, ViewGroup parent, boolean attachToRoot)
//         *  - attachToRoot= false: parent is only used to create the correct subclass of LayoutParams
//         */
//        Log.d("RecyclerView", "onCreateViewHolder()")
//        return MyViewHolder(LayoutItemBinding.inflate(LayoutInflater.from(parent.context),
//            parent, false))
//    }
//    // onCreateViewHolder()에서 반환한 뷰 홀더 객체는 자동으로 onBindViewHolder()의 매개변수로 전달
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        Log.d("RecyclerView", "onBindViewHolder(): $position")
//        val binding = (holder as MyViewHolder).binding
//        // 뷰에 데이터 출력
//        binding.itemData.text = dataSet[position]
//        // 뷰에 이벤트 추가
//        binding.itemRoot.setOnClickListener {
//            Log.d("RecyclerView", "item root click: $position")
//        }
//    }
//
//    override fun getItemCount(): Int {
//        Log.d("RecyclerView", "init data size: ${dataSet.size}")
//        return dataSet.size
//    }
//}
//
//
//
