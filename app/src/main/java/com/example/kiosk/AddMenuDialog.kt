package com.example.kiosk

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kiosk.databinding.ActivityMainpageBinding
import com.example.kiosk.databinding.DialogAddmenuBinding
import com.example.kiosk.databinding.DialogPattyBinding
import com.example.kiosk.databinding.DialogVegetableBinding

public class AddMenuDialog : AppCompatActivity() {

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
        val binding = DialogAddmenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data1 = intent.getStringExtra("data1")
        Log.d("sendIntentData","Yes")

        binding.addPatty.setOnClickListener{
            val dialog = PattyDialog(this)
            dialog.onDialog()

            dialog.setOnClickListener(object : PattyDialog.OnDialogClickListener {
                override fun onClicked(name: String) {
                    Log.d("setOnClickDialog", "onclicked")
                    pattyName = name
                    Log.d("dialog",name)
                    when (name) {
                        "bbq" -> {
                            drawable = resources.getDrawable(R.drawable.bbq)
                        }
                        "chicken" -> {
                            drawable = resources.getDrawable(R.drawable.chicken)
                        }
                        "egg" -> {
                            drawable = resources.getDrawable(R.drawable.eggsalad)
                        }
                        "shrimp" -> {
                            drawable = resources.getDrawable(R.drawable.shrimp)
                        }
                        "steak" -> {
                            drawable = resources.getDrawable(R.drawable.steak)
                        }
                    }
                    intent.putExtra("result","$pattyName")
                    setResult(RESULT_OK,intent)
                    finish()
                }
            })
        }
        binding.addCheese.setOnClickListener{
            val dialog = SauceDialog(this)
            dialog.onDialog()

            dialog.setOnClickListener(object : SauceDialog.OnDialogClickListener {
                override fun onClicked(name: String) {

                    sauceName = name
                    Log.d("dialog",name)
                    when (name) {
                        "bbqsauce" -> drawable = resources.getDrawable(R.drawable.bbqsauce)
                        "garlic" -> drawable = resources.getDrawable(R.drawable.garlicsauce)
                        "hotchili" -> drawable = resources.getDrawable(R.drawable.hotchilisauce)
                        "sweetchili" -> drawable = resources.getDrawable(R.drawable.sweetchilisauce)
                        "tartar" -> drawable = resources.getDrawable(R.drawable.tartarsauce)
                    }
                    intent.putExtra("result","$sauceName")
                    setResult(RESULT_OK,intent)
                    finish()
                }
            })
        }
        binding.addSauce.setOnClickListener{
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
                    intent.putExtra("result","$cheeseName")
                    setResult(RESULT_OK,intent)
                    finish()
                }
            })
        }
        binding.addVegetable.setOnClickListener{
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
                    intent.putExtra("result","$vegetableName")
                    setResult(RESULT_OK,intent)
                    finish()
                }
                //만약 닫기 클릭 후에 변경되기 하려면 setPositiveButton의 두번째 인자로 리스너 등록
                setPositiveButton("닫기", null)
                show()
            }
        }
    }
}


//        private lateinit var onClickListener: OnDialogClickListener
//        fun setOnClickListener(listener: OnDialogClickListener) {
//            onClickListener = listener
//        }
//
//        fun onDialog() {
//            dialog.setContentView(R.layout.dialog_sauce)
//            dialog.window!!.setLayout(
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT
//            )
//            dialog.setCanceledOnTouchOutside(true)
//            dialog.setCancelable(true)
//            dialog.show()
//
//            fun setOnClickListener(listener: AddMenuDialog.OnDialogClickListener) {
//                onClickListener = listener
//            }
//
//            lateinit var drawable: Drawable
//            var pattyName = " "
//            var patty = dialog.findViewById<Button>(R.id.addPatty)
//            var sauce = dialog.findViewById<Button>(R.id.addSauce)
//            var cheese = dialog.findViewById<Button>(R.id.addCheese)
//            var vegetable = dialog.findViewById<Button>(R.id.addVegetable)
//            var endSelect = dialog.findViewById<Button>(R.id.endSelect)
//
//            patty.setOnClickListener {
//                pattyDialog.setContentView(R.layout.dialog_sauce)
//                pattyDialog.window!!.setLayout(
//                    WindowManager.LayoutParams.MATCH_PARENT,
//                    WindowManager.LayoutParams.WRAP_CONTENT
//                )
//                pattyDialog.setCanceledOnTouchOutside(true)
//                pattyDialog.setCancelable(true)
//                pattyDialog.show()
//
//                var pattyName = " "
//                var bbq = dialog.findViewById<Button>(R.id.pattyBbqIcon)
//                var chicken = dialog.findViewById<Button>(R.id.pattyChickenIcon)
//                var egg = dialog.findViewById<Button>(R.id.pattyEggIcon)
//                var steak = dialog.findViewById<Button>(R.id.pattySteakIcon)
//                var shrimp = dialog.findViewById<Button>(R.id.pattyShrimpIcon)
//
//                bbq.setOnClickListener {
//                    onClickListener.onClicked("bbq")
//                    dialog.dismiss()
//                }
//                chicken.setOnClickListener {
//                    onClickListener.onClicked("chicken")
//                    dialog.dismiss()
//                }
//                egg.setOnClickListener {
//                    onClickListener.onClicked("egg")
//                    dialog.dismiss()
//                }
//                shrimp.setOnClickListener {
//                    onClickListener.onClicked("shrimp")
//                    dialog.dismiss()
//                }
//                steak.setOnClickListener {
//                    onClickListener.onClicked("steak")
//                    dialog.dismiss()
//                }
//
//            }

//
//            val dialog = PattyDialog(this.context)
//            dialog.onDialog()
//
//            dialog.setOnClickListener(object : PattyDialog.OnDialogClickListener {
//                override fun onClicked(name: String) {
//
//                    pattyName = name
//                    Log.d("dialog",name)
//                    when (name) {
//                        "bbq" -> drawable = resources.getDrawable(R.drawable.bbq)
//                        "chicken" -> drawable = resources.getDrawable(R.drawable.chicken)
//                        "egg" -> drawable = resources.getDrawable(R.drawable.eggsalad)
//                        "shrimp" -> drawable = resources.getDrawable(R.drawable.shrimp)
//                        "steak" -> drawable = resources.getDrawable(R.drawable.steak)
//                    }
//                    binding.btnMainPatty.background=drawable
//                }
//            })
//            onClickListener.onClicked("bbq")
//            dialog.dismiss()
//        }



//            sauce.setOnClickListener {
//                onClickListener.onClicked("bbq")
//                dialog.dismiss()
//            }
//            cheese.setOnClickListener {
//                onClickListener.onClicked("hotchili")
//                dialog.dismiss()
//            }
//            vegetable.setOnClickListener {
//                onClickListener.onClicked("sweetchili")
//                dialog.dismiss()
//            }
//            endSelect.setOnClickListener {
//                onClickListener.onClicked("tartar")
//                dialog.dismiss()
//            }
//
//        }
//
//        interface OnDialogClickListener {
//            fun onClicked(name: String)
//        }
//    }
//}