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


class CheeseDialog(context: Context){

    private val dialog = Dialog(context)
    private lateinit var onClickListener : OnDialogClickListener
    fun setOnClickListener(listener: OnDialogClickListener){
        onClickListener = listener
    }
    fun onDialog() {
        dialog.setContentView(R.layout.dialog_cheese)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        lateinit var drawable : Drawable
        var pattyName = " "
        var mozza = dialog.findViewById<Button>(R.id.mozzaCheeseIcon)
        var cheddar = dialog.findViewById<Button>(R.id.cheddarCheeseIcon)

        mozza.setOnClickListener{
            onClickListener.onClicked("mozza")
            dialog.dismiss()
        }
        cheddar.setOnClickListener{
            onClickListener.onClicked("cheddar")
            dialog.dismiss()
        }

    }

    interface OnDialogClickListener
    {
        fun onClicked(name: String)
    }
}