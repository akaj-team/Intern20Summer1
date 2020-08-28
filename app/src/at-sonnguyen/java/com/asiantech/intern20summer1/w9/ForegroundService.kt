package com.asiantech.intern20summer1.w9

import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.core.content.ContextCompat
import com.asiantech.intern20summer1.w9.data.MusicAction
import com.asiantech.intern20summer1.w9.data.Song
import com.asiantech.intern20summer1.w9.data.SongData
import com.asiantech.intern20summer1.w9.notification.CreateNotification

class ForegroundService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
    MediaPlayer.OnCompletionListener {

    private var binder = LocalBinder()
    private var positionSong = 0
    private var isShuffle = false
    private var isLooping = false
    private var notification: CreateNotification? = null
    private val songs = mutableListOf<Song>()
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private var filter = IntentFilter()

    companion object {
        internal const val CHANNEL_ID = "Foreground Service"
        private const val DEFAULT_POSITION_VALUE = 0
        private const val POSITION_KEY = "position Key"
        fun startService(context: Context, message: Int) {
            val startIntent = Intent(context, ForegroundService::class.java)
            startIntent.putExtra(POSITION_KEY, message)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, ForegroundService::class.java)
            context.stopService(stopIntent)
        }
    }

    inner class LocalBinder : Binder() {
        internal val getService: ForegroundService
            get() = this@ForegroundService
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
//        val position = intent.getIntExtra(POSITION_KEY, DEFAULT_POSITION_VALUE)
//        if (position != DEFAULT_POSITION_VALUE) {
//            positionSong = position
//            playMusic()
//            isPlaying = true
//        }
//        when (intent.action) {
//            MusicAction.PLAY -> {
//                isPlaying = true
//                playSong()
//            }
//
//            MusicAction.PREVIOUS -> {
//                playPrevious()
//            }
//            MusicAction.PAUSE -> {
//                isPlaying = false
//                pauseMusic()
//            }
//            MusicAction.NEXT -> {
//                playNext()
//            }
//            MusicAction.SHUFFLE -> {
//                shuffleMusic()
//            }
//            MusicAction.LOOP -> {
//                loopMusic()
//            }
//            MusicAction.CLOSE -> {
//                Companion.stopService(ForegroundService())
//            }
//        }
//        createNotificationMusicChannel()
//        return START_REDELIVER_INTENT
        intent.apply { positionSong = intent.getIntExtra(POSITION_KEY, 0) }
        createNotification(positionSong)
        initMediaPlayer(positionSong)
        createIntentFilter()
        return START_NOT_STICKY
    }

    private fun createNotification(position: Int) {
        notification = CreateNotification(this)
        val notification = notification?.createNotification(songs[position], isPlaying)
        this.startForeground(1, notification)
        isPlaying = this.isPlaying()
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                MusicAction.PREVIOUS -> {
                    playPrevious()
                    createNotification(positionSong)
                }
                MusicAction.PAUSE -> {
                    onPauseOrPlay()
                    createNotification(positionSong)
                }
                MusicAction.NEXT -> {
                    playNext()
                    createNotification(positionSong)
                }
                MusicAction.CLOSE -> {
                    createNotification(positionSong)
                }
            }
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    fun onPauseOrPlay() {
        if (mediaPlayer?.isPlaying!!) {
            mediaPlayer?.pause()
        } else {
            mediaPlayer?.start()
        }
    }

    private fun createAction(action: String): PendingIntent? {
        val intent = Intent(this, ForegroundService::class.java)
        intent.action = action
        return PendingIntent.getService(this, 0, intent, 0)
    }

    override fun onCreate() {
        super.onCreate()
        songs.addAll(SongData.getSong(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
    }

    private fun createIntentFilter() {
        filter.apply {
            addAction(MusicAction.PREVIOUS)
            addAction(MusicAction.PAUSE)
            addAction(MusicAction.NEXT)
            addAction(MusicAction.PLAY)
            addAction(MusicAction.CLOSE)
        }
        registerReceiver(MusicReceiver(), filter)
    }

    internal fun playMusic() {
        initMediaPlayer(positionSong)
        mediaPlayer?.setDataSource(this, songs[positionSong].uri)
        mediaPlayer?.prepare()
        mediaPlayer?.setOnPreparedListener {
            mediaPlayer?.start()
        }
    }

    internal fun initMediaPlayer(position: Int) {
        if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setOnCompletionListener(this)
        mediaPlayer?.setDataSource(applicationContext, songs[position].uri)
        mediaPlayer?.prepare()
        mediaPlayer?.setOnPreparedListener {
            mediaPlayer?.start()
        }
    }

    internal fun playNext() {
        positionSong++
        if (positionSong >= songs.size - 1) {
            positionSong = 0
        }
        initMediaPlayer(positionSong)
    }

    internal fun playPrevious() {
        positionSong--
        if (positionSong < 0) {
            positionSong = songs.size - 1
        }
        initMediaPlayer(positionSong)
    }

    internal fun isLoop() = isLooping

    internal fun isShuffle() = isShuffle

    internal fun shuffleSong() {
        if (isShuffle) {
            songs.clear()
            songs.addAll(SongData.getSong(this))
            isShuffle = false
        } else {
            songs.shuffle()
        }
    }

    internal fun isPlaying(): Boolean {
        return isPlaying
    }

    internal fun loopMusic() {
        mediaPlayer?.isLooping = true
    }

    internal fun pauseMusic() {
        mediaPlayer?.pause()
    }

    private fun playSong() {
        mediaPlayer?.start()
    }

    internal fun getPosition(): Int {
        return positionSong
    }

    internal fun seekTo(currentDuration: Int) {
        mediaPlayer?.seekTo(currentDuration)
    }

    internal fun getCurrentDuration(): Int? {
        return mediaPlayer?.currentPosition
    }

    override fun onPrepared(p0: MediaPlayer?) {
    }

    override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        return false
    }

    override fun onCompletion(mediaPlayer: MediaPlayer?) {
        if (!isLooping) {
            playNext()
        } else {
            playSong()
        }
    }
}
