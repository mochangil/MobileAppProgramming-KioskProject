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


class PattyDialog(context: Context){

    private val dialog = Dialog(context)
    private lateinit var onClickListener : OnDialogClickListener
    fun setOnClickListener(listener: OnDialogClickListener){
        onClickListener = listener
    }
    fun onDialog() {
        dialog.setContentView(R.layout.dialog_patty)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        var bbq = dialog.findViewById<Button>(R.id.pattyBbqIcon)
        var chicken = dialog.findViewById<Button>(R.id.pattyChickenIcon)
        var egg = dialog.findViewById<Button>(R.id.pattyEggIcon)
        var steak = dialog.findViewById<Button>(R.id.pattySteakIcon)
        var shrimp = dialog.findViewById<Button>(R.id.pattyShrimpIcon)
        var end = dialog.findViewById<Button>(R.id.endPattySelect)

        end.setOnClickListener{
            dialog.dismiss()
        }
        bbq.setOnClickListener{
            onClickListener.onClicked("불고기")
            dialog.dismiss()
        }
        chicken.setOnClickListener{
            onClickListener.onClicked("치킨")
            dialog.dismiss()
        }
        egg.setOnClickListener{
            onClickListener.onClicked("달걀샐러드")
            dialog.dismiss()
        }
        shrimp.setOnClickListener{
            onClickListener.onClicked("새우")
            dialog.dismiss()
        }
        steak.setOnClickListener{
            onClickListener.onClicked("스테이크")
            dialog.dismiss()
        }

    }

    interface OnDialogClickListener
    {
        fun onClicked(name: String)
    }
}