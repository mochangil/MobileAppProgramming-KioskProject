package com.example.kiosk

import android.util.Log
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

fun getPassword(path : String) : Int {
    var pass : Int = 0
    try {
        val file = FileReader(path)
        pass = file.readLines()[0].toInt()
    } catch (e : Exception) {
        pass = -1
    }

    return pass
}

fun isPass(inputPass : Int, path: String) : Boolean {
    if (inputPass == getPassword(path)) return true
    else return false
}

fun changePassword(inputPass : Int, path: String) {
    val writer = FileWriter(path, false)

    try {
        writer.write(inputPass.toString())
    } catch (e : IOException) {
        Log.e("Error", "Pass Change Error.")
    } finally {
        writer.close()
    }
}

fun create_dummy_inv(path : String, content : String) {
    val writer = FileWriter(path, false)

    try {
        writer.write(content)
    } catch (e : IOException) {
        Log.e("Error", "Pass Change Error.")
    } finally {
        writer.close()
    }
}

fun get_info(path : String) {

}

data class TestMessage (
    var mes : String = "",
    var type : String = "") {}

fun main() {

}