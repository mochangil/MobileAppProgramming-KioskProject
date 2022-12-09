package com.example.kiosk

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kiosk.databinding.ActivityMainpageBinding
import com.example.kiosk.databinding.DialogPattyBinding


class DrinkDialog(context: Context){

    private val dialog = Dialog(context)
    private lateinit var onClickListener : OnDialogClickListener
    fun setOnClickListener(listener: OnDialogClickListener){
        onClickListener = listener
    }
    fun onDialog() {
        dialog.setContentView(R.layout.dialog_drinkmenu)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        lateinit var drawable : Drawable
        var pattyName = " "
        var cider = dialog.findViewById<Button>(R.id.drinkCiderIcon)
        var coke = dialog.findViewById<Button>(R.id.drinkCokeIcon)


        cider.setOnClickListener{
            onClickListener.onClicked("cider")
            dialog.dismiss()
        }
        coke.setOnClickListener{
            onClickListener.onClicked("coke")
            dialog.dismiss()
        }


    }

    interface OnDialogClickListener
    {
        fun onClicked(name: String)
    }
}