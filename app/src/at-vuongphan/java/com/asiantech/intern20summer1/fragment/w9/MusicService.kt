package com.asiantech.intern20summer1.fragment.w9

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.data.w9.Music
import com.asiantech.intern20summer1.data.w9.MusicAction
import com.asiantech.intern20summer1.data.w9.MusicData

class MusicService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
    MediaPlayer.OnCompletionListener {
    private val filter = IntentFilter()
    private var binder = LocalBinder()
    private var positionSong: Int = 0
    private var isPlaying = false
    private var mediaPlayer: MediaPlayer? = null
    private val music = mutableListOf<Music>()

    companion object {
        const val CHANNEL_ID = "Music kotlin"
        private const val DEFAULT_VALUE_POSITION = 0
        fun startService(context: Context, message: Int) {
            val startIntent = Intent(
                context, MusicService
                ::class.java
            )
            startIntent.putExtra("put", message)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            context.stopService(Intent(context, MusicService::class.java))
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val position = intent.getIntExtra("put", DEFAULT_VALUE_POSITION)
        if (position != DEFAULT_VALUE_POSITION) {
            positionSong = position
            playMusic()
            isPlaying = true
        }
        when (intent.action) {
            MusicAction.NEXT -> {
                playNext()
            }
            MusicAction.PAUSE -> {
                isPlaying = false
                pauseMusic()
            }
            MusicAction.PREVIOUS -> {
                playPrevious()
            }
            MusicAction.SHUFFLE -> {
                shuffleMusic()
            }
            MusicAction.LOOP -> {
                loopMusic()
            }
            MusicAction.PLAY -> {
                playSong()
                isPlaying = true
            }
            MusicAction.CLOSE -> {
                Companion.stopService(MusicService())
            }
        }
        createNotificationChannel()
        return START_REDELIVER_INTENT
    }

    override fun onCreate() {
        super.onCreate()
        music.addAll(MusicData.getMusic(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        unregisterReceiver(BroadcastReceiver())
        Toast.makeText(this, getString(R.string.toast_destroy_services), Toast.LENGTH_LONG).show()
    }

    override fun onPrepared(p0: MediaPlayer?) {
    }

    override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        return false
    }

    override fun onCompletion(p0: MediaPlayer?) {
    }

    internal fun getPosition() = positionSong

    internal fun seekTo(currentDuration: Int) {
        mediaPlayer?.seekTo(currentDuration)
    }

    internal fun getCurrentDuration() = mediaPlayer?.currentPosition
    internal fun getTime() = mediaPlayer?.duration

    internal fun isPlaying() = isPlaying

    private fun initMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setOnCompletionListener {
            playNext()
        }
    }

    private fun createAction(action: String): PendingIntent? {
        val intent = Intent(this, MusicService::class.java)
        intent.action = action
        return PendingIntent.getService(this, 0, intent, 0)
    }

    private fun createIntentFilter() {
        filter.apply {
            addAction(MusicAction.PREVIOUS)
            addAction(MusicAction.PAUSE)
            addAction(MusicAction.NEXT)
            addAction(MusicAction.PLAY)
            addAction(MusicAction.CLOSE)
        }
        registerReceiver(BroadcastReceiver(), filter)
    }

    private fun playMusic() {
        initMediaPlayer()
        if (positionSong > music.size - 1 || positionSong < 0) {
            positionSong = 0
            mediaPlayer?.setDataSource(this, music[positionSong].uri)
            mediaPlayer?.prepare()
            mediaPlayer?.setOnPreparedListener {
                mediaPlayer?.start()
            }
        } else {
            mediaPlayer?.setDataSource(this, music[positionSong].uri)
            mediaPlayer?.prepare()
            mediaPlayer?.setOnPreparedListener {
                mediaPlayer?.start()
            }
        }
    }

    private fun playPrevious() {
        if (positionSong < 0) {
            positionSong = 0
            playMusic()
        } else {
            positionSong--
            playMusic()
        }
    }

    private fun shuffleMusic() {
        music.shuffle()
    }

    private fun playNext() {
        if (positionSong > music.size - 1) {
            positionSong = 0
            stopMusic()
        } else {
            positionSong++
            playMusic()
        }
    }

    private fun stopMusic() {
        mediaPlayer?.stop()
    }

    private fun loopMusic() {
        mediaPlayer?.isLooping = true
    }

    private fun pauseMusic() {
        mediaPlayer?.pause()
    }

    private fun playSong() {
        mediaPlayer?.start()
    }

    private fun createNotificationChannel() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(music[positionSong].name)
            .setContentText(music[positionSong].artist)
            .setSmallIcon(R.drawable.ic_music)
            .setLargeIcon(MusicData.convertUriToBitmap(music[positionSong].uri, this))
            .addAction(
                R.drawable.ic_skip_previous_red,
                null,
                createAction(MusicAction.PREVIOUS)
            )
            .addAction(
                R.drawable.ic_pause_circle_outline_red,
                null,
                createAction(MusicAction.PAUSE)
            )
            .addAction(R.drawable.ic_skip_next_red, null, createAction(MusicAction.NEXT))
            .addAction(R.drawable.ic_close_black_24dp, null, createAction(MusicAction.CLOSE))
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(1, 2, 3, 4)
            )
            .build()
        startForeground(1, notification)
        createIntentFilter()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, getString(R.string.channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }

    inner class LocalBinder : Binder() {
        internal val getService: MusicService
            get() = this@MusicService
    }
}
