package com.example.kiosk

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.kiosk.databinding.DialogPattyBinding
import java.util.ArrayList

class PattyFragmentDialog : DialogFragment(){

    private var _binding: DialogPattyBinding? = null
    private val binding get() = _binding!!
    var pattyOutOfAmountList = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogPattyBinding.inflate(inflater, container, false)
        val view = binding.root
        // 레이아웃 배경을 투명하게 해줌, 필수 아님
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        pattyOutOfAmountList = arguments?.getStringArrayList("pattyAmount") as ArrayList<String>
        for (a in pattyOutOfAmountList) {
            Log.d("testtesttest",a)
        }
        binding.pattyBbqIcon.setOnClickListener {
            dismiss()    // 대화상자를 닫는 함수
        }
        binding.pattyChickenIcon.setOnClickListener {
            dismiss()
        }
        binding.pattyEggIcon.setOnClickListener {
            dismiss()
        }
        binding.pattyShrimpIcon.setOnClickListener {
            dismiss()
        }
        binding.pattySteakIcon.setOnClickListener {
            dismiss()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}