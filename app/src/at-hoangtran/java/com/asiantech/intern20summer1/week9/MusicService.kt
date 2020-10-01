package com.asiantech.intern20summer1.week9

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import com.asiantech.intern20summer1.week9.CreateNotification.Companion.ACTION_KILL_MEDIA
import com.asiantech.intern20summer1.week9.CreateNotification.Companion.ACTION_NEXT
import com.asiantech.intern20summer1.week9.CreateNotification.Companion.ACTION_PAUSE
import com.asiantech.intern20summer1.week9.CreateNotification.Companion.ACTION_PREVIOUS

class MusicService : Service(), MediaPlayer.OnCompletionListener {
    companion object {
        internal const val SONG_LIST_KEY = "list"
        internal const val SONG_POSITION_KEY = "position"
    }

    private var isShuffle = false
    private var isRepeat = false
    private var currentPosition = 0
    private var songList = arrayListOf<Song>()
    private var mediaPlayer: MediaPlayer? = MediaPlayer()
    private val binder = LocalBinder()
    private var createNotification: CreateNotification? = null


    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (isRepeat) {
            playMedia(currentPosition)
        } else {
            nextSong()
        }
        createNotification(currentPosition)
    }


    inner class LocalBinder() : Binder() {
        internal val getServerInstance: MusicService
            get() = this@MusicService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.apply {
            currentPosition = intent.getIntExtra(SONG_POSITION_KEY, 0)
            songList = getParcelableArrayListExtra<Song>(SONG_LIST_KEY) as ArrayList<Song>
        }
        playMedia(currentPosition)
        addAction()
        createNotification(currentPosition)
        return START_NOT_STICKY
    }

    private fun createNotification(position: Int) {
        createNotification = CreateNotification(this)
        val notification = createNotification?.createNotification(songList[position], isPlaying())
        this.startForeground(1, notification)
    }

    private fun playMedia(position: Int) {
        if (mediaPlayer == null) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setOnCompletionListener(this)
        mediaPlayer?.setDataSource(applicationContext, Uri.parse(songList[position].path))
        mediaPlayer?.prepare()
        mediaPlayer?.setOnPreparedListener {
            mediaPlayer?.start()
        }
    }

    fun nextSong() {
        if (currentPosition == songList.size - 1) {
            currentPosition = 0
        } else {
            currentPosition++
        }
        playMedia(currentPosition)
    }

    fun previousSong() {
        if (currentPosition == 0) {
            currentPosition == songList.size - 1
        } else {
            currentPosition--
        }
        playMedia(currentPosition)
    }

    fun playPause() {
        if (mediaPlayer?.isPlaying!!) {
            mediaPlayer?.pause()
        } else {
            mediaPlayer?.start()
        }
    }

    fun seekTo(seek: Int) {
        mediaPlayer?.seekTo(seek)
    }

    fun currentPosition() = currentPosition

    fun isRepeat() = isRepeat

    fun isShuffle() = isShuffle

    fun isPlaying() = mediaPlayer?.isPlaying

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                ACTION_PREVIOUS -> {
                    previousSong()
                    createNotification(currentPosition)
                }
                ACTION_NEXT -> {
                    nextSong()
                    createNotification(currentPosition)
                }
                ACTION_PAUSE -> {
                    playPause()
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
        val filter = IntentFilter().apply {
            addAction(ACTION_PAUSE)
            addAction(ACTION_PREVIOUS)
            addAction(ACTION_NEXT)
            addAction(ACTION_KILL_MEDIA)
        }
        registerReceiver(broadcastReceiver, filter)
    }
}