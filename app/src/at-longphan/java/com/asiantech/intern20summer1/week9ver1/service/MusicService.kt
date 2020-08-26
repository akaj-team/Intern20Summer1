package com.asiantech.intern20summer1.week9ver1.service

import android.app.Notification
import android.app.PendingIntent
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
import com.asiantech.intern20summer1.MainActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9ver1.model.Song
import kotlin.random.Random

class MusicService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
    MediaPlayer.OnCompletionListener {

    private var musicBind: IBinder = MusicBinder()
    //media player
    private lateinit var player: MediaPlayer
    //song list
    private lateinit var songs: ArrayList<Song>
    //current position
    private var songPosn = 0
    private var songTitle = ""
    private var shuffle = false
    private lateinit var rand: Random

    companion object {
        private const val NOTIFY_ID = 1
    }

    override fun onBind(intent: Intent?): IBinder? {
        return musicBind
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()

        val notIntent = Intent(this, MainActivity::class.java)
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendInt = PendingIntent.getActivity(
            this, 0,
            notIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder: Notification.Builder = Notification.Builder(this)

        builder.setContentIntent(pendInt)
            .setSmallIcon(R.drawable.ic_arrow_back_ios_20)
            .setTicker(songTitle)
            .setOngoing(true)
            .setContentTitle("Playing")
            .setContentText(songTitle)
        val not: Notification = builder.build()

        startForeground(NOTIFY_ID, not)
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        mp?.reset()
        return false
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (player.currentPosition == 0) {
            mp?.reset();
            playNext();
        }
    }

    override fun onCreate() {
        super.onCreate()
        songPosn = 0
        player = MediaPlayer()
        initMusicPlayer()
        rand = Random
    }

    override fun onUnbind(intent: Intent?): Boolean {
        player.stop()
        player.release()
        return false
    }

    override fun onDestroy() {
        stopForeground(true)
    }

    private fun initMusicPlayer() {
        player.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            player.setAudioAttributes(
                AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
            )
        } else {
            player.setAudioStreamType(AudioManager.STREAM_MUSIC)
        }

        player.setOnPreparedListener(this)
        player.setOnErrorListener(this)
        player.setOnCompletionListener(this)
    }

    internal fun setList(songs: ArrayList<Song>) {
        this.songs = songs
    }

    fun setSong(songIndex: Int) {
        songPosn = songIndex
    }

    fun playSong() {
        player.reset()

        //get song
        val playSong = songs[songPosn]
        songTitle = playSong.title
        //get id
        val currSong: Long? = playSong.id
        //set uri
        val trackUri: Uri? = currSong?.let {
            ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                it
            )
        }

        try {
            trackUri?.let {
                player.setDataSource(applicationContext, it)
            }
        } catch (e: Exception) {
            Log.e("MUSIC SERVICE", "Error setting data source", e)
        }

        player.prepareAsync()
    }

    internal fun playPrev() {
        songPosn--
        if (songPosn == 0) {
            songPosn = songs.size - 1
        }
        playSong()
    }

    internal fun playNext() {
        if (shuffle) {
            var newSong = songPosn
            while (newSong == songPosn) {
                newSong = rand.nextInt(songs.size)
            }
            songPosn = newSong
        } else {
            songPosn++
            if (songPosn == songs.size) {
                songPosn = 0
            }
        }

        playSong()
    }

    internal fun setShuffle() {
        shuffle = !shuffle
    }

    internal fun getPosn(): Int {
        return player.currentPosition
    }

    internal fun getDur(): Int {
        return player.duration
    }

    internal fun isPng(): Boolean {
        return player.isPlaying
    }

    internal fun pausePlayer() {
        player.pause()
    }

    internal fun seek(posn: Int) {
        player.seekTo(posn)
    }

    internal fun go() {
        player.start()
    }

    class MusicBinder : Binder() {

        internal fun getService(): MusicService {
            return MusicService()
        }
    }
}
