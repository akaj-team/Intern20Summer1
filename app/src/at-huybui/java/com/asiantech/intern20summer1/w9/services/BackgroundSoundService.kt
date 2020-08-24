package com.asiantech.intern20summer1.w9.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log.d
import com.asiantech.intern20summer1.w9.models.Song

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is service class to play mp3
 */

class BackgroundSoundService : Service() {
    var audioPlayer = MediaPlayer()
    var songList = mutableListOf<Song>()
    var songPlaying: Song? = null
    var currentTime = 0
    var iBinder: IBinder = LocalBinder()
    var songPosition = 0

    internal var onUpdateCurrentPosition: (Int) -> Unit = {}
    internal var onPausePlayer: () -> Unit = {}
    internal var onResumePlayer: () -> Unit = {}
    internal var onStartPlayer: () -> Unit = {}
    internal var onPausePlayerBar: () -> Unit = {}
    internal var onResumePlayerBar: () -> Unit = {}
    internal var onStartPlayerBar: () -> Unit = {}
    internal var onStopPlayer: () -> Unit = {}

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
        updateTimeSong()
        return START_REDELIVER_INTENT
    }

    fun onPauseMusic() {
        d("XXX", "on pause")
        if (audioPlayer.isPlaying) {
            audioPlayer.pause()
            onPausePlayer.invoke()
            onPausePlayerBar.invoke()
        }
    }

    fun onResumeMusic() {
        d("XXX", "on resume")
        if (!audioPlayer.isPlaying) {
            audioPlayer.start()
            onResumePlayer.invoke()
            onResumePlayerBar.invoke()
        }
    }

    fun onStartMusic(position: Int) {
        songPosition = position
        val song = songList[songPosition]
        songPlaying = Song().getData(this, song)
        audioPlayer.stop()
        audioPlayer = MediaPlayer.create(this, Uri.parse(songPlaying?.contentUri))
        audioPlayer.setVolume(100f, 100f)
        audioPlayer.start()
        audioPlayer.setOnCompletionListener {
            d("XXX", "finish")
            if(!audioPlayer.isLooping){
                d("XXX", "finish")
                onNextRightMusic()
            }
        }
        onStartPlayer.invoke()
        onStartPlayerBar.invoke()
    }

    fun onNextRightMusic() {
        songPosition += 1
        onStartMusic(songPosition)
    }

    fun onNextLeftMusic() {
        songPosition -= 1
        onStartMusic(songPosition)
    }

    private fun updateTimeSong() {
        object : CountDownTimer(100, 50) {
            override fun onFinish() {
                currentTime = audioPlayer.currentPosition
                onUpdateCurrentPosition.invoke(currentTime)
                this.start()
            }

            override fun onTick(p0: Long) {}
        }.start()
    }

    override fun onDestroy() {}

    override fun onLowMemory() {}
}
