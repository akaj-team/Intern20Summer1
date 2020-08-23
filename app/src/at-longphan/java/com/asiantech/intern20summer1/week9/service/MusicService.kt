package com.asiantech.intern20summer1.week9.service

import android.app.Service
import android.content.ContentUris
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.provider.MediaStore
import android.util.Log
import com.asiantech.intern20summer1.week9.model.Song


class MusicService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
    MediaPlayer.OnCompletionListener {

    //media player
    private var player: MediaPlayer? = null

    //song list
    private var songs: ArrayList<Song>? = null

    //current position
    private var songPosn = 0

    override fun onBind(intent: Intent?): IBinder? {
        return MusicBinder().musicBind
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onCompletion(mp: MediaPlayer?) {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        songPosn = 0
        player = MediaPlayer()
        initMusicPlayer()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        player?.stop()
        player?.release()
        return false
    }

    private fun initMusicPlayer() {
        player?.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            player?.setAudioAttributes(
                AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
            )
        } else {
            player?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        }

        player?.setOnPreparedListener(this)
        player?.setOnErrorListener(this)
        player?.setOnCompletionListener(this)
    }

    internal fun setList(songs: ArrayList<Song>) {
        this.songs = songs
    }

    fun setSong(songIndex: Int) {
        songPosn = songIndex
    }

    fun playSong() {
        player?.reset()

        //get song
        val playSong = songs?.get(songPosn)
        //get id
        val currSong: Long? = playSong?.id?.toLong()
        //set uri
        val trackUri: Uri? = currSong?.let {
            ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                it
            )
        }

        try {
            trackUri?.let{
                player?.setDataSource(applicationContext, it)
            }
        } catch (e: Exception) {
            Log.e("MUSIC SERVICE", "Error setting data source", e)
        }

        player?.prepareAsync()
    }

    private fun getPosn(): Int {
        return player!!.currentPosition
    }

    private fun getDur(): Int {
        return player!!.duration
    }

    private fun isPng(): Boolean {
        return player!!.isPlaying
    }

    private fun pausePlayer() {
        player!!.pause()
    }

    private fun seek(posn: Int) {
        player!!.seekTo(posn)
    }

    private fun go() {
        player!!.start()
    }

    class MusicBinder : Binder() {

        internal val musicBind: IBinder = MusicBinder()

        internal fun getService(): MusicService {
            return MusicService()
        }
    }
}
