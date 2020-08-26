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

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION")
class PlayMusicService : Service(), MediaPlayer.OnCompletionListener {
    override fun onCompletion(mp: MediaPlayer?) {
        if (!isReNew) {
            initNextMusic()
        } else playMedia(currentPosition)
        createNotification(currentPosition)
    }

    companion object {
        var isShuffle = false
        var isReNew = false
    }

    private var currentPosition = 0
    private var musicDataList: ArrayList<Song> = ArrayList()
    private var mMediaPlayer: MediaPlayer? = MediaPlayer()
    private val mBinder = LocalBinder()
    private var isPlaying = false
    private var notification: Notification? = null

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.apply {
            currentPosition = intent.getIntExtra(SongAdapter.SONG_ITEM_POSITION, 0)
            musicDataList = intent.getParcelableArrayListExtra(SongAdapter.SONG_LIST_PATH)
        }
        createNotification(currentPosition)
        playMedia(currentPosition)
        addAction()
        return START_NOT_STICKY
    }

    private fun playMedia(position: Int) {
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
        if (mMediaPlayer?.isPlaying!!) {
            mMediaPlayer?.pause()
        } else {
            mMediaPlayer?.start()
        }
    }

    fun seekTo(current: Int) {
        mMediaPlayer?.seekTo(current)
    }

    fun currentPosition() = mMediaPlayer?.currentPosition

    fun initPosition(): Int = currentPosition

    fun isPlaying(): Boolean = mMediaPlayer?.isPlaying!!

    fun isRenew(): Boolean = isReNew

    fun isShulle(): Boolean = isShuffle

    inner class LocalBinder : Binder() {
        internal val getServerInstance: PlayMusicService
            get() = this@PlayMusicService
    }

    private fun createNotification(position: Int) {
        notification = Notification(this)
        val notification = notification?.createNotification(musicDataList[position], isPlaying)
        this.startForeground(1, notification)
        isPlaying = this.isPlaying()
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                Units.ACTION_PREVIOUS -> {
                    initPreviousMusic()
                    createNotification(currentPosition)
                }
                Units.ACTION_PLAY_PAUSE -> {
                    initPlayPause()
                    createNotification(currentPosition)
                }
                Units.ACTION_SKIP_NEXT -> {
                    initNextMusic()
                    createNotification(currentPosition)
                }
                Units.ACTION_KILL_MEDIA -> {
                    mMediaPlayer?.stop()
                    mMediaPlayer?.release()
                    stopForeground(true)
                }
            }
        }
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