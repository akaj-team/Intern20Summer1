package com.asiantech.intern20summer1.test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationService: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.sendBroadcast(Intent("Song_Song").putExtra("actionName",intent?.action))
    }

}