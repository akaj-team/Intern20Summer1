package com.asiantech.intern20summer1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.asiantech.intern20summer1.R

class PagerAdapterActivityTwo(
    private var list: List<Int>,
    private var ensign: List<Int>,
    private var title: List<String>,
    private var context: Context
) : PagerAdapter() {
    private lateinit var imameView: ImageView
    private lateinit var iconImageViewOne: ImageView
    private lateinit var iconImageViewTwo: ImageView
    private lateinit var textViewOne: TextView
    private lateinit var textViewTwo: TextView
    private lateinit var layoutInflater: LayoutInflater
    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == (`object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater=LayoutInflater.from(context)
        val view=layoutInflater.inflate(R.layout.image_view, container, false)
        initViews(view)
        setData(position)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title=" "
        when (position) {
            0 -> title=context.getString(R.string.tab_layout_description_one)
            1 -> title=context.getString(R.string.tab_layout_description_two)
            2 -> title=context.getString(R.string.tab_layout_description_three)
        }
        return title
    }

    private fun initViews(view: View) {
        imameView=view.findViewById(R.id.imgView)
        iconImageViewOne=view.findViewById(R.id.imgIcon)
        iconImageViewTwo=view.findViewById(R.id.imgIconTwo)
        textViewOne=view.findViewById(R.id.tvNameIcon)
        textViewTwo=view.findViewById(R.id.tvNameIconTwo)
    }

    private fun setData(position: Int) {
        iconImageViewOne.setImageResource(ensign[position])
        iconImageViewTwo.setImageResource(ensign[position])
        textViewOne.text=title[position]
        textViewTwo.text=title[position]
        imameView.setImageResource(list[position])
    }
}
