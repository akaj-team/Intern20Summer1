package com.asiantech.intern20summer1.week9ver1.activity

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9ver1.adapter.SongAdapter
import com.asiantech.intern20summer1.week9ver1.controller.MusicController
import com.asiantech.intern20summer1.week9ver1.model.Song
import com.asiantech.intern20summer1.week9ver1.service.MusicService
import kotlin.system.exitProcess

class HomeWeekNineActivity : AppCompatActivity(), MediaController.MediaPlayerControl {

    private lateinit var songAdt: SongAdapter
    private var songList = arrayListOf<Song>()
    private lateinit var songView: ListView
    private lateinit var controller: MusicController
    private var musicSrv: MusicService? = null
    private var playIntent: Intent? = null
    private var paused = false
    private var playbackPaused = false
    // The boolean flag to keep track of the binding status
    private var musicBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_w9v1)
        configStatusBar()

        setController()
        songView = findViewById(R.id.song_list)
        getSongList()
    }

    override fun onStart() {
        super.onStart()
        if (playIntent == null) {
            playIntent = Intent(this, MusicService::class.java)
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE)
            startService(playIntent)
        }
    }

    override fun onPause() {
        super.onPause()
        paused = true
    }

    override fun onResume() {
        super.onResume()
        if (paused) {
            setController()
            paused = false
        }
    }

    override fun onStop() {
        controller.hide()
        super.onStop()
    }

    override fun onDestroy() {
        stopService(playIntent)
        musicSrv = null
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_shuffle -> {
                musicSrv?.setShuffle()
            }
            R.id.action_end -> {
                stopService(playIntent)
                musicSrv = null
                exitProcess(0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setController() {
        controller = MusicController(this)

        // Init 2 onClickListener
        controller.setPrevNextListeners(
            { playNext() },
            { playPrev() }
        )

        controller.setMediaPlayer(this);
        controller.setAnchorView(findViewById(R.id.song_list));
        controller.isEnabled = true;
    }

    @SuppressLint("Recycle")
    private fun getSongList() {
        //retrieve song info
        val musicResolver = contentResolver
        val musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val musicCursor = musicResolver.query(musicUri, null, null, null, null)

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            val idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            //add songs to list
            do {
                val thisId = musicCursor.getLong(idColumn)
                val thisTitle = musicCursor.getString(titleColumn)
                val thisArtist = musicCursor.getString(artistColumn)
                songList.add(Song(thisId, thisTitle, thisArtist))
            } while (musicCursor.moveToNext())
        }

        songList.sortWith(Comparator<Song> { a, b -> a.title.compareTo(b.title) })

        songAdt = SongAdapter(this, songList)
        songView.adapter = songAdt
    }

    fun songPicked(view: View) {
        musicSrv?.setSong(view.tag.toString().toInt())
        musicSrv?.playSong()
        if (playbackPaused) {
            setController()
            playbackPaused = false
        }
        controller.show(0)
    }

    private fun playPrev() {
        musicSrv?.playPrev()
        if (playbackPaused) {
            setController();
            playbackPaused = false;
        }
        controller.show(0)
    }

    private fun playNext() {
        musicSrv?.playNext()
        if (playbackPaused) {
            setController();
            playbackPaused = false;
        }
        controller.show(0)
    }

    private val musicConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder : MusicService.MusicBinder? = service as? MusicService.MusicBinder
            // Get service
            musicSrv = binder?.getService()
            // Pass list
            musicSrv?.setList(songList)
            musicBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicBound = false
        }
    }

    private fun configStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    override fun isPlaying(): Boolean {
        if (musicBound) {
            return musicSrv?.isPng()!!
        }
        return false
    }

    override fun canSeekForward(): Boolean {
        return true
    }

    override fun getDuration(): Int {
        return if (musicSrv != null && musicBound == musicSrv?.isPng())
            musicSrv!!.getDur()
        else 0
    }

    override fun pause() {
        playbackPaused = true
        musicSrv?.pausePlayer()
    }

    override fun getBufferPercentage(): Int {
        TODO("Not yet implemented")
    }

    override fun seekTo(pos: Int) {
        musicSrv?.seek(pos)
    }

    override fun getCurrentPosition(): Int {
        return if (musicSrv != null && musicBound == musicSrv?.isPng()) {
            musicSrv!!.getPosn()
        } else {
            0
        }
    }

    override fun canSeekBackward(): Boolean {
        return true
    }

    override fun start() {
        musicSrv?.go()
    }

    override fun getAudioSessionId(): Int {
        TODO("Not yet implemented")
    }

    override fun canPause(): Boolean {
        return true
    }
}
