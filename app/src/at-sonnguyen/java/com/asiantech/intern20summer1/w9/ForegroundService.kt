package com.asiantech.intern20summer1.w9

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
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.data.MusicAction
import com.asiantech.intern20summer1.w9.data.Song
import com.asiantech.intern20summer1.w9.data.SongData

class ForegroundService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
    MediaPlayer.OnCompletionListener {

    private var binder = LocalBinder()
    private var positionSong = 0
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
        val position = intent.getIntExtra(POSITION_KEY, DEFAULT_POSITION_VALUE)
        if (position != DEFAULT_POSITION_VALUE) {
            positionSong = position
            playMusic()
            isPlaying = true
        }
        when (intent.action) {
            MusicAction.PREVIOUS -> {
                playPrevious()
            }
            MusicAction.PAUSE -> {
                pauseMusic()
            }
            MusicAction.NEXT -> {
                playNext()
            }
            MusicAction.SHUFFLE -> {
                shuffleMusic()
            }
            MusicAction.LOOP -> {
                loopMusic()
            }
            MusicAction.CLOSE -> {
                Companion.stopService(ForegroundService())
            }
        }
        createNotificationMusicChannel()
        return START_REDELIVER_INTENT
    }

    private fun createNotificationMusicChannel() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(songs[positionSong].songName)
            .setContentText(songs[positionSong].singerName)
            .setSmallIcon(R.drawable.ic_music)
            .setLargeIcon(songs[positionSong].uri.let {
                SongData.convertUriToBitmap(
                    it,
                    this
                )
            })
            .addAction(
                R.drawable.ic_baseline_skip_previous_24,
                null,
                createAction(MusicAction.PREVIOUS)
            )
            .addAction(R.drawable.ic_baseline_pause_24, null, createAction(MusicAction.PAUSE))
            .addAction(R.drawable.ic_baseline_skip_next_24, null, createAction(MusicAction.NEXT))
            .addAction(R.drawable.ic_baseline_close_24, null, createAction(MusicAction.CLOSE))
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(1, 2, 3, 4)
            )
            .build()
        startForeground(1, notification)
        createIntentFilter()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, getString(R.string.w9_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
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

    private fun playMusic() {
        initMediaPlayer()
        mediaPlayer?.setDataSource(this, songs[positionSong].uri)
        mediaPlayer?.prepare()
        mediaPlayer?.setOnPreparedListener {
            mediaPlayer?.start()
        }
    }

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

    private fun playNext() {
        positionSong++
        playMusic()
    }

    private fun playPrevious() {
        positionSong--
        playMusic()
    }

    private fun shuffleMusic() {
        songs.shuffle()
    }

    internal fun isPlaying(): Boolean {
        return isPlaying
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

    override fun onCompletion(p0: MediaPlayer?) {
    }
}
