package com.asiantech.intern20summer1.week9.services

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import com.asiantech.intern20summer1.week9.extensions.CreateNotification
import com.asiantech.intern20summer1.week9.extensions.CreateNotification.Companion.ACTION_KILL_MEDIA
import com.asiantech.intern20summer1.week9.extensions.CreateNotification.Companion.ACTION_PAUSE
import com.asiantech.intern20summer1.week9.extensions.CreateNotification.Companion.ACTION_PREVIOUS
import com.asiantech.intern20summer1.week9.extensions.CreateNotification.Companion.ACTION_SKIP_NEXT
import com.asiantech.intern20summer1.week9.models.Song

class MusicService : Service(), MediaPlayer.OnCompletionListener {
    companion object {
        internal const val LIST_SONG_KEY = "list"
        internal const val SONG_POSITION_KEY = "position"
        var isShuffle = false
        var isRePeat = false
    }

    private var currentPosition = 0
    private var musicDataList: ArrayList<Song> = ArrayList()
    private var mediaPlayer: MediaPlayer? = MediaPlayer()
    private val binder = LocalBinder()
    private var isPlaying = false
    private var notification: CreateNotification? = null

    override fun onCompletion(mp: MediaPlayer?) {
        if (!isRePeat) {
            initNextMusic()
        } else playMedia(currentPosition)
        createNotification(currentPosition)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.apply {
            currentPosition = intent.getIntExtra(SONG_POSITION_KEY, 0)
            musicDataList =
                intent.getParcelableArrayListExtra<Song>(LIST_SONG_KEY) as ArrayList<Song>
        }
        createNotification(currentPosition)
        playMedia(currentPosition)
        addAction()
        return START_NOT_STICKY
    }

    private fun playMedia(position: Int) {
        if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setOnCompletionListener(this)
        mediaPlayer?.setDataSource(applicationContext, Uri.parse(musicDataList[position].path))
        mediaPlayer?.prepare()
        mediaPlayer?.setOnPreparedListener {
            mediaPlayer?.start()
        }
    }

    fun initNextMusic() {
        currentPosition++
        if (currentPosition > musicDataList.size - 1) {
            currentPosition = 0
        }
        playMedia(currentPosition)
    }

    fun initPreviousMusic() {
        currentPosition--
        if (currentPosition < 0) {
            currentPosition = musicDataList.size - 1
        }
        playMedia(currentPosition)
    }

    fun initPlayPause() {
        if (mediaPlayer?.isPlaying!!) {
            mediaPlayer?.pause()
        } else {
            mediaPlayer?.start()
        }
    }

    fun seekTo(current: Int) {
        mediaPlayer?.seekTo(current)
    }

    fun currentPosition() = mediaPlayer?.currentPosition

    fun initPosition(): Int = currentPosition

    fun isPlaying(): Boolean = mediaPlayer?.isPlaying!!

    fun isRepeat(): Boolean = isRePeat

    fun isShuffle(): Boolean = isShuffle

    inner class LocalBinder : Binder() {
        internal val getServerInstance: MusicService
            get() = this@MusicService
    }

    private fun createNotification(position: Int) {
        notification = CreateNotification(this)
        val notification = notification?.createNotification(musicDataList[position], isPlaying)
        this.startForeground(1, notification)
        isPlaying = this.isPlaying()
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                ACTION_PREVIOUS -> {
                    initPreviousMusic()
                    createNotification(currentPosition)
                }
                ACTION_PAUSE -> {
                    initPlayPause()
                    createNotification(currentPosition)
                }
                ACTION_SKIP_NEXT -> {
                    initNextMusic()
                    createNotification(currentPosition)
                }
                ACTION_KILL_MEDIA -> {
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    stopForeground(true)
                }
            }
        }
    }

    private fun addAction() {
        val filter = IntentFilter()
        filter.apply {
            addAction(ACTION_PAUSE)
            addAction(ACTION_SKIP_NEXT)
            addAction(ACTION_PREVIOUS)
            addAction(ACTION_KILL_MEDIA)
        }
        registerReceiver(broadcastReceiver, filter)
    }
}
