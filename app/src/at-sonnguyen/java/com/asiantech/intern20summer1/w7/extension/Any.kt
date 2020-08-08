package com.asiantech.intern20summer1.w7.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

internal fun AppCompatActivity.replaceFragment(
    containerId: Int,
    fragment: Fragment,
    isAddToBackStack: Boolean = false
): Fragment? {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(containerId, fragment, fragment.javaClass.simpleName)
    if (isAddToBackStack) {
        transaction.addToBackStack(fragment.javaClass.simpleName)
    }
    transaction.commit()
    return fragment
}