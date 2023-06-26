package com.example.piggy.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.piggy.R

class CustomAdapter(context: Context, private val list: List<SpendItem>) :
    ArrayAdapter<SpendItem>(context, R.layout.spend_item, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (convertView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.spend_item, parent, false)
        }

        val data = list[position]

        val nameTextView: TextView? = listItemView?.findViewById(R.id.name)
        val priceTextView: TextView? = listItemView?.findViewById(R.id.price)

        nameTextView?.text = data.label
        priceTextView?.text = data.price.toString()

        return listItemView!!
    }
}