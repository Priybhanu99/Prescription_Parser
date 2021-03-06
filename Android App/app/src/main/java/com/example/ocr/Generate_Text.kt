package com.example.ocr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_generate__text.*

class Generate_Text : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate__text)


        supportActionBar?.setTitle("Text from Image")

        val data:String = intent.getStringExtra("mykey")
        text_from_image.text = data

        parse_details_bttn.setOnClickListener{
            val intent = Intent(this,Parse_Details::class.java)
            startActivity(intent)
        }
    }
}