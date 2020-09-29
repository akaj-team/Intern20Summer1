package com.asiantech.intern20summer1.week6.extensions

import androidx.viewpager.widget.ViewPager

internal inline fun ViewPager.addOnPageChangeListener(
    crossinline onPageScrollStateChanged: (state: Int) -> Unit = {},
    crossinline onPageScrolled: (
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) -> Unit = { _, _, _ -> },
    crossinline onPageSelected: (position: Int) -> Unit = {}
): ViewPager.OnPageChangeListener {
    val viewPager = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChanged.invoke(state)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            onPageScrolled.invoke(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            onPageSelected.invoke(position)
        }
    }
    addOnPageChangeListener(viewPager)
    return viewPager
}
