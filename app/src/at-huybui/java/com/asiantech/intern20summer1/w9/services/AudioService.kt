package com.asiantech.intern20summer1.w9.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log.d
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.models.Song
import com.asiantech.intern20summer1.w9.notification.ClickPlayable
import com.asiantech.intern20summer1.w9.notification.CreatePlayerNotification
import com.asiantech.intern20summer1.w9.notification.NotificationBroadcastReceiver

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is service class to play mp3
 */

class AudioService : Service(), ClickPlayable {

    var audioPlayer = MediaPlayer()
    var songLists = mutableListOf<Song>()
    var songPlaying: Song? = null
    var currentTime = 0
    private var iBinder: IBinder = LocalBinder()
    var songPosition = 0
    var isRandom = false
    var isOpenApp = true

    private lateinit var notification: NotificationManager
    internal var onUpdateCurrentPosition: (Int) -> Unit = {}
    internal var onPlayer: (StatePlayer) -> Unit = {}
    internal var onPlayerBar: (StatePlayer) -> Unit = {}
    internal var onShuffleSong: () -> Unit = {}
    internal val timerUpdateCurrent = object : CountDownTimer(500, 500) {
        override fun onFinish() {
            d("servicelog", "[curent] " + audioPlayer.currentPosition.toString())
            if (isOpenApp) {
                currentTime = audioPlayer.currentPosition
                onUpdateCurrentPosition.invoke(currentTime)
            }
            this.start()
        }

        override fun onTick(p0: Long) {}
    }

    enum class StatePlayer { START, PAUSE, RESUME }

    inner class LocalBinder : Binder() {
        fun getService(): AudioService {
            return this@AudioService
        }
    }

    override fun onBind(arg0: Intent?): IBinder? {
        return iBinder
    }

    override fun onCreate() {
        d("servicelog", "onCreate")
        super.onCreate()
        timerUpdateCurrent.start()
        registerReceiver(broadcastReceiver, IntentFilter(NotificationBroadcastReceiver.ACTION_KEY))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        d("servicelog", "onStartCommand")
        createChannel()
        return START_NOT_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        timerUpdateCurrent.cancel()
        d("servicelog", "onDestroy")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.cancelAll()
        }
        audioPlayer.release()
        unregisterReceiver(broadcastReceiver)
        stopSelf()

    }

    fun onShuffleMusic(songList: MutableList<Song>) {
        if (isRandom) {
            songLists.shuffle()
        } else {
            songLists.clear()
            songList.toCollection(songLists)
        }
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CreatePlayerNotification.CHANNEL_ID,
                CreatePlayerNotification.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            notification = getSystemService(NotificationManager::class.java)
            notification.createNotificationChannel(channel)
        }
    }

    private var broadcastReceiver = object : NotificationBroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.extras?.getString(ACTION_NAME)) {
                CreatePlayerNotification.ACTION_PREVIOUS -> {
                    onMusicPrevious()
                }
                CreatePlayerNotification.ACTION_PLAY -> {
                    if (audioPlayer.isPlaying) {
                        onMusicPause()
                    } else {
                        onMusicResume()
                    }
                }
                CreatePlayerNotification.ACTION_NEXT -> {
                    onMusicNext()
                }
            }
        }

    }

    override fun onMusicStart() {
        CreatePlayerNotification().createNotification(
            this,
            songLists[songPosition],
            R.drawable.ic_pause_notification
        )
        songPlaying = songLists[songPosition]
        audioPlayer.release()
        audioPlayer = MediaPlayer.create(this, Uri.parse(songPlaying?.contentUri))
        audioPlayer.apply {
            setVolume(100f, 100f)
            start()
            setOnCompletionListener {
                if (!audioPlayer.isLooping) {
                    onMusicNext()
                }
            }
        }
        if (isOpenApp) {
            onPlayer.invoke(StatePlayer.START)
            onPlayerBar.invoke(StatePlayer.START)
        }
    }

    override fun onMusicPrevious() {
        CreatePlayerNotification().createNotification(
            this,
            songLists[songPosition],
            R.drawable.ic_previous_notification
        )
        if (songPosition > 0) {
            songPosition -= 1
        } else {
            songPosition = songLists.size - 1
        }
        onMusicStart()
    }

    override fun onMusicResume() {
        CreatePlayerNotification().createNotification(
            this,
            songLists[songPosition],
            R.drawable.ic_pause_notification
        )
        if (!audioPlayer.isPlaying) {
            audioPlayer.start()
            if (isOpenApp) {
                onPlayer.invoke(StatePlayer.RESUME)
                onPlayerBar.invoke(StatePlayer.RESUME)
            }
        }
    }

    override fun onMusicPause() {
        CreatePlayerNotification().createNotification(
            this,
            songLists[songPosition],
            R.drawable.ic_play_notification
        )
        if (audioPlayer.isPlaying) {
            audioPlayer.pause()
            if (isOpenApp) {
                onPlayer.invoke(StatePlayer.PAUSE)
                onPlayerBar.invoke(StatePlayer.PAUSE)
            }
        }
    }

    override fun onMusicNext() {
        CreatePlayerNotification().createNotification(
            this,
            songLists[songPosition],
            R.drawable.ic_next_notification
        )
        if (songPosition < songLists.size - 1) {
            songPosition += 1
        } else {
            songPosition = 0
        }
        onMusicStart()
    }
}
