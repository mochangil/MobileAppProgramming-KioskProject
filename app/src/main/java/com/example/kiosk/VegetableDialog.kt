package com.example.kiosk

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.WindowManager
import android.widget.Button


class VegetableDialog (context: Context){

    private val dialog = Dialog(context)
    private lateinit var onClickListener : OnDialogClickListener
    fun setOnClickListener(listener: OnDialogClickListener){
        onClickListener = listener
    }
    fun onDialog() {
        dialog.setContentView(R.layout.dialog_vegetable)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val selectedVegetable = arrayListOf<String>()
        val isSelected = booleanArrayOf(false,false,false,false)

        var lettuce = dialog.findViewById<Button>(R.id.lettuceIcon)
        var onion = dialog.findViewById<Button>(R.id.onionIcon)
        var pickle = dialog.findViewById<Button>(R.id.pickleIcon)
        var tomato = dialog.findViewById<Button>(R.id.tomatoIcon)
        var end = dialog.findViewById<Button>(R.id.endVegetableSelect)
        lettuce.setOnClickListener{
            if(!isSelected[0]){
                isSelected[0] = true
                selectedVegetable.add("양상추")
            }
            else{
                isSelected[0] = false
                selectedVegetable.remove("양상추")
            }
        }
        onion.setOnClickListener{
            if(!isSelected[1]){
                isSelected[1] = true
                selectedVegetable.add("양파")
            }
            else{
                isSelected[1] = false
                selectedVegetable.remove("양파")
            }
        }
        pickle.setOnClickListener{
            if(!isSelected[2]){
                isSelected[2] = true
                selectedVegetable.add("피클")
            }
            else{
                isSelected[2] = false
                selectedVegetable.remove("피클")
            }
        }
        tomato.setOnClickListener{
            if(!isSelected[3]){
                isSelected[3] = true
                selectedVegetable.add("토마토")
            }
            else{
                isSelected[3] = false
                selectedVegetable.remove("토마토")
            }
        }

        end.setOnClickListener{
            onClickListener.onClicked(selectedVegetable)
            dialog.dismiss()
        }

    }

    interface OnDialogClickListener
    {
        fun onClicked(list: ArrayList<String>)
    }
}