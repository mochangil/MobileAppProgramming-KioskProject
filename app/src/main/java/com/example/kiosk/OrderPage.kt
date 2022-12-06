package com.example.kiosk

import android.content.Context
import android.content.Intent
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
import kotlin.math.roundToInt


public class OrderPage : AppCompatActivity() {


    lateinit var drawable: Drawable
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

        binding.addBtn.setOnClickListener {
            val addOrderIntent = Intent(this, AddMenuDialog::class.java)
            startActivity(addOrderIntent)
        }

//        binding.addBtn.setOnClickListener {
//            if (addmenucount <=2) {
//                val dialog = AddMenuDialog(this)
//                dialog.onDialog()
//
//                var newStyle = ContextThemeWrapper(this, R.style.Button_Border)
//                var btn = Button(newStyle)
//                val layoutParams = LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//                )
//                btn.layoutParams = layoutParams
//                layoutParams.width = changeDP(120)
//                layoutParams.height = changeDP(30)
//
//                dialog.setOnClickListener(object : AddMenuDialog.OnDialogClickListener {
//                    override fun onClicked(name: String) {
//
//                        pattyName = name
//                        Log.d("dialog", name)
//                        when (name) {
//                            "bbq" -> drawable = resources.getDrawable(R.drawable.bbq)
//                            "chicken" -> drawable = resources.getDrawable(R.drawable.chicken)
//                            "egg" -> drawable = resources.getDrawable(R.drawable.eggsalad)
//                            "shrimp" -> drawable = resources.getDrawable(R.drawable.shrimp)
//                            "steak" -> drawable = resources.getDrawable(R.drawable.steak)
//                        }
//                        when (addmenucount) {
//                            0 -> btn.id = R.id.addMenu1
//                            1 -> btn.id = R.id.addMenu2
//                            2 -> btn.id = R.id.addMenu3
//                        }
//                        binding.addButtonView.addView(btn)
//                        addmenucount++
//                        var newbutton = findViewById<Button>(btn.id)
//                        newbutton.background = drawable
//                        newbutton.setOnClickListener{
//
//                        }
//                    }
//                })
//
//            }
//            else{
//                Toast.makeText(this, "더이상 메뉴를 추가할 수 없습니다!", Toast.LENGTH_SHORT).show()
//            }
//
//        }

        binding.btnMainPatty.setOnClickListener {
            val dialog = PattyDialog(this)
            dialog.onDialog()

            dialog.setOnClickListener(object : PattyDialog.OnDialogClickListener {
                override fun onClicked(name: String) {

                    pattyName = name
                    Log.d("dialog",name)
                    when (name) {
                        "bbq" -> drawable = resources.getDrawable(R.drawable.bbq)
                        "chicken" -> drawable = resources.getDrawable(R.drawable.chicken)
                        "egg" -> drawable = resources.getDrawable(R.drawable.eggsalad)
                        "shrimp" -> drawable = resources.getDrawable(R.drawable.shrimp)
                        "steak" -> drawable = resources.getDrawable(R.drawable.steak)
                    }
                    binding.btnMainPatty.background=drawable
                }
            })


        }

        binding.btnMainSauce.setOnClickListener {
            val dialog = SauceDialog(this)
            dialog.onDialog()

            dialog.setOnClickListener(object : SauceDialog.OnDialogClickListener {
                override fun onClicked(name: String) {

                    sauceName = name
                    Log.d("dialog",name)
                    when (name) {
                        "bbq" -> drawable = resources.getDrawable(R.drawable.bbqsauce)
                        "garlic" -> drawable = resources.getDrawable(R.drawable.garlicsauce)
                        "hotchili" -> drawable = resources.getDrawable(R.drawable.hotchilisauce)
                        "sweetchili" -> drawable = resources.getDrawable(R.drawable.sweetchilisauce)
                        "tartar" -> drawable = resources.getDrawable(R.drawable.tartarsauce)
                    }
                    binding.btnMainSauce.background=drawable
                }
            })
        }

        binding.btnMainCheese.setOnClickListener {
            val dialog = CheeseDialog(this)
            dialog.onDialog()

            dialog.setOnClickListener(object : CheeseDialog.OnDialogClickListener {
                override fun onClicked(name: String) {

                    cheeseName = name
                    Log.d("dialog",name)
                    when (name) {
                        "mozza" -> drawable = resources.getDrawable(R.drawable.mozzacheese)
                        "cheddar" -> drawable = resources.getDrawable(R.drawable.cheddarcheese)
                    }
                    binding.btnMainCheese.background=drawable
                }
            })
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
                layoutParams.width = changeDP(60)
                layoutParams.height = changeDP(60)
                layoutParams.setMargins(changeDP(10), changeDP(10), changeDP(10), 0)

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
                        when (addmenucount) {
                            0 -> btn.id = R.id.addSide1
                            1 -> btn.id = R.id.addSide2
                            2 -> btn.id = R.id.addSide3
                        }
                        binding.addSideButtonView.addView(btn)
                        sidecount++
                        var newbutton = findViewById<Button>(btn.id)
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
                layoutParams.width = changeDP(60)
                layoutParams.height = changeDP(60)
                layoutParams.setMargins(changeDP(10), changeDP(20), changeDP(10), 0)
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
                        when (drinkcount) {
                            0 -> btn.id = R.id.addSide1
                            1 -> btn.id = R.id.addSide2
                            2 -> btn.id = R.id.addSide3
                        }
                        binding.addDrinkButtonView.addView(btn)
                        drinkcount++
                        var newbutton = findViewById<Button>(btn.id)
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
            binding.btnMainPatty.background = resources.getDrawable(R.drawable.meat_choice)
            binding.btnMainCheese.background = resources.getDrawable(R.drawable.cheese_choice)
            binding.btnMainSauce.background = resources.getDrawable(R.drawable.sauce_choice)
            binding.btnMainVegetable.background = resources.getDrawable(R.drawable.vege_choice)
            addmenucount = 0; sidecount = 0; drinkcount= 0
            pattyName=" "; sauceName=" "; cheeseName = " "
            sideName=" ";drinkName = " ";vegetableName=" "

        }
        binding.completeOrder.setOnClickListener{
            binding.addDrinkButtonView.removeAllViews()
            binding.addSideButtonView.removeAllViews()
            binding.addButtonView.removeAllViews()
            binding.btnMainPatty.background = resources.getDrawable(R.drawable.meat_choice)
            binding.btnMainCheese.background = resources.getDrawable(R.drawable.cheese_choice)
            binding.btnMainSauce.background = resources.getDrawable(R.drawable.sauce_choice)
            binding.btnMainVegetable.background = resources.getDrawable(R.drawable.vege_choice)
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
}