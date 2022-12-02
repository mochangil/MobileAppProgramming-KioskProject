package com.example.kiosk

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kiosk.databinding.AdminPasswordBinding
import com.example.kiosk.databinding.ChangePasswordBinding
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class AdminPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val APbinding = AdminPasswordBinding.inflate(layoutInflater)
        setContentView(APbinding.root)

        // 첫 실행 시 파일이 파일이 없을 경우 최초 비밀번호 생성
        // 최초 비밀번호 : 1234
        val passFilepath = filesDir.path + "/password.txt"

        if (getPassword(passFilepath) == -1) {
            val file = File(passFilepath)
            val fileWriter = FileWriter(file, false)
            val bufferedWriter = BufferedWriter(fileWriter)

            bufferedWriter.append("1234")
            bufferedWriter.close()
        }

        APbinding.button0.setOnClickListener {
            var ed_str: String = APbinding.passBox.text.toString()
            ed_str = ed_str + "0"
            APbinding.passBox.setText(ed_str)
        }
        APbinding.button1.setOnClickListener {
            var ed_str: String = APbinding.passBox.text.toString()
            ed_str = ed_str + "1"
            APbinding.passBox.setText(ed_str)
        }
        APbinding.button2.setOnClickListener {
            var ed_str: String = APbinding.passBox.text.toString()
            ed_str = ed_str + "2"
            APbinding.passBox.setText(ed_str)
        }
        APbinding.button3.setOnClickListener {
            var ed_str: String = APbinding.passBox.text.toString()
            ed_str = ed_str + "3"
            APbinding.passBox.setText(ed_str)
        }
        APbinding.button4.setOnClickListener {
            var ed_str: String = APbinding.passBox.text.toString()
            ed_str = ed_str + "4"
            APbinding.passBox.setText(ed_str)
        }
        APbinding.button5.setOnClickListener {
            var ed_str: String = APbinding.passBox.text.toString()
            ed_str = ed_str + "5"
            APbinding.passBox.setText(ed_str)
        }
        APbinding.button6.setOnClickListener {
            var ed_str: String = APbinding.passBox.text.toString()
            ed_str = ed_str + "6"
            APbinding.passBox.setText(ed_str)
        }
        APbinding.button7.setOnClickListener {
            var ed_str: String = APbinding.passBox.text.toString()
            ed_str = ed_str + "7"
            APbinding.passBox.setText(ed_str)
        }
        APbinding.button8.setOnClickListener {
            var ed_str: String = APbinding.passBox.text.toString()
            ed_str = ed_str + "8"
            APbinding.passBox.setText(ed_str)
        }
        APbinding.button9.setOnClickListener {
            var ed_str: String = APbinding.passBox.text.toString()
            ed_str = ed_str + "9"
            APbinding.passBox.setText(ed_str)
        }
        APbinding.back.setOnClickListener {
            var ed_str: String = APbinding.passBox.text.toString()
            var n: Int = ed_str.length
            var ch_str: String = ""
            for (i: Int in 0..(n - 2))
                ch_str = ch_str + ed_str[i]
            APbinding.passBox.setText(ch_str)
        }
        APbinding.ok.setOnClickListener {
            var pass: Int = APbinding.passBox.text.toString().toInt()
            if (isPass(pass, passFilepath)) {
                // Log.d("debug", "You passed!")
                Toast.makeText(applicationContext, "로그인에 성공했습니다!", Toast.LENGTH_SHORT).show()
                val adminIntent = Intent(this, AdminMenu::class.java)
                startActivity(adminIntent)
            } else
                Toast.makeText(applicationContext, "비밀번호가 틀렸습니다!", Toast.LENGTH_SHORT).show()
        }
        // 비밀번호 변경 다이얼로그
        var cp = ChangePasswordBinding.inflate(layoutInflater)
        val passHandler = object : DialogInterface.OnClickListener {
            override fun onClick(dialogInterface: DialogInterface?, which: Int) {
                var nowPass: String = cp.nowPass.text.toString()
                var newPass: String = cp.newPass.text.toString()
                var checkPass: String = cp.checkPass.text.toString()

                if (!isPass(nowPass.toInt(), passFilepath))
                    Toast.makeText(
                        applicationContext,
                        "비밀번호가 틀렸습니다. 다시 시도해주세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    if (newPass.equals(checkPass)) {
                        changePassword(newPass.toInt(), passFilepath)
                        Toast.makeText(
                            applicationContext,
                            "비밀번호가 정상적으로 수정되었습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "새 비밀번호가 일치하지 않습니다. 다시 시도해주세요!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        APbinding.change.setOnClickListener {
            cp = ChangePasswordBinding.inflate(layoutInflater)
            AlertDialog.Builder(this).run {
                setTitle("비밀번호 변경")
                setView(cp.root)
                setPositiveButton("확인", passHandler)
                setCancelable(true)
                show()
            }.setCanceledOnTouchOutside(true)
        }
        APbinding.backPass.setOnClickListener {
            finish()
        }
    }
}