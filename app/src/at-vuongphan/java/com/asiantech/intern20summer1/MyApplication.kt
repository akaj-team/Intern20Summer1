package com.asiantech.intern20summer1

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

/**
 * Asian tech Co., ltd.
 * Intern20Summer1 Project
 * Create by Vuong Phan T. on 9/25/20.
 * This is MyApplication TODO("Not yes implemented")
 */
class MyApplication : Application(){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
