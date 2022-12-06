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


class SauceDialog(context: Context){

    private val dialog = Dialog(context)
    private lateinit var onClickListener : OnDialogClickListener
    fun setOnClickListener(listener: OnDialogClickListener){
        onClickListener = listener
    }
    fun onDialog() {
        dialog.setContentView(R.layout.dialog_sauce)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        lateinit var drawable : Drawable
        var pattyName = " "
        var bbq = dialog.findViewById<Button>(R.id.sauceBbqIcon)
        var garlic = dialog.findViewById<Button>(R.id.sauceGarlicIcon)
        var hotchili = dialog.findViewById<Button>(R.id.sauceHotChiliIcon)
        var sweetchili = dialog.findViewById<Button>(R.id.sauceSweetChiliIcon)
        var tartar = dialog.findViewById<Button>(R.id.sauceTartarIcon)

        bbq.setOnClickListener{
            onClickListener.onClicked("bbq")
            dialog.dismiss()
        }
        garlic.setOnClickListener{
            onClickListener.onClicked("bbq")
            dialog.dismiss()
        }
        hotchili.setOnClickListener{
            onClickListener.onClicked("hotchili")
            dialog.dismiss()
        }
        sweetchili.setOnClickListener{
            onClickListener.onClicked("sweetchili")
            dialog.dismiss()
        }
        tartar.setOnClickListener{
            onClickListener.onClicked("tartar")
            dialog.dismiss()
        }

    }

    interface OnDialogClickListener
    {
        fun onClicked(name: String)
    }
}