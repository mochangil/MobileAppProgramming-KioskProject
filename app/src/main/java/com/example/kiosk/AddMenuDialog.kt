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
                    intent.putExtra("result","$pattyName")
                    setResult(RESULT_OK,intent)
                    finish()
                }
            })
        }
        binding.addCheese.setOnClickListener{
            val dialog = CheeseDialog(this)
            dialog.onDialog()

            dialog.setOnClickListener(object : CheeseDialog.OnDialogClickListener {
                override fun onClicked(name: String) {

                    sauceName = name
                    Log.d("dialog",name)
                    intent.putExtra("result","$sauceName")
                    setResult(RESULT_OK,intent)
                    finish()
                }
            })
        }
        binding.addSauce.setOnClickListener{
            val dialog = SauceDialog(this)
            dialog.onDialog()

            dialog.setOnClickListener(object : SauceDialog.OnDialogClickListener {
                override fun onClicked(name: String) {

                    cheeseName = name
                    Log.d("dialog",name)
                    intent.putExtra("result","$cheeseName")
                    setResult(RESULT_OK,intent)
                    finish()
                }
            })
        }
        binding.addVegetable.setOnClickListener{
            val dialog = VegetableDialog(this)
            dialog.onDialog()
            var selectedVegetable = arrayListOf<String>()
            selectedVegetable.clear()
            vegetableName = ""
            dialog.setOnClickListener(object : VegetableDialog.OnDialogClickListener {
                override fun onClicked(list: ArrayList<String>) {

                    selectedVegetable = list
                    for(veges in selectedVegetable){
                        vegetableName += veges+" "
                    }
                    intent.putExtra("result","$vegetableName")
                    setResult(RESULT_OK,intent)
                    finish()
                }
            })
        }
        binding.endAddSelect.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}

