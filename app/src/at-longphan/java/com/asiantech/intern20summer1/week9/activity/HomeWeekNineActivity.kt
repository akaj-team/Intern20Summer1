package com.asiantech.intern20summer1.week9.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.MenuItem
import android.view.View
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9.controller.MusicController
import com.asiantech.intern20summer1.week9.service.MusicService
import kotlin.system.exitProcess


class HomeWeekNineActivity : AppCompatActivity(), MediaController.MediaPlayerControl {

    private lateinit var controller: MusicController
    private var musicSrv: MusicService? = null
    private var playIntent: Intent? = null

    // The boolean flag to keep track of the binding status
    private var musicBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_w9)
        configStatusBar()

        setController()
    }

    override fun onStart() {
        super.onStart()
        if (playIntent == null) {
            playIntent = Intent(this, MusicService::class.java)
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE)
            startService(playIntent)
        }
    }

    override fun onDestroy() {
        stopService(playIntent)
        musicSrv = null
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_shuffle -> {
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

    private fun songPicked(view: View) {
        musicSrv?.setSong(view.tag.toString().toInt())
        musicSrv?.playSong()
    }

    private val musicConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as? MusicService.MusicBinder
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
        TODO("Not yet implemented")
    }

    override fun canSeekForward(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getDuration(): Int {
        TODO("Not yet implemented")
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun getBufferPercentage(): Int {
        TODO("Not yet implemented")
    }

    override fun seekTo(pos: Int) {
        TODO("Not yet implemented")
    }

    override fun getCurrentPosition(): Int {
        TODO("Not yet implemented")
    }

    override fun canSeekBackward(): Boolean {
        TODO("Not yet implemented")
    }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun getAudioSessionId(): Int {
        TODO("Not yet implemented")
    }

    override fun canPause(): Boolean {
        TODO("Not yet implemented")
    }
}