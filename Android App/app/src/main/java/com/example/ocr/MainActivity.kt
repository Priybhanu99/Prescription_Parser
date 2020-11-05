package com.example.ocr

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        
        click_id.setOnClickListener {
            takePhoto()
        }


        upload_id.setOnClickListener {
            selectImageInAlbum()
        }
    }

    fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, 0)
        }
    }

    fun takePhoto() {
        val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent1.resolveActivity(packageManager) != null) {
            startActivityForResult(intent1, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val bitmap: Bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(bitmap)
            Log.i("MainActivity", "image is taken")

            val firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap)
            val firebaseVisionTextRecognizer= FirebaseVision.getInstance().onDeviceTextRecognizer
            firebaseVisionTextRecognizer.processImage(firebaseVisionImage).addOnSuccessListener(
            )
            {
                processTextRecognition(it)

            }.addOnFailureListener {
                //show error
            }

        }

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
//            imageView.setImageURI(null)
            imageView.setImageURI(data?.data)

            val imageUri: Uri? = data?.data
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)

            val firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap)
            val firebaseVisionTextRecognizer= FirebaseVision.getInstance().onDeviceTextRecognizer
            firebaseVisionTextRecognizer.processImage(firebaseVisionImage).addOnSuccessListener(
            )
            {
                processTextRecognition(it)

            }.addOnFailureListener {
                //show error
            }
        }
    }

    private fun processTextRecognition(text: FirebaseVisionText) {

        text_from_image.text = ""
        val textBlock: List<FirebaseVisionText.TextBlock> = text.textBlocks
        if (textBlock.isEmpty()) {
            text_from_image.text = "No Text found"
            return
        }
        for (index in 0 until textBlock.size) {
            val lines: List<FirebaseVisionText.Line> = textBlock[index].lines
            for (j in 0 until lines.size) {
                val element: List<FirebaseVisionText.Element> = lines[j].elements
                for (k in 0 until element.size) {
                    text_from_image.append(" " + element[k].text)
                }
            }

        }

        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(this));
        }

        val py = Python.getInstance()
        val pyobj: PyObject = py.getModule("android_pythontest")

        val obj: PyObject ?= pyobj.callAttr("main",text_from_image.text)
        Log.i("MainActivity",text_from_image.text.toString())

        Log.i("MainActivity",obj.toString())



    }


}