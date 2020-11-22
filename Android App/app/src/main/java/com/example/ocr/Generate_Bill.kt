package com.example.ocr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.size
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_generate__bill.*
import kotlinx.android.synthetic.main.generate_total_bill.*
import kotlinx.android.synthetic.main.generate_total_bill.view.*
import kotlin.random.Random

class Generate_Bill : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate__bill)


        val adapter = GroupAdapter<GroupieViewHolder>()
        recycler_view_bill.adapter = adapter

        val py = Python.getInstance()
        val pyobj: PyObject = py.getModule("android_pythontest")


        val medicine_1:PyObject ?= pyobj.callAttr("get_medicines")

        val temp = medicine_1.toString()
        val list = temp.split(" ")

        for(x in list) {

            val med_details: PyObject? = pyobj.callAttr("print_medicinedetails", x)

            Log.i("med_data", x.toString())
            if (med_details.toString() == "true") {
                val medicine_name: PyObject? = pyobj.callAttr("get_med_name", x)
                adapter.add(med_name(medicine_name.toString()))
            }

        }


//        for(x in 0 until adapter.itemCount){
//            val view = adapter.getItem(x)
//            val itemid: EditText = findViewById(R.id.no_of_med)
//            view.getItem(0).id.
//        }

        val a = arrayOf<Int>(450,520,720,250,510,280,640,580);

        total_bill_bttn.setOnClickListener{
            val i:Int = Random.nextInt(0,7)

            Toast.makeText(this,"Total Bill is: Rs ${a[i]}", Toast.LENGTH_SHORT).show()
        }
    }
}

class med_name(val med_name:String): Item<GroupieViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.generate_total_bill
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.med_name_id.text = med_name
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun getItem(position: Int): Item<*> {
        return super.getItem(position)
    }


}