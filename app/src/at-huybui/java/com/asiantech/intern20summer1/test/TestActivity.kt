package com.asiantech.intern20summer1.test

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.models.Song
import kotlinx.android.synthetic.`at-huybui`.activity_test.*

class TestActivity : AppCompatActivity(), Playable {

    var songList = mutableListOf<Song>()
    var position = 2
    var isPlaying = false

    lateinit var notification: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        setColorStatusBar(R.color.background_white)
        initView()
    }

//    1598327444.039 1994-2041/com.asiantech.intern20summer1.huybui.debug D/XXXX: content://media/external/audio/media/16
//    1598327444.039 1994-2041/com.asiantech.intern20summer1.huybui.debug D/XXXX: content://media/external/audio/media/17
//    1598327444.039 1994-2041/com.asiantech.intern20summer1.huybui.debug D/XXXX: content://media/external/audio/media/18
//    1598327444.039 1994-2041/com.asiantech.intern20summer1.huybui.debug D/XXXX: content://media/external/audio/media/19
//    1598327444.039 1994-2041/com.asiantech.intern20summer1.huybui.debug D/XXXX: content://media/external/audio/media/20
//    1598327444.039 1994-2041/com.asiantech.intern20summer1.huybui.debug D/XXXX: content://media/external/audio/media/21
//    1598327444.039 1994-2041/com.asiantech.intern20summer1.huybui.debug D/XXXX: content://media/external/audio/media/22
//    1598327444.039 1994-2041/com.asiantech.intern20summer1.huybui.debug D/XXXX: content://media/external/audio/media/23
//    1598327444.039 1994-2041/com.asiantech.intern20summer1.huybui.debug D/XXXX: content://media/external/audio/media/24
//    1598327444.039 1994-2041/com.asiantech.intern20summer1.huybui.debug D/XXXX: content://media/external/audio/media/25
//    1598327444.039 1994-2041/com.asiantech.intern20summer1.huybui.debug D/XXXX: content://media/external/audio/media/26
//    1598327444.039 1994-2041/com.asiantech.intern20summer1.huybui.debug D/XXXX: content://media/external/audio/media/27
//    1598327444.039 1994-2041/com.asiantech.intern20summer1.huybui.debug D/XXXX: content://media/external/audio/media/28
//    1598327444.039 1994-2041/com.asiantech.intern20summer1.huybui.debug D/XXXX: content://media/external/audio/media/29
//    1598327444.039 1994-2041/com.asiantech.intern20summer1.huybui.debug D/XXXX: content://media/external/audio/media/30
//    1598327444.039 1994-2041/com.asiantech.intern20summer1.huybui.debug D/XXXX: content://media/external/audio/media/31
//    1598327444.039 1994-2041/com.asiantech.intern20summer1.huybui.debug D/XXXX: content://media/external/audio/media/32
//    1598327444.039 1994-2041/com.asiantech.intern20summer1.huybui.debug D/XXXX: content://media/external/audio/media/33

    private fun initView() {
        initData()
        createChannel()
        registerReceiver(broadcastReceiver, IntentFilter("Song_Song"))
        startService(Intent(baseContext, ServiceTest::class.java))
        btnPlayTest?.setOnClickListener {
            if (isPlaying) {
                onSongPause()
            } else {
                onSongPlay()
            }
        }
    }

    private fun initData() {
        var song = Song(contentUri = "content://media/external/audio/media/16")
        song = Song().getData(this, song)
        songList.add(song)
        song = Song(contentUri = "content://media/external/audio/media/17")
        song = Song().getData(this, song)
        songList.add(song)
        song = Song(contentUri = "content://media/external/audio/media/18")
        song = Song().getData(this, song)
        songList.add(song)
        song = Song(contentUri = "content://media/external/audio/media/19")
        song = Song().getData(this, song)
        songList.add(song)
        song = Song(contentUri = "content://media/external/audio/media/20")
        song = Song().getData(this, song)
        songList.add(song)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = NotificationChannel(
                CreateNotification.CHANNEL_ID,
                "HUY",
                NotificationManager.IMPORTANCE_LOW
            )
            notification = getSystemService(NotificationManager::class.java)
            if (notification != null) {
                notification.createNotificationChannel(channel)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.cancelAll()
        }
        unregisterReceiver(broadcastReceiver)
    }

    private fun setColorStatusBar(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.apply {
                window.statusBarColor = ContextCompat.getColor(this, color)
                if (color == R.color.background_white) {
                    window.decorView.systemUiVisibility =
                        window.decorView.systemUiVisibility.or(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                } else {
                    window.decorView.systemUiVisibility =
                        window.decorView.systemUiVisibility.and(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
                }
            }
        }
    }

    var broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var action = intent?.extras?.getString("actionName")
            when (action) {
                CreateNotification.ACTION_PREVIUOS -> {
                    onSongPrevious()
                }
                CreateNotification.ACTION_PLAY -> {
                    if (isPlaying) {
                        onSongPause()
                    } else {
                        onSongPlay()
                    }
                }
                CreateNotification.ACTION_NEXT -> {
                    onSongNext()
                }
            }
        }

    }

    override fun onSongPrevious() {
        position--
        if (position < 0) {
            position = 0
        }
        CreateNotification().createNotification(
            this,
            songList[position],
            R.drawable.ic_pause_notification,
            position,
            songList.size - 1
        )
        title = songList[position].nameSong
        isPlaying = true
    }

    override fun onSongPlay() {
        CreateNotification().createNotification(
            this,
            songList[position],
            R.drawable.ic_pause_notification,
            position,
            songList.size - 1
        )
        btnPlayTest?.setImageResource(R.drawable.ic_pause_button)
        title = songList[position].nameSong
        isPlaying = true
    }

    override fun onSongPause() {
        CreateNotification().createNotification(
            this,
            songList[position],
            R.drawable.ic_play_notification,
            position,
            songList.size - 1
        )
        btnPlayTest?.setImageResource(R.drawable.ic_play_button)
        title = songList[position].nameSong
        isPlaying = false
    }

    override fun onSongNext() {
        position++
        if (position >= songList.size) position = songList.size - 1
        CreateNotification().createNotification(
            this,
            songList[position],
            R.drawable.ic_pause_notification,
            position,
            songList.size - 1
        )
        title = songList[position].nameSong
        isPlaying = true
    }
}
