package com.example.agtafeprojects22024

import android.animation.TypeConverter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomAdapter(val context:Context,val empList:ArrayList<Employee>):BaseAdapter() {
    override fun getCount(): Int {
        return empList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, converterView:View?, parent: ViewGroup): View {
        val cView = LayoutInflater.from(context).inflate(R.layout.row_item,parent,false)
        var tvName = cView.findViewById<TextView>(R.id.tvName)
        var tvMobile = cView.findViewById<TextView>(R.id.tvMobile)
        var tvAddress = cView.findViewById<TextView>(R.id.tvAddress)
        var tvEmail = cView.findViewById<TextView>(R.id.tvEmail)
        var ivImage = cView.findViewById<ImageView>(R.id.ivImage)

        tvName.text = empList[position].name
        tvAddress.text = empList[position].address
        tvMobile.text = empList[position].mobile
        tvEmail.text = empList[position].email

        ivImage.setImageResource(context.resources.getIdentifier(
            empList[position].imageFile,"drawable","com.example.agtafeprojects22024"
        ))


        return cView
    }
}