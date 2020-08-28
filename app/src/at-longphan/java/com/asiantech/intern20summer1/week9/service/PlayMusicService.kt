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

    companion object {
        var isShuffle = false
        var isRepeat = false
        var currentPos = 0
    }

    private var musicDataList: ArrayList<Song> = ArrayList()
    private var mMediaPlayer: MediaPlayer? = MediaPlayer()
    private val mBinder = LocalBinder()
    private var isPlaying = false
    private var notification: Notification? = null
    private var rand: Random = Random

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                Units.ACTION_PREVIOUS -> {
                    playPrev()
                    createNotification(currentPos)
                }
                Units.ACTION_PLAY_PAUSE -> {
                    togglePlayAndPause()
                    createNotification(currentPos)
                }
                Units.ACTION_SKIP_NEXT -> {
                    playNext()
                    createNotification(currentPos)
                }
                Units.ACTION_KILL_MEDIA -> {
                    mMediaPlayer?.stop()
                    mMediaPlayer?.release()
                    stopForeground(true)
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (!isRepeat) {
            playNext()
        } else playMedia(currentPos)
        createNotification(currentPos)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.apply {
            currentPos = intent.getIntExtra(SongAdapter.SONG_ITEM_POSITION, 0)
            musicDataList = intent.getParcelableArrayListExtra(SongAdapter.SONG_LIST_PATH)
        }
        createNotification(currentPos)
        playMedia(currentPos)
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

    internal fun playNext() {
        if(isShuffle){
            var newPos = currentPos
            while (newPos == currentPos){
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
    }

    internal fun seekTo(current: Int) {
        mMediaPlayer?.seekTo(current)
    }

    internal fun currentPosition() = mMediaPlayer?.currentPosition

    internal fun initPosition(): Int = currentPos

    internal fun isPlaying(): Boolean = mMediaPlayer?.isPlaying!!

    internal fun isRepeat(): Boolean = isRepeat

    internal fun isShuffle(): Boolean = isShuffle

    inner class LocalBinder : Binder() {
        internal val getServerInstance: PlayMusicService
            get() = this@PlayMusicService
    }

    private fun createNotification(position: Int) {
        notification = Notification(this)
        val notification =
            notification?.createPlayMusicNotification(musicDataList[position], isPlaying)
        this.startForeground(1, notification)
        isPlaying = this.isPlaying()
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
