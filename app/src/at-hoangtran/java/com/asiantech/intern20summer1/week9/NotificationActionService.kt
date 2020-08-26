package com.asiantech.intern20summer1.week9

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationActionService : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.sendBroadcast(Intent("song")
            .putExtra("actionname", intent?.action))
    }
}
