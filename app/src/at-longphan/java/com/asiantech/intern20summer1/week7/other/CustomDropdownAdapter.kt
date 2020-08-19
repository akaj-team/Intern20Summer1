package com.asiantech.intern20summer1.week7.other

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.asiantech.intern20summer1.R

class CustomDropDownAdapter(val context: Context, var listItemsTxt: Array<String>) : BaseAdapter() {

    val inflater: LayoutInflater = LayoutInflater.from(context)

    @SuppressLint("ResourceAsColor")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.view_drop_down_menu, parent, false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }

        // setting adapter item height programmatically.
        val params = view.layoutParams
        params.height = 60
        view.layoutParams = params

        vh.label.text = listItemsTxt[position]
        vh.label.height = 52
        if (position == 0) {
            vh.label.setTextColor(R.color.colorEditTextHintWeek7Text)
        }
        return view
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return listItemsTxt.size
    }

    private class ItemRowHolder(row: View?) {
        var label: TextView = row?.findViewById(R.id.txtDropDownLabel) as TextView
    }
}
