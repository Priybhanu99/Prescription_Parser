package com.example.ocr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_medicines__details.*
import kotlinx.android.synthetic.main.med_data.view.*

class Medicines_Details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicines__details)


        val adapter = GroupAdapter<GroupieViewHolder>()
        recyler_view.adapter = adapter

        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(this));
        }

        val py = Python.getInstance()
        val pyobj: PyObject = py.getModule("android_pythontest")

        val medicine_1:PyObject ?= pyobj.callAttr("get_medicines")
        val temp = medicine_1.toString()
        val list = temp.split(" ")
        Log.i("med_data",list.toString())
        var str = ""
        for(x in list){
            val med_details:PyObject ?= pyobj.callAttr("print_medicinedetails",x)

            Log.i("med_data",x.toString())
            if(med_details.toString()=="true") {

                val medicine_name:PyObject ?= pyobj.callAttr("get_med_name",x)
                val medicine_description:PyObject ?= pyobj.callAttr("get_med_description",x)
                val medicine_side_effect:PyObject ?= pyobj.callAttr("get_med_sideeffects",x)
                val medicine_price:PyObject ?= pyobj.callAttr("get_med_price",x)
                Log.i("med_details",medicine_name.toString())
                adapter.add(items(medicine_name.toString(), medicine_description.toString(), medicine_side_effect.toString(), medicine_price.toString()))
            }
        }
    }
}

class items(val name:String,val description:String,val side_effects:String,val price:String): Item<GroupieViewHolder>(){

    override fun getLayout(): Int {
        return R.layout.med_data
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.med_name.text = name
        viewHolder.itemView.med_description.text = description

        viewHolder.itemView.med_sideeffects.text = side_effects
        viewHolder.itemView.med_price.text = price


    }

}