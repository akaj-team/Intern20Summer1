package com.asiantech.intern20summer1.week6.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.asiantech.intern20summer1.week6.ModelObject

class CustomPagerAdapter(context: Context) : PagerAdapter() {

    private var mContext: Context = context

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val modelObject = ModelObject.values()[position]
        val inflater = LayoutInflater.from(mContext)
        val layout =
            inflater.inflate(modelObject.layoutResId, collection, false) as ViewGroup
        collection.addView(layout)

        return layout
    }

    override fun destroyItem(
        collection: ViewGroup,
        position: Int,
        view: Any
    ) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return ModelObject.values().size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val customPagerEnum = ModelObject.values()[position]
        return mContext.getString(customPagerEnum.titleResId)
    }

    init {
        mContext = context
    }
}
