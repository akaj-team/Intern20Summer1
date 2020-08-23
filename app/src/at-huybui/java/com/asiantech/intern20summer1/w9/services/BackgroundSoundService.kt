package com.asiantech.intern20summer1.w9.services

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log.d
import com.asiantech.intern20summer1.w9.models.Song

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is service class to play mp3
 */

class BackgroundSoundService : Service() {
    var player = MediaPlayer()
    var song: Song? = null
    var iBinder: IBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): BackgroundSoundService {
            return this@BackgroundSoundService
        }
    }

    override fun onBind(arg0: Intent?): IBinder? {
        return iBinder
    }

    override fun onCreate() {
        super.onCreate()
        d("XXX", "[service] on Create")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player.stop()
        song = intent?.getSerializableExtra("song") as Song
        player = MediaPlayer.create(this.baseContext, Uri.parse(song?.contentUri))
        player.apply {
            isLooping = true // Set looping
            setVolume(100f, 100f)
        }
        player.start()
        return START_REDELIVER_INTENT
    }


    override fun onStart(intent: Intent?, startId: Int) {}

    fun onPauseMusic() {
        d("XXX", "on pause")
        if (player.isPlaying) {
            player.pause()
        }
    }

    fun onStartMusic() {
        d("XXX", "on start")
        if (!player.isPlaying) {
            player.start()
        }
    }

    override fun onDestroy() {}

    override fun onLowMemory() {}
}
