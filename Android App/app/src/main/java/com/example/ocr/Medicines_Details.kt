package com.example.ocr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        var str = ""
        for(x in list){
            val med_details:PyObject ?= pyobj.callAttr("print_medicinedetails",x)
            if(med_details!=null) {
                adapter.add(items("", "", "", ""))
            }
        }
    }
}

class items(val name:String,val description:String,val side_effects:String,val price:String): Item<GroupieViewHolder>(){

    override fun getLayout(): Int {
        return R.layout.med_data
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.name_data.text = name
        viewHolder.itemView.description_data.text = description
    }

}