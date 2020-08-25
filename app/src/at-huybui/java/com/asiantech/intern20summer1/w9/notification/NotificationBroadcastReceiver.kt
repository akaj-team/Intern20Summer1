package com.asiantech.intern20summer1.w9.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

open class NotificationBroadcastReceiver: BroadcastReceiver() {
    companion object{
        internal const val ACTION_KEY = "player_music"
        internal const val ACTION_NAME = "action_name"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.sendBroadcast(Intent(ACTION_KEY).putExtra(ACTION_NAME,intent?.action))
    }
}
