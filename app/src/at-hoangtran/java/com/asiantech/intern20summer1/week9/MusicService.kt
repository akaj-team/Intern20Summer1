package com.asiantech.intern20summer1.week9

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi

class MusicService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    companion object {
        private const val LIST_KEY = "list"
        private const val POSITION_KEY = "position"
        private const val DEFAULT_POSITION_KEY = 0

        fun newInstance(context: Context, songList: ArrayList<Song>): Intent {
            val musicServiceIntent = Intent(context, MusicService::class.java)
            musicServiceIntent.putParcelableArrayListExtra(LIST_KEY, songList)
            return musicServiceIntent
        }
    }

    private lateinit var songList: ArrayList<Song>
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var songBinder: SongBinder
    private var loopType = 0
    private var notification: Notification? = null
    private var position = 0

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
        startForeground(position, notification)
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
        when (loopType) {
            0 -> nextSong()
            1 -> playSong()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun playSong() {
        mediaPlayer.stop()
        mediaPlayer.release()
        initMediaPlayer()
        val song = songList[position]
        mediaPlayer.setDataSource(applicationContext, Uri.parse(song.path))
        mediaPlayer.prepareAsync()
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

    fun getCurrentPosition() {
        mediaPlayer.currentPosition
    }

    fun getPosition() = position

    fun pause() = mediaPlayer.pause()

    fun start() = mediaPlayer.start()

    fun seekTo(progress: Int) = mediaPlayer.seekTo(progress)

    inner class SongBinder : Binder() {
        fun getService(): MusicService {
            return this@MusicService
        }
    }
}
