package com.asiantech.intern20summer1.week9.services

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9.models.Song
import com.asiantech.intern20summer1.week9.views.MusicMediaActivity

class MusicService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
    MediaPlayer.OnCompletionListener {

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "ForegroundService"

        fun newInstance() = MusicService()
    }

    private var songList: MutableList<Song>? = null
    private var mediaPlayer: MediaPlayer? = null
    private var position = 0
    private val musicBind = MusicBinder()
    private var songName = ""
    private lateinit var uri: Uri

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        initMusicPlayer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return musicBind
    }

    override fun onUnbind(intent: Intent?): Boolean {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        return false
    }

    override fun onPrepared(p0: MediaPlayer?) {
        p0?.start()
        createNotification()
    }

    override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        p0?.reset()
        return false
    }

    override fun onCompletion(p0: MediaPlayer?) {
        if (mediaPlayer?.currentPosition!! > 0) {
            p0?.reset()
            playNext()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

    inner class MusicBinder : Binder() {
        internal val getService: MusicService
            get() = this@MusicService
    }

    fun playSong() {
        mediaPlayer?.reset()
        val songModel = songList?.get(position)
        songName = songModel?.songName.toString()

        uri = Uri.parse(songModel?.imgUri)

        try {
            uri.let { mediaPlayer?.setDataSource(applicationContext, it) }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
        mediaPlayer?.prepare()
        mediaPlayer?.start()
    }

    fun seekBar(position: Int) {
        mediaPlayer?.seekTo(position)
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun start() {
        mediaPlayer?.start()
    }

    fun playPrev() {
        position--
        if (position < 0) {
            position = (songList?.size ?: 0) - 1
        }
        playSong()
    }

    fun playNext() {
        position++
        if (position >= songList?.size!!) {
            position = 0
        }
        playSong()
    }

    fun getPosition(): Int {
        return position
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    fun getDuration(): Int? {
        return mediaPlayer?.duration
    }

    private fun initMusicPlayer() {
        mediaPlayer?.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
        mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer?.setOnPreparedListener(this)
        mediaPlayer?.setOnCompletionListener(this)
        mediaPlayer?.setOnErrorListener(this)
    }

    private fun createNotification() {
        val notificationIntent = Intent(this, MusicMediaActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notifications = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        notifications.apply {
            setContentTitle(getString(R.string.music_service_content_title))
            setContentText("")
            setSmallIcon(R.drawable.ic_baseline_play_circle_filled_24)
            addAction(R.drawable.ic_baseline_play_circle_filled_24, "", pendingIntent)
            setContentIntent(pendingIntent)
        }
        val builder = notifications.build()
        startForeground(1, builder)
    }
}
