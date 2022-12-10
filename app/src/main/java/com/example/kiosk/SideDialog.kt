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


class SideDialog(context: Context){

    private val dialog = Dialog(context)
    private lateinit var onClickListener : OnDialogClickListener
    fun setOnClickListener(listener: OnDialogClickListener){
        onClickListener = listener
    }
    fun onDialog() {
        dialog.setContentView(R.layout.dialog_sidemenu)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        lateinit var drawable : Drawable
        var pattyName = " "
        var frenchfries = dialog.findViewById<Button>(R.id.sideFrenchfriesIcon)
        var cheesefries = dialog.findViewById<Button>(R.id.sideCheesefriesIcon)
        var chickenfries = dialog.findViewById<Button>(R.id.sideChickenfriesIcon)
        var end = dialog.findViewById<Button>(R.id.endSideSelect)

        end.setOnClickListener{
            dialog.dismiss()
        }
        frenchfries.setOnClickListener{
            onClickListener.onClicked("frenchfries")
            dialog.dismiss()
        }
        cheesefries.setOnClickListener{
            onClickListener.onClicked("cheesefries")
            dialog.dismiss()
        }
        chickenfries.setOnClickListener{
            onClickListener.onClicked("chickenfries")
            dialog.dismiss()
        }

    }

    interface OnDialogClickListener
    {
        fun onClicked(name: String)
    }
}