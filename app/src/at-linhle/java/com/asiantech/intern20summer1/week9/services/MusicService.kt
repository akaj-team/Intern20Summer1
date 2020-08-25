package com.asiantech.intern20summer1.week9.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.asiantech.intern20summer1.week9.models.Song

class MusicService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    companion object {
        private const val LIST_SONG_KEY = "list"
        private const val SONG_POSITION_KEY = "position"
        private const val DEFAULT_SONG_POSITION = 0

        fun newInstance(context: Context, songList: ArrayList<Song>, currentPosition: Int): Intent {
            val musicServiceIntent = Intent(context, MusicService::class.java)
            musicServiceIntent.putParcelableArrayListExtra(LIST_SONG_KEY, songList)
            musicServiceIntent.putExtra(SONG_POSITION_KEY, currentPosition)
            return musicServiceIntent
        }
    }

    private lateinit var songList: ArrayList<Song>
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var songBinder: SongBinder
    private var loopType = 0
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
    }

    override fun onBind(p0: Intent?): IBinder? {
        return songBinder
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.apply {
            songList = getParcelableArrayListExtra<Song>(LIST_SONG_KEY) as ArrayList<Song>
            position = getIntExtra(SONG_POSITION_KEY, DEFAULT_SONG_POSITION)
        }
        playSong()
        return START_NOT_STICKY
    }

    override fun onPrepared(p0: MediaPlayer?) {
        mediaPlayer.start()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCompletion(p0: MediaPlayer?) {
        when (loopType) {
            0 -> nextSong()
            1 -> playSong()
        }
    }

    override fun onDestroy() {
        mediaPlayer.release()
        super.onDestroy()
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
            position = DEFAULT_SONG_POSITION
        }
        playSong()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun previousSong() {
        position--
        if (position < 0) {
            position = (songList.size) - 1
        }
        playSong()
    }

    fun getDuration(): Int {
        return mediaPlayer.duration
    }

    fun getCurrentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    fun getPosition(): Int{
        return position
    }
    fun pause() {
        mediaPlayer.pause()
    }

    fun start() {
        mediaPlayer.start()
    }

    fun seekTo(progress: Int) {
        mediaPlayer.seekTo(progress)
    }

    inner class SongBinder : Binder() {
        fun getService(): MusicService {
            return this@MusicService
        }
    }
}
