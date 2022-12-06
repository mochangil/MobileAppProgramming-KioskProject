package com.example.kiosk

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.kiosk.databinding.DialogAddmenuBinding
import com.example.kiosk.databinding.DialogPattyBinding
import com.example.kiosk.databinding.DialogVegetableBinding

interface CustomDialogInterface{
    fun onAddButtonClicked()
    fun onCancelButtonClicked()
}

class CustomDialog(context: Context, Interface:CustomDialogInterface) : Dialog(context){

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

    private var customDialogInterface: CustomDialogInterface = Interface

    private lateinit var patty : Button
    private lateinit var cheese : Button
    private lateinit var sauce : Button
    private lateinit var vegetable : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DialogAddmenuBinding.inflate(layoutInflater)
        val pattyBinding = DialogPattyBinding.inflate(layoutInflater)

        setContentView(binding.root)

        patty = findViewById(R.id.addPatty)
        cheese = findViewById(R.id.addCheese)
        sauce = findViewById(R.id.addSauce)
        vegetable = findViewById(R.id.addVegetable)

        // 배경을 투명하게함
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        binding.addPatty.setOnClickListener {
            val dialog = PattyDialog(this.context)
            dialog.onDialog()

            dialog.setOnClickListener(object : PattyDialog.OnDialogClickListener {
                override fun onClicked(name: String) {
                    pattyName = name
                    Log.d("dialog",name)
                }
            })


        }


        // 취소 버튼 클릭 시 onCancelButtonClicked 호출 후 종료
        cheese.setOnClickListener {
            customDialogInterface.onCancelButtonClicked()
            dismiss()
        }
        sauce.setOnClickListener {
            customDialogInterface.onCancelButtonClicked()
            dismiss()
        }
        vegetable.setOnClickListener {
            customDialogInterface.onCancelButtonClicked()
            dismiss()
        }
    }
}
