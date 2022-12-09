package com.example.kiosk

import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import kotlin.math.roundToInt


public class OrderPage : AppCompatActivity() {



    lateinit var requestLaunch: ActivityResultLauncher<Intent>
    lateinit var newbutton : Button

    var addmenucount: Int = 0
    var sidecount: Int = 0
    var drinkcount: Int = 0
    var pattyName: String = " "
    var sauceName: String = " "
    var cheeseName: String = " "
    var sideName: String = " "
    var drinkName: String = " "
    var vegetableName: String = " "
    var burgerPrice: Int = 0
    var sidePrice: Int = 0
    var drinkPrice: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var num = 0


        requestLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            lateinit var drawable: Drawable
            if(it.resultCode == RESULT_OK){
                val resultData = it.data?.getStringExtra("result")
                Log.d("finishJob","yes")
                drawable = matchDrawable(resultData)
                newbutton.setBackgroundColor(Color.parseColor("#00ff0000"));
                newbutton.typeface = resources.getFont(R.font.rixinooariduriregular)
                newbutton.setTextColor(resources.getColor(R.color.brown_600))
                newbutton.text = resultData
                newbutton.textSize = changeDP(30).toFloat()

            }
        }
        binding.addBtn.setOnClickListener {
            if(addmenucount <= 2) {
                val addOrderIntent = Intent(this, AddMenuDialog::class.java)
                intent.putExtra("data1", "hi")
                requestLaunch.launch(addOrderIntent)

                var newStyle = ContextThemeWrapper(this, R.style.Button_Border)
                var btn = Button(newStyle)
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                btn.layoutParams = layoutParams
                layoutParams.width = changeDP(150)
                layoutParams.height = changeDP(30)
                layoutParams.setMargins(changeDP(6), changeDP(6), changeDP(6), changeDP(6))
//                when (addmenucount) {
//                    0 -> btn.id = R.id.addMenu1
//                    1 -> btn.id = R.id.addMenu2
//                    2 -> btn.id = R.id.addMenu3
//                }
                binding.addButtonView.addView(btn)
                addmenucount++
                newbutton = btn
//                newbutton = findViewById<Button>(btn.id)
                newbutton.setOnClickListener{
                    binding.addButtonView.removeView(btn)
                    addmenucount--
                }
            }
            else{
                Toast.makeText(this, "더이상 메뉴를 추가할 수 없습니다!", Toast.LENGTH_SHORT).show()
            }

        }



        binding.btnMainPatty.setOnClickListener {
            val dialog = PattyDialog(this)
            dialog.onDialog()

            dialog.setOnClickListener(object : PattyDialog.OnDialogClickListener {
                override fun onClicked(name: String) {
                    lateinit var drawable: Drawable
                    pattyName = name
                    Log.d("dialog",name)
                    when (name) {
                        "bbq" -> drawable = resources.getDrawable(R.drawable.bbq)
                        "chicken" -> drawable = resources.getDrawable(R.drawable.chicken)
                        "egg" -> drawable = resources.getDrawable(R.drawable.eggsalad)
                        "shrimp" -> drawable = resources.getDrawable(R.drawable.shrimp)
                        "steak" -> drawable = resources.getDrawable(R.drawable.steak)
                    }
                    binding.btnMainPatty.text = pattyName
//                    binding.btnMainPatty.background=drawable
                }
            })


        }

        binding.btnMainSauce.setOnClickListener {
            val dialog = SauceDialog(this)
            dialog.onDialog()

            dialog.setOnClickListener(object : SauceDialog.OnDialogClickListener {
                override fun onClicked(name: String) {
                    lateinit var drawable: Drawable
                    sauceName = name
                    Log.d("dialog",name)
                    when (name) {
                        "bbq" -> drawable = resources.getDrawable(R.drawable.bbqsauce)
                        "garlic" -> drawable = resources.getDrawable(R.drawable.garlicsauce)
                        "hotchili" -> drawable = resources.getDrawable(R.drawable.hotchilisauce)
                        "sweetchili" -> drawable = resources.getDrawable(R.drawable.sweetchilisauce)
                        "tartar" -> drawable = resources.getDrawable(R.drawable.tartarsauce)
                    }
                    binding.btnMainSauce.text = sauceName
//                    binding.btnMainSauce.background=drawable
                }
            })
        }

        binding.btnMainCheese.setOnClickListener {
            val dialog = CheeseDialog(this)
            dialog.onDialog()

            dialog.setOnClickListener(object : CheeseDialog.OnDialogClickListener {
                override fun onClicked(name: String) {
                    lateinit var drawable: Drawable
                    cheeseName = name
                    Log.d("dialog",name)
                    when (name) {
                        "mozza" -> drawable = resources.getDrawable(R.drawable.mozzacheese)
                        "cheddar" -> drawable = resources.getDrawable(R.drawable.cheddarcheese)
                    }
                    binding.btnMainCheese.text = cheeseName
//                    binding.btnMainCheese.background=drawable
                }
            })
        }

        binding.btnMainVegetable.setOnClickListener {
            val dialogBinding = DialogVegetableBinding.inflate(layoutInflater)
            lateinit var drawable: Drawable
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
                            vegetableName = "onion"
                            drawable = resources.getDrawable(R.drawable.onion)
                        }
                        R.id.pickleIcon -> {
                            vegetableName = "pickle"
                            drawable = resources.getDrawable(R.drawable.pickle)
                        }
                        R.id.tomatoIcon -> {
                            vegetableName = "tomato"
                            drawable = resources.getDrawable(R.drawable.tomato)
                        }
                    }
//                    binding.btnMainVegetable.text = vegetableName
                    binding.btnMainVegetable.text = vegetableName
//                    binding.btnMainVegetable.background = drawable
                }
                //만약 닫기 클릭 후에 변경되기 하려면 setPositiveButton의 두번째 인자로 리스너 등록
                setPositiveButton("닫기", null)
                show()
            }
        }
        //binding.addBtn.setOnClickListener(){}
        binding.addSideBtn.setOnClickListener {
            if (sidecount <= 2) {
                val dialog = SideDialog(this)
                lateinit var sideimg: Drawable
                dialog.onDialog()

                var newStyle = ContextThemeWrapper(this, R.style.Button_Border)
                var btn = Button(newStyle)
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                btn.layoutParams = layoutParams
                layoutParams.width = changeDP(80)
                layoutParams.height = changeDP(80)
                layoutParams.setMargins(changeDP(10), changeDP(10), changeDP(10), changeDP(10))

                dialog.setOnClickListener(object : SideDialog.OnDialogClickListener {
                    override fun onClicked(name: String) {

                        sideName = name
                        Log.d("dialog", name)
                        when (name) {
                            "frenchfries" -> sideimg =
                                resources.getDrawable(R.drawable.frenchfries)
                            "cheesefries" -> sideimg =
                                resources.getDrawable(R.drawable.cheesefries)
                            "chickenfries" -> sideimg =
                                resources.getDrawable(R.drawable.chickenfries)
                        }

                        binding.addSideButtonView.addView(btn)
                        sidecount++
                        newbutton = btn
                        newbutton.background = sideimg
                        newbutton.setOnClickListener{
                            binding.addSideButtonView.removeView(btn)
                            sidecount--
                        }
                    }
                })
            }
        }
        binding.addDrinkBtn.setOnClickListener {
            if (drinkcount <= 2) {
                val dialog = DrinkDialog(this)
                lateinit var drinkimg: Drawable
                dialog.onDialog()

                var newStyle = ContextThemeWrapper(this, R.style.Button_Border)
                var btn = Button(newStyle)
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                btn.layoutParams = layoutParams
                layoutParams.width = changeDP(80)
                layoutParams.height = changeDP(80)
                layoutParams.setMargins(changeDP(10), changeDP(10), changeDP(10), changeDP(10))
                dialog.setOnClickListener(object : DrinkDialog.OnDialogClickListener {
                    override fun onClicked(name: String) {

                        drinkName = name
                        Log.d("dialog", name)
                        when (name) {
                            "cider" -> drinkimg =
                                resources.getDrawable(R.drawable.cider)
                            "coke" -> drinkimg =
                                resources.getDrawable(R.drawable.coke)
                        }

                        binding.addDrinkButtonView.addView(btn)
                        drinkcount++
                        newbutton = btn
                        newbutton.background = drinkimg
                        newbutton.setOnClickListener{
                            binding.addDrinkButtonView.removeView(btn)
                            drinkcount--
                        }
                    }
                })
            }
        }

        binding.moreOrder.setOnClickListener{
            binding.addDrinkButtonView.removeAllViews()
            binding.addSideButtonView.removeAllViews()
            binding.addButtonView.removeAllViews()
            binding.btnMainPatty.text = "패티 고르기"
            binding.btnMainCheese.text = "치즈 고르기"
            binding.btnMainSauce.text = "소스 고르기"
            binding.btnMainVegetable.text = "야채 고르기"
            addmenucount = 0; sidecount = 0; drinkcount= 0
            pattyName=" "; sauceName=" "; cheeseName = " "
            sideName=" ";drinkName = " ";vegetableName=" "

        }
        binding.completeOrder.setOnClickListener{
            binding.addDrinkButtonView.removeAllViews()
            binding.addSideButtonView.removeAllViews()
            binding.addButtonView.removeAllViews()
            binding.btnMainPatty.text = "패티 고르기"
            binding.btnMainCheese.text = "치즈 고르기"
            binding.btnMainSauce.text = "소스 고르기"
            binding.btnMainVegetable.text = "야채 고르기"
            addmenucount = 0; sidecount = 0; drinkcount= 0
            pattyName=" "; sauceName=" "; cheeseName = " "
            sideName=" ";drinkName = " ";vegetableName=" "
            burgerPrice = 0
            sidePrice = 0
            drinkPrice = 0
        }
    }



    private fun changeDP(value: Int): Int {

        var displayMetrics = resources.displayMetrics
        var dp = (value * displayMetrics.density).roundToInt()
        return dp
    }
    private fun matchDrawable(value: String?): Drawable{
        var d1 = resources.getDrawable(R.drawable.bbq)
        when(value){
            "bbq" -> d1 = resources.getDrawable(R.drawable.bbq)
            "shrimp" -> d1 = resources.getDrawable(R.drawable.shrimp)
            "egg" -> d1 = resources.getDrawable(R.drawable.eggsalad)
            "steak" -> d1 = resources.getDrawable(R.drawable.steak)
            "chicken" -> return resources.getDrawable(R.drawable.chicken)
            "bbqsauce" -> d1 = resources.getDrawable(R.drawable.bbqsauce)
            "hotchili" -> d1 = resources.getDrawable(R.drawable.hotchilisauce)
            "sweetchili" -> d1 = resources.getDrawable(R.drawable.sweetchilisauce)
            "garlic" -> d1 = resources.getDrawable(R.drawable.garlicsauce)
            "tartar" -> d1 = resources.getDrawable(R.drawable.tartarsauce)
            "mozza" -> d1 = resources.getDrawable(R.drawable.mozzacheese)
            "cheddar" -> d1 = resources.getDrawable(R.drawable.cheddarcheese)
            "lettuce" -> d1 = resources.getDrawable(R.drawable.lettuce)
            "onion" -> d1 = resources.getDrawable(R.drawable.onion)
            "pickle" -> d1 = resources.getDrawable(R.drawable.pickle)
            "tomato" -> d1 = resources.getDrawable(R.drawable.tomato)
        }
        return d1
    }
    private fun matchPattyDrawable(value: String?): Drawable{
        var d1 = resources.getDrawable(R.drawable.bbq)
        when(value){
            "bbq" -> d1 = resources.getDrawable(R.drawable.bbq)
            "shrimp" -> d1 = resources.getDrawable(R.drawable.shrimp)
            "egg" -> d1 = resources.getDrawable(R.drawable.eggsalad)
            "steak" -> d1 = resources.getDrawable(R.drawable.steak)
            "chicken" -> return resources.getDrawable(R.drawable.chicken)
        }
        return d1
    }
    private fun matchSauceDrawable(value: String?): Drawable{
        lateinit var d1 : Drawable
        when(value){
            "bbq" -> d1 = resources.getDrawable(R.drawable.bbqsauce)
            "hotchili" -> d1 = resources.getDrawable(R.drawable.hotchilisauce)
            "sweetchili" -> d1 = resources.getDrawable(R.drawable.sweetchilisauce)
            "garlic" -> d1 = resources.getDrawable(R.drawable.garlicsauce)
            "tartar" -> d1 = resources.getDrawable(R.drawable.tartarsauce)
        }
        return d1
    }
    private fun matchCheeseDrawable(value: String?): Drawable{
        lateinit var d1 : Drawable
        when(value){
            "mozza" -> d1 = resources.getDrawable(R.drawable.mozzacheese)
            "cheddar" -> d1 = resources.getDrawable(R.drawable.cheddarcheese)
        }
        return d1
    }
    private fun matchVegetablerawable(value: String?): Drawable{
        lateinit var d1 : Drawable
        when(value){
            "lettuce" -> d1 = resources.getDrawable(R.drawable.lettuce)
            "onion" -> d1 = resources.getDrawable(R.drawable.onion)
            "pickle" -> d1 = resources.getDrawable(R.drawable.pickle)
            "tomato" -> d1 = resources.getDrawable(R.drawable.tomato)

        }
        return d1
    }
}