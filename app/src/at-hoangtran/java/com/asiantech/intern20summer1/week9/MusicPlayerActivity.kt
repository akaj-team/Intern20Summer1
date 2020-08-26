package com.asiantech.intern20summer1.week9

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.activity_music_player.*

class MusicPlayerActivity : AppCompatActivity() {

    companion object {
        const val READ_REQUEST_CODE = 1
    }

    private var totalTime: Int = 0
    private lateinit var mp: MediaPlayer
    private lateinit var songList: ArrayList<Song>
    private val songAdapter = MusicAdapter(songList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        handleButton()
        handleProgressBar()
    }

    private fun handleButton() {
        val intent = Intent(this, MusicService::class.java)
        btnSongPlayerPlayPause.setOnClickListener {
            if (mp.isPlaying) {
                stopService(intent)
                btnSongPlayerPlayPause.setBackgroundResource(R.drawable.play)
            } else {
                startService(intent)
                btnSongPlayerPlayPause.setBackgroundResource(R.drawable.pause)
            }
        }
    }

    private fun handleProgressBar() {
        seekBarSongPlayer.max = totalTime
        seekBarSongPlayer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mp.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun handleThread() {
        Thread(Runnable {
            while (true) {
                try {
                    val msg = Message()
                    msg.what = mp.currentPosition
//                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                }
            }
        }).start()

        var handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                val currentPosition = msg.what
                seekBarSongPlayer.progress = currentPosition
                val elapsedTime = createTimeLabel(currentPosition)
                tvElapsedTime.text = elapsedTime
                val remainingTime = createTimeLabel(totalTime - currentPosition)
                tvRemainingTime.text = "-$remainingTime"
            }
        }
    }

    private fun createTimeLabel(time: Int): String {
        var timeLabel: String
        val min = time / 1000 / 60
        val sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec
        return timeLabel
    }

    private fun initApdater() {
        songRecyclerView?.apply {
            adapter = songAdapter
            layoutManager = LinearLayoutManager(this@MusicPlayerActivity)
            setHasFixedSize(true)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getSongFromDevice() {
        songList = arrayListOf()
        val adapter = MusicAdapter(songList)
        val contentResolver = contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = contentResolver.query(uri, null, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val author = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val duration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            do {
                val currentId = cursor.getLong(id)
                val imgUri = ContentUris.withAppendedId(uri, currentId)
                val currentTitle = cursor.getString(title)
                val cuttentArtist = cursor.getString(author)
                val currentduration = cursor.getString(duration)
                songList.add(
                    Song(
                        currentduration, currentTitle, cuttentArtist, imgUri.toString(), currentId
                    )
                )
            } while (cursor.moveToNext())
            cursor.close()
        }
        songRecyclerView.layoutManager = LinearLayoutManager(this)
        handleButton()
        songRecyclerView.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun runTimePermission() {
        val permission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions()
        } else {
            getSongFromDevice()
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            READ_REQUEST_CODE
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(
            CreateNotification.CHANNEL_ID,
            "music",
            NotificationManager.IMPORTANCE_LOW
        )
    }
}
