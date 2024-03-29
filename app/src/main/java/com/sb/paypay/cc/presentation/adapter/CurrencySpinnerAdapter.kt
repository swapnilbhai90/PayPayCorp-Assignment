package com.sb.paypay.cc.presentation.adapter


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CurrencySpinnerAdapter(
    private val context: Context,
    private val itemList: MutableMap<String, String>
) : BaseAdapter() {

    private var textView: TextView? = null

    override fun getCount(): Int {
        return itemList.size
    }

    override fun getItem(position: Int): Any {
        return itemList.entries.elementAt(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_spinner_item, null)
        val item = itemList.entries.elementAt(position)
        textView = view.findViewById<TextView>(android.R.id.text1)
        textView?.text = item.value + " " + item.key
        return view
    }

    fun setSelectedText(selectedText: String) {
        textView?.text = selectedText
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }
}
