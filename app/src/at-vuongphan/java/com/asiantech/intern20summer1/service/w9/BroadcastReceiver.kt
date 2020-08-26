package com.asiantech.intern20summer1.service.w9

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("aaa", "onReceive: aaaa")
    }
}
