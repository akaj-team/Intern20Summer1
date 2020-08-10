package com.asiantech.intern20summer1.week7.other

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.content.ContextCompat


open class AutoCompleteDropDown : AppCompatAutoCompleteTextView {
    private val startClickTime: Long = 0
    private var isPopup = false
    val position: Int = android.widget.ListView.INVALID_POSITION

    companion object {
        //    implements AdapterView.OnItemClickListener
        private const val MAX_CLICK_DURATION = 200
    }

    constructor(context: Context?) : super(context) {
        //  setOnItemClickListener(this);
    }

    constructor(arg0: Context?, arg1: AttributeSet?) : super(
        arg0,
        arg1
    ) {
        //  setOnItemClickListener(this);
    }

    constructor(arg0: Context?, arg1: AttributeSet?, arg2: Int) : super(
        arg0,
        arg1,
        arg2
    ) {
        //  setOnItemClickListener(this);
    }

    override fun enoughToFilter(): Boolean {
        return true
    }

    override fun onFocusChanged(
        focused: Boolean, direction: Int,
        previouslyFocusedRect: Rect?
    ) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) {
            performFiltering("", 0)
            val imm: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
            keyListener = null
            dismissDropDown()
        } else {
            isPopup = false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                if (isPopup) {
                    dismissDropDown()
                } else {
                    requestFocus()
                    showDropDown()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun showDropDown() {
        super.showDropDown()
        isPopup = true
    }

    override fun dismissDropDown() {
        super.dismissDropDown()
        isPopup = false
    }

    @SuppressLint("ResourceType")
    override fun setCompoundDrawablesWithIntrinsicBounds(
        left: Drawable?,
        top: Drawable?,
        right: Drawable?,
        bottom: Drawable?
    ) {
        var right = right
        val dropdownIcon =
            ContextCompat.getDrawable(context, android.R.layout.simple_spinner_dropdown_item)
        if (dropdownIcon != null) {
            right = dropdownIcon
            right.mutate().alpha = 66
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            super.setCompoundDrawablesRelativeWithIntrinsicBounds(left, top, right, bottom)
        } else {
            super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
        }
    }
}
