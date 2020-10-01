package com.asiantech.intern20summer1.week6

import androidx.viewpager.widget.ViewPager

internal inline fun ViewPager.addOnPageChangeListener(
    crossinline onPageScrollStateChanged: (state: Int) -> Unit = {},
    crossinline onPageScrolled: (
        position: Int,
        positionOffSet: Float,
        positionOffSetPixels: Int
    ) -> Unit = { _, _, _ -> },

    crossinline onPageSelected: (position: Int) -> Unit = {}
): Any {
    val viewPager = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChanged.invoke(state)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffSet: Float,
            positionOffSetPixels: Int
        ) {
            onPageScrolled.invoke(position, positionOffSet, positionOffSetPixels)
        }

        override fun onPageSelected(position: Int) {
            onPageSelected.invoke(position)
        }
    }
    addOnPageChangeListener(viewPager)
    return viewPager
}
