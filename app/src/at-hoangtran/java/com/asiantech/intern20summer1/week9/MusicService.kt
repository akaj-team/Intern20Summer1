package com.asiantech.intern20summer1.week9

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.asiantech.intern20summer1.R

class MusicService : Service() {
    private var totalTime: Int = 0
    private lateinit var mp: MediaPlayer

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        mp = MediaPlayer.create(this, R.raw.bensound)
        totalTime = mp.duration
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        mp.stop()
    }
}