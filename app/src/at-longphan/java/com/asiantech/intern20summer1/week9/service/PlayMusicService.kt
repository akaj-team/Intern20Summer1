package com.asiantech.intern20summer1.week9.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.asiantech.intern20summer1.week9.Notification
import com.asiantech.intern20summer1.week9.adapter.SongAdapter
import com.asiantech.intern20summer1.week9.model.Song
import com.asiantech.intern20summer1.week9.model.Units
import kotlin.random.Random

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION")
class PlayMusicService : Service(), MediaPlayer.OnCompletionListener {

    internal var currentPos = 0
    internal var isShuffle = false
    internal var isRepeat = false
    internal var isPlaying = false
    private var musicDataList: ArrayList<Song> = ArrayList()
    private var mMediaPlayer: MediaPlayer? = MediaPlayer()
    private val mBinder = LocalBinder()
    private var notification: Notification? = null
    private var rand: Random = Random

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                Units.ACTION_PREVIOUS -> {
                    playPrev()
                    onMusicNotificationSelected()
                }
                Units.ACTION_PLAY_PAUSE -> {
                    togglePlayAndPause()
                    onMusicNotificationSelected()
                    onNotificationChange()
                }
                Units.ACTION_SKIP_NEXT -> {
                    playNext()
                    onMusicNotificationSelected()
                }
                Units.ACTION_KILL_MEDIA -> {
                    mMediaPlayer?.stop()
                    mMediaPlayer?.release()
                    stopForeground(true)
                }
            }
        }
    }

    internal var onMusicNotificationSelected: () -> Unit = {}
    internal var onNotificationChange: () -> Unit = {}

    override fun onCompletion(mp: MediaPlayer?) {
        if (!isRepeat) {
            playNext()
        } else playMedia(currentPos)
        onMusicNotificationSelected.invoke()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.apply {
            currentPos = intent.getIntExtra(SongAdapter.SONG_ITEM_POSITION, 0)
            musicDataList = intent.getParcelableArrayListExtra(SongAdapter.SONG_LIST_PATH)
        }
        playMedia(currentPos)
        addAction()
        return START_NOT_STICKY
    }

    private fun playMedia(position: Int) {
        isPlaying = true
        if (mMediaPlayer != null) {
            mMediaPlayer?.stop()
            mMediaPlayer?.release()
        }
        mMediaPlayer = MediaPlayer()
        mMediaPlayer?.setOnCompletionListener(this)
        mMediaPlayer?.setDataSource(musicDataList[position].path)
        mMediaPlayer?.prepare()
        mMediaPlayer?.setOnPreparedListener {
            mMediaPlayer?.start()
        }
        createNotification(currentPos)
    }

    internal fun playNext() {
        if (isShuffle) {
            var newPos = currentPos
            while (newPos == currentPos) {
                newPos = rand.nextInt(musicDataList.size)
            }
            currentPos = newPos
        } else {
            currentPos++
            if (currentPos > musicDataList.size - 1) {
                currentPos = 0
            }
        }
        playMedia(currentPos)
    }

    internal fun playPrev() {
        currentPos--
        if (currentPos < 0) {
            currentPos = musicDataList.size - 1
        }
        playMedia(currentPos)
    }

    internal fun togglePlayAndPause() {
        if (mMediaPlayer?.isPlaying!!) {
            mMediaPlayer?.pause()
        } else {
            mMediaPlayer?.start()
        }
        isPlaying = isMediaPlayerPlaying()
        createNotification(currentPos)
    }

    internal fun seekTo(current: Int) {
        mMediaPlayer?.seekTo(current)
    }

    internal fun currentPosition() = mMediaPlayer?.currentPosition

    internal fun isMediaPlayerPlaying(): Boolean = mMediaPlayer?.isPlaying!!

    inner class LocalBinder : Binder() {
        internal val getServerInstance: PlayMusicService
            get() = this@PlayMusicService
    }

    private fun createNotification(position: Int) {
        notification = Notification(this)
        val notification =
            notification?.createPlayMusicNotification(musicDataList[position], isPlaying)
        this.startForeground(1, notification)
    }

    private fun addAction() {
        val filter = IntentFilter()
        filter.apply {
            addAction(Units.ACTION_PLAY_PAUSE)
            addAction(Units.ACTION_SKIP_NEXT)
            addAction(Units.ACTION_PREVIOUS)
            addAction(Units.ACTION_KILL_MEDIA)
        }
        registerReceiver(broadcastReceiver, filter)
    }
}
