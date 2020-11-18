package com.example.ocr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import kotlinx.android.synthetic.main.activity_parse__details.*

class Parse_Details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parse__details)

        supportActionBar?.setTitle("Extracted Details")


        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(this));
        }

        val py = Python.getInstance()
        val pyobj: PyObject = py.getModule("android_pythontest")

        val name_1: PyObject ?= pyobj.callAttr("getName")
        val email_1: PyObject ?= pyobj.callAttr("getEmail")
        val phone_1: PyObject ?= pyobj.callAttr("getPhone")
        val price_1: PyObject ?= pyobj.callAttr("getPrice")
        val medicine_1:PyObject ?= pyobj.callAttr("get_medicines")

        val name:TextView = findViewById(R.id.name)
        val email:TextView = findViewById(R.id.email)
        val phone:TextView = findViewById(R.id.phoneno)

        val temp = medicine_1.toString()
        val list = temp.split(" ")

        for (x in list) {
            medicines.text = medicines.text.toString() + x + "\n"
        }

        Log.i("parse_details", list.toString())
        val price: Button = findViewById(R.id.bill_bttn)

        name.text = name_1.toString()
        email.text = email_1.toString()
        phone.text = phone_1.toString()



    }
}