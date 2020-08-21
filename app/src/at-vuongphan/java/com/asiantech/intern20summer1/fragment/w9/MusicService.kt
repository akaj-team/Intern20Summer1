package com.asiantech.intern20summer1.fragment.w9

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import androidx.core.content.ContextCompat

class MusicService : Service() {
    private val filter = IntentFilter()
    private var binder = LocalBinder()
    private var positionSong: Int = 0

    companion object {
        const val CHANNEL_ID = "Music kotlin"
        private const val DEFAULT_VALUE_POSITION = 0
        fun startService(context: Context, message: Int) {
            val startIntent = Intent(
                context, MusicService
                ::class.java
            )
            startIntent.putExtra("put", message)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            context.stopService(Intent(context, MusicService::class.java))
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val position = intent.getIntExtra("put", DEFAULT_VALUE_POSITION)
        if (position != DEFAULT_VALUE_POSITION) {
            positionSong = position
        }
        return START_REDELIVER_INTENT
    }

    inner class LocalBinder : Binder() {
        internal val getService: MusicService
            get() = this@MusicService
    }
}
