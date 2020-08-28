package com.asiantech.intern20summer1.week9

import android.app.Notification
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.asiantech.intern20summer1.week9.MusicNotification.Companion.KILL
import com.asiantech.intern20summer1.week9.MusicNotification.Companion.NEXT
import com.asiantech.intern20summer1.week9.MusicNotification.Companion.PAUSE
import com.asiantech.intern20summer1.week9.MusicNotification.Companion.PREVIOUS

class MusicService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    companion object {
        private const val LIST_KEY = "list"
        private const val POSITION_KEY = "position"
        private const val DEFAULT_POSITION_KEY = 0
    }

    private lateinit var songList: ArrayList<Song>
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var songBinder: SongBinder
    private var notification: MusicNotification? = null
    private var position = 0
    private var isPlaying = false

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        )
        mediaPlayer.setOnPreparedListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate() {
        super.onCreate()
        songBinder = SongBinder()
        initMediaPlayer()
        startForeground(position, Notification())
    }

    override fun onBind(intent: Intent?): IBinder? {
        return songBinder
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        intent?.apply {
            songList = getParcelableArrayListExtra<Song>(LIST_KEY) as ArrayList<Song>
            position = getIntExtra(POSITION_KEY, DEFAULT_POSITION_KEY)
        }
        playSong()
        createNotification(position)
        addAction()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        stopForeground(true)
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mediaPlayer.start()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCompletion(mp: MediaPlayer?) {
        nextSong()
        createNotification(position)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun playSong() {
        mediaPlayer.stop()
        mediaPlayer.release()
        mediaPlayer = MediaPlayer()
        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.setDataSource(applicationContext, Uri.parse(songList[position].path))
        mediaPlayer.prepare()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun nextSong() {
        position++
        if (position >= songList.size) {
            position = DEFAULT_POSITION_KEY
        }
        playSong()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun previousSong() {
        position--
        if (position < DEFAULT_POSITION_KEY) {
            position = songList.size - 1
        }
        playSong()
    }

    fun isPlaying() = mediaPlayer.isPlaying

    fun getPosition() = position

    fun pause() = mediaPlayer.pause()

    fun start() = mediaPlayer.start()

    fun seekTo(progress: Int) = mediaPlayer.seekTo(progress)

    inner class SongBinder : Binder() {
        fun getService(): MusicService {
            return this@MusicService
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createNotification(position: Int) {
        notification = MusicNotification(this)
        val notification = notification?.createNotification(songList[position], isPlaying())
        this.startForeground(1, notification)
        isPlaying = this.isPlaying()
    }

    private fun broadcastReceiver() = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                PREVIOUS -> {
                    previousSong()
                    createNotification(position)
                }
                PAUSE -> {
                    pause()
                    createNotification(position)
                }
                NEXT -> {
                    nextSong()
                    createNotification(position)
                }
                KILL -> {
                    mediaPlayer.stop()
                    mediaPlayer.release()
                    stopForeground(true)
                }
            }
        }
    }

    private fun addAction(){
        val filter = IntentFilter()
        filter.apply {
            addAction(PAUSE)
            addAction(PREVIOUS)
            addAction(NEXT)
            addAction(KILL)
        }
        registerReceiver(broadcastReceiver(),filter)
    }
}
